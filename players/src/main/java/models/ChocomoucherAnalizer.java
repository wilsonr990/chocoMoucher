package models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Exceptions.GameHasEnded;
import models.GameInterface;
import models.Player;
import models.impl.ChocoMouche;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static models.GameHandler.Status.PlayingGame;
import static models.GameHandler.Status.WaitingToStart;
import static models.impl.ChocoMouche.Property.Lives;
import static models.impl.ChocoMouche.Property.TurnPercentage;

/**
 * @author wilsonr
 */
public class ChocomoucherAnalizer extends Player {
    private int[][] map;
    private List<Point> numbers;
    public double[][] probabilities;
    private int totalOfCombinations;
    private double[][] ocurrencesOfMines;
    private int lives;
    private List<Point> moves;
    private Point lastMove;
    private boolean done=false;

    public ChocomoucherAnalizer(ChocoMouche chocoMouche, GameInterface gameInterface) {
        super(chocoMouche, gameInterface);

        this.lives = 3;
        restartData();
    }

    private void restartData() {
        this.probabilities = new double[8][9];
        this.ocurrencesOfMines = null;
        this.numbers = new LinkedList<Point>();
        this.moves = new ArrayList<Point>();

        map = new int[8][9];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++) {
                map[i][j] = -1;
                probabilities[i][j] = -1;
            }
    }

    public double[][] findProbabilities() {
        totalOfCombinations = 0;
        ocurrencesOfMines = new double[8][9];
        for (int i = 0; i < 8; i++)
            System.arraycopy(probabilities[i], 0, ocurrencesOfMines[i], 0, 9);

        totalOfCombinations = calculateCombinations();

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (ocurrencesOfMines[i][j] == 0)
                    probabilities[i][j] = 0;
                else if (ocurrencesOfMines[i][j] == -1)
                    probabilities[i][j] = 0.5;
                else if (probabilities[i][j] == -1)
                    probabilities[i][j] = ocurrencesOfMines[i][j] / totalOfCombinations;

        return probabilities;
    }

    public List<Point> bestMove() throws GameHasEnded {
        double minProbability = 2;
        LinkedList<Point> moves = new LinkedList<Point>();
        for (int k = 0; k < 8; k++)
            for (int l = 0; l < 9; l++)
                if (probabilities[k][l] == 0 && map[k][l] == -1)
                    moves.add(new Point(k, l));
                else if (probabilities[k][l] < minProbability && map[k][l] == -1)
                    minProbability = probabilities[k][l];

        if (!moves.isEmpty())
            return moves;

        if (minProbability == 1)
            throw new GameHasEnded();

        for (int k = 0; k < 8; k++)
            for (int l = 0; l < 9; l++)
                if (probabilities[k][l] == minProbability && map[k][l] == -1) {
                    moves.add(new Point(k, l));
                }
        return moves;
    }

    public boolean hasEnded() {
        for (int k = 0; k < 8; k++)
            for (int l = 0; l < 9; l++)
                if (probabilities[k][l] > 0 && probabilities[k][l] < 1)
                    return false;

        return true;
    }

    private int calculateCombinations() {
        if (numbers.isEmpty())
            return 0;

        int numberIndex = 0;
        return calculateCombinationsForNumber(numberIndex);
    }

    private int calculateCombinationsForNumber(int numberIndex) {
        Point numberPos = numbers.get(numberIndex);
        int numberOfCombinations = 0;
        List<Point> neighbors = new LinkedList<Point>();
        int missingBombs;

        missingBombs = findNeighborsAndMissingBombs(numberPos, neighbors);
        if (missingBombs < 0)
            return 0;

        for (Point neighbor : neighbors)
            if (ocurrencesOfMines[neighbor.x][neighbor.y] == -1)
                ocurrencesOfMines[neighbor.x][neighbor.y] = 0;

        if (missingBombs == 0) {
            int increment = calculateCombinationsForNextNeighbor(numberIndex, neighbors);
            numberOfCombinations += increment;
        } else {
            if (neighbors.isEmpty())
                return 0;
            for (Point neighbor : neighbors) {
                probabilities[neighbor.x][neighbor.y] = 1;
                int increment;

                if (missingBombs > 1)
                    increment = calculateCombinationsForNumber(numberIndex);
                else
                    increment = calculateCombinationsForNextNeighbor(numberIndex, neighbors);
                numberOfCombinations += increment;
                if(increment>0)
                    ocurrencesOfMines[neighbor.x][neighbor.y] += increment;
                probabilities[neighbor.x][neighbor.y] = 0;
            }
        }
        for (Point neigh : neighbors)
            probabilities[neigh.x][neigh.y] = -1;

        return numberOfCombinations;
    }

    private int calculateCombinationsForNextNeighbor(int numberIndex, List<Point> neighbors) {
        int combinations = 1;
        if (numbers.size() > numberIndex + 1) {
            for (Point neigh : neighbors)
                if (probabilities[neigh.x][neigh.y] == -1)
                    probabilities[neigh.x][neigh.y] = 0;
            combinations = calculateCombinationsForNumber(numberIndex + 1);
        }
        return combinations;
    }

    private int findNeighborsAndMissingBombs(Point numberPos, List<Point> neighbors) {
        int missingBombs = map[numberPos.x][numberPos.y];
        for (int k = numberPos.x - 1; k <= numberPos.x + 1; k++)
            for (int l = numberPos.y - 1; l <= numberPos.y + 1; l++)
                if (k >= 0 && l >= 0 && k < 8 && l < 9)
                    if (probabilities[k][l] == -1)
                        neighbors.add(new Point(k, l));
                    else if (probabilities[k][l] == 1)
                        --missingBombs;
        return missingBombs;
    }

    private void restartProbabilities() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++) {
                if (probabilities[i][j] > 0 && probabilities[i][j] < 1)
                    probabilities[i][j] = -1;
                if (i==lastMove.x && j==lastMove.y)
                    probabilities[i][j] = -1;
            }
    }

    private void updateNumbers(Point pos) {
        if (probabilities[pos.x][pos.y] == -1) {
            probabilities[pos.x][pos.y] = 0;
            if (map[pos.x][pos.y] != 0)
                numbers.add(new Point(pos.x, pos.y));
            else {
                for (int k = pos.x - 1; k <= pos.x + 1; k++)
                    for (int l = pos.y - 1; l <= pos.y + 1; l++)
                        if (k >= 0 && l >= 0 && k < 8 && l < 9)
                            updateNumbers(new Point(k, l));
            }
        }
    }

    public int update(Point lastMove, int[][] m) {
        map = m.clone();
        restartProbabilities();

        if (this.map[lastMove.x][lastMove.y] == 9) {
            probabilities[lastMove.x][lastMove.y] = 1.0;
        } else {
            updateNumbers(lastMove);
        }
        return this.map[lastMove.x][lastMove.y];
    }

    @Override
    protected void Play() throws GameHasEnded {
        System.out.println("IN PLAY");
        Map<ChocoMouche.Property, Object> gameProperties = gameHandler.getGameProperties();
        map = (int[][]) gameProperties.get(ChocoMouche.Property.Map);
        Integer turnPercent = (Integer) gameProperties.get(TurnPercentage);
        Integer lives = (Integer) gameProperties.get(Lives);
        if (gameHandler.getStatus().equals(WaitingToStart)) {
            System.out.println("  Detecting Game");
            restartData();
            GameInterface.MoveMouseTo(new Point(0, 0));
        } else if (gameHandler.getStatus().equals(PlayingGame)) {
            System.out.println("  Playing");
            if (lives != this.lives) {
                System.out.println("    live lost!!");
                this.lives = lives;
                restartData();
            } else if (turnPercent < 99) {
                if(!done) {
                    System.out.println("    deciding");
                    if (lastMove != null) {
                        System.out.println("  (updateMove)");
                        update(lastMove, map);
                    }
                    lastMove = decideNextMove();

                    GameInterface.MoveMouseTo(lastMove);
                }
                else {
                    System.out.println("    move: " + lastMove.x + " " + lastMove.y);

                    System.out.println("sssssssssss "+numbers.size());
                }
                done=true;
            }else if (turnPercent >=99) {
                done=false;
                if(lastMove!=null)
                    System.out.println("    last: " + lastMove.x + " " + lastMove.y);
            }else {
                System.out.println("    wait, Im not sure");
            }
        } else {
            System.out.println("  unknown state!!");
        }
    }

    private Point decideNextMove() throws GameHasEnded {
        findProbabilities();
        moves = bestMove();

        //int random = (int) (Math.random() * (moves.size() - 1));
        return moves.remove(0);
    }
}
