package models.impl;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import models.BasicPlayer;
import models.Game;
import models.GameInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static models.impl.ChocoMouche.Property.TurnPercentage;

/**
 * @author wilsonr
 */
public class ChocomouchePlayer extends BasicPlayer {
    static private Integer turnPercentage;
    private int lives;
    private int[][] map;

    private List<Point> numbers;
    public double[][] probabilities;
    private double[][] ocurrencesOfMines;
    private List<Point> moves;
    private double minProbability;

    public ChocomouchePlayer(Game chocoMouche) {
        super(chocoMouche);

        restartData();
    }

    public void reset() {
        System.out.println("reseting");
        restartData();
    }

    public boolean update() {
        Map<Object, Object> properties = game.getGameProperties();
        turnPercentage = (Integer) properties.get(TurnPercentage);
        Integer lives = (Integer) properties.get(ChocoMouche.Property.Lives);
        int[][] newMap = (int[][]) properties.get(ChocoMouche.Property.Map);

        if (turnPercentage <= 98 && mapChanged(newMap)) {
            System.out.println("updating");

            updateNumbers();

            System.out.println("updated");
            return true;
        }
        return false;
    }

    private boolean mapChanged(int[][] newMap) {
        if (newMap == null) return false;
        if (map == null) {
            map = new int[8][9];
            for (int i = 0; i < 8; i++)
                System.arraycopy(newMap[i], 0, map[i], 0, 9);
            return true;
        }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (newMap[i][j] != map[i][j]) {
                    for (int n = 0; n < 8; n++)
                        System.arraycopy(newMap[n], 0, map[n], 0, 9);
                    return true;
                }
        return false;
    }

    public boolean getBestMoves() {
        System.out.println("getting best moves");

        if (minProbability != 0 || moves.isEmpty()) {
            findProbabilities();

            moves = bestMoves();

            if (moves.isEmpty()) {
                game.reset();
                restartData();
                return false;
            }
            System.out.println("new best moves:" + moves);
        } else {
            System.out.println("there was pending moves!");
        }
        return true;
    }

    public boolean move() {
        System.out.println("moving");
        int random = 0;//(int) (Math.random() * (moves.size() - 1));

        Point move = moves.remove(random);
        System.out.println("MOVEE! " + move);
        Point mapLocation = new Point(53, 38);
        Dimension cellDimension = new Dimension(30, 29);
        int cellPositionX = (int) (game.getLocation().x + mapLocation.x + move.x * cellDimension.getWidth() + cellDimension.getWidth() / 2);
        int cellPositionY = (int) (game.getLocation().y + mapLocation.y + move.y * cellDimension.getHeight() + cellDimension.getHeight() / 2);

        boolean success = GameInterface.MoveMouseTo(new Point(cellPositionX, cellPositionY));
        success &= GameInterface.MouseClick();
        success &= GameInterface.MouseClick();
        return success;
    }

    private void restartData() {
        map = null;
        this.minProbability = 1;
        this.probabilities = new double[8][9];
        this.ocurrencesOfMines = null;
        this.numbers = new LinkedList<Point>();
        this.moves = new ArrayList<Point>();

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                probabilities[i][j] = -1;
    }

    private void updateNumbers() {
        numbers = new ArrayList<Point>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++) {
                if (map[i][j] > 0 && map[i][j] < 9)
                    numbers.add(new Point(i, j));
                if (map[i][j] >= 0 && map[i][j] <= 9)
                    moves.remove(new Point(i, j));
            }
    }

    public void findProbabilities() {
        restartProbabilities();

        ocurrencesOfMines = new double[8][9];
        for (int i = 0; i < 8; i++)
            System.arraycopy(probabilities[i], 0, ocurrencesOfMines[i], 0, 9);

        int totalOfCombinations = this.calculateCombinations(0);

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (ocurrencesOfMines[i][j] == 0)
                    probabilities[i][j] = 0;
                else if (ocurrencesOfMines[i][j] == -1)
                    probabilities[i][j] = 0.5;
                else if (probabilities[i][j] == -1)
                    probabilities[i][j] = ocurrencesOfMines[i][j] / totalOfCombinations;
    }

    private void restartProbabilities() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++) {
                if (probabilities[i][j] > 0 && probabilities[i][j] < 1)
                    probabilities[i][j] = -1;
                if (map[i][j] >= 0 && map[i][j] <= 8)
                    probabilities[i][j] = 0;
                else if (map[i][j] == 9)
                    probabilities[i][j] = 1;
            }
    }

    private int calculateCombinations(int numberIndex) {
        if (numbers.isEmpty()) return 0;

        int numberOfCombinations = 0;
        List<Point> neighbors = new LinkedList<Point>();
        int missingBombs = findNeighborsAndMissingBombs(numbers.get(numberIndex), neighbors);

        if (missingBombs < 0 || (missingBombs > 0 && neighbors.isEmpty()))
            return 0;

        for (Point neighbor : neighbors)
            if (ocurrencesOfMines[neighbor.x][neighbor.y] == -1)
                ocurrencesOfMines[neighbor.x][neighbor.y] = 0;

        if (missingBombs == 0) {
            int increment = calculateCombinationsForNextNeighbor(numberIndex, neighbors);
            numberOfCombinations += increment;
        } else {
            for (Point neighbor : neighbors) {
                probabilities[neighbor.x][neighbor.y] = 1;

                int increment;
                if (missingBombs > 1)
                    increment = calculateCombinations(numberIndex);
                else
                    increment = calculateCombinationsForNextNeighbor(numberIndex, neighbors);

                numberOfCombinations += increment;
                if (increment > 0)
                    ocurrencesOfMines[neighbor.x][neighbor.y] += increment;
                probabilities[neighbor.x][neighbor.y] = 0;
            }
        }
        for (Point neigh : neighbors)
            probabilities[neigh.x][neigh.y] = -1;

        return numberOfCombinations;
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

    private int calculateCombinationsForNextNeighbor(int numberIndex, List<Point> neighbors) {
        int combinations = 1;
        if (numbers.size() > numberIndex + 1) {
            for (Point neigh : neighbors)
                if (probabilities[neigh.x][neigh.y] == -1)
                    probabilities[neigh.x][neigh.y] = 0;
            combinations = calculateCombinations(numberIndex + 1);
        }
        return combinations;
    }

    private List<Point> bestMoves() {
        minProbability = 1;
        LinkedList<Point> moves = new LinkedList<Point>();
        for (int k = 0; k < 8; k++)
            for (int l = 0; l < 9; l++)
                if (probabilities[k][l] < minProbability && map[k][l] == -1)
                    minProbability = probabilities[k][l];

        for (int k = 0; k < 8; k++)
            for (int l = 0; l < 9; l++)
                if (probabilities[k][l] == minProbability && map[k][l] == -1) {
                    moves.add(new Point(k, l));
                }

        return moves;
    }

    public List<Point> getMoves() {
        return moves;
    }

    public double getMinProbability() {
        return minProbability;
    }
}
