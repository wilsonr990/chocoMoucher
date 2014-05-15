/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chocomoucher;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author wilsonr
 */
public class Analizer {
    private int[][] map;
    private List<Point> numbers;
    public double[][] probabilities;
    private int totalOfCombinations;
    private double[][] ocurrencesOfMines;
    
    Analizer(){
        this.probabilities = new double[8][9];
        this.numbers = new LinkedList<>();
        
        map = new int[8][9];
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ ){
            map[i][j] = -1;
            probabilities[i][j] = -1;
        }
    }

    public int update( Point lastMove, int[][] m){
        map = m;
        restartProbabilities();
        
        if( map[lastMove.x][lastMove.y] == 9 ){
            probabilities[lastMove.x][lastMove.y] = 1;
        }
        else{
            updateNumbers(lastMove);
        }
        return map[lastMove.x][lastMove.y];
    }
    
    double[][] findProbabilities( ) {
        totalOfCombinations = 0;
        ocurrencesOfMines = new double[8][9];
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            ocurrencesOfMines[i][j] = probabilities[i][j];
        
        totalOfCombinations = calculateCombinations();
        
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            if( ocurrencesOfMines[i][j] == 0 )
                probabilities[i][j] = 0;
            else if( ocurrencesOfMines[i][j] == -1 )
                probabilities[i][j] = 0.5;
            else if( probabilities[i][j]==-1 )
                probabilities[i][j] = ocurrencesOfMines[i][j]/totalOfCombinations;
        
        return probabilities;
    }
    
    List<Point> bestMove( ) {
        double minProbability = 2;
        LinkedList<Point> moves = new LinkedList<>();
        for(int k=0; k<8; k++)for(int l=0; l<9; l++)
            if( probabilities[k][l] == 0 && map[k][l]==-1 ){
                probabilities[k][l] = -1;
                moves.add( new Point(k,l) );
                return moves;
            }
            else if( probabilities[k][l] < minProbability && map[k][l]==-1 ){
                minProbability = probabilities[k][l];
            }
        if( minProbability == 1 )
            return null;
        for(int k=0; k<8; k++)for(int l=0; l<9; l++)
            if( probabilities[k][l] == minProbability && map[k][l]==-1 ){
                moves.add( new Point(k,l) );
            }
        return moves;
    }

    public boolean hasEnded(){
        for(int k=0; k<8; k++)for(int l=0; l<9; l++)
            if( probabilities[k][l]>0 && probabilities[l][l]<1 )
                return false;
            
        return true;
    }
    
    private int calculateCombinations() {
        if( numbers.isEmpty() )
            return 0;
        
        int numberIndex = 0;
        return calculateCombinationsForNumber(numberIndex);
    }

    private int calculateCombinationsForNumber(int numberIndex) {
        Point numberPos = numbers.get(numberIndex);
        int numberOfCombinations = 0;
        List<Point> neighbors = new LinkedList<>();
        int missingBombs;
        
        missingBombs = findNeighborsAndMissingBombs(numberPos, neighbors);
        if( missingBombs<0 )
            return 0;
        
        for( Point neighbor : neighbors )
            if( ocurrencesOfMines[neighbor.x][neighbor.y] == -1 )
                ocurrencesOfMines[neighbor.x][neighbor.y] = 0;
        
        if( missingBombs==0 ){
            int increment = calculateCombinationsForNextNeighbor(numberIndex, neighbors);
            numberOfCombinations += increment;
        }else{
            if( neighbors.isEmpty() )
                return 0;
            for( Point neighbor : neighbors ){
                probabilities[neighbor.x][neighbor.y] = 1;
                int increment = 1;
                
                if( missingBombs > 1 )
                    increment = calculateCombinationsForNumber(numberIndex);
                else 
                    increment = calculateCombinationsForNextNeighbor(numberIndex, neighbors);
                numberOfCombinations += increment;
                ocurrencesOfMines[neighbor.x][neighbor.y] += increment;
                probabilities[neighbor.x][neighbor.y] = 0;
            }
        }
        for( Point neigh : neighbors )
            probabilities[neigh.x][neigh.y] = -1;
        
        return numberOfCombinations;
    }

    private int calculateCombinationsForNextNeighbor(int numberIndex, List<Point> neighbors) {
        int combinations = 1;
        if( numbers.size() > numberIndex+1 ){
            for( Point neigh : neighbors )
                if( probabilities[neigh.x][neigh.y] == -1 )
                    probabilities[neigh.x][neigh.y] = 0;
            combinations = calculateCombinationsForNumber(numberIndex+1);
        }
        return combinations;
    }

    private int findNeighborsAndMissingBombs(Point numberPos, List<Point> neighbors) {
        int missingBombs = map[numberPos.x][numberPos.y];
        for(int k=numberPos.x-1; k<=numberPos.x+1; k++)
            for(int l=numberPos.y-1; l<=numberPos.y+1; l++)
                if( k>=0 && l>=0 && k<8 && l<9 )
                    if( probabilities[k][l]==-1 )
                        neighbors.add( new Point(k,l) );
                    else if( probabilities[k][l]==1 )
                        --missingBombs;
        return missingBombs;
    }

    private void restartProbabilities() {
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            if( probabilities[i][j]>0 && probabilities[i][j]<1 )
                probabilities[i][j] = -1;
    }

    private void updateNumbers(Point pos) {
        if( probabilities[pos.x][pos.y]==-1 ){
            probabilities[pos.x][pos.y] = 0;
            if( map[pos.x][pos.y] != 0 )
                numbers.add(new Point(pos.x,pos.y));
            else{
                for(int k=pos.x-1; k<=pos.x+1; k++)
                    for(int l=pos.y-1; l<=pos.y+1; l++)
                        if( k>=0 && l>=0 && k<8 && l<9 )
                            updateNumbers(new Point(k,l));
            }
        }
    }
}
