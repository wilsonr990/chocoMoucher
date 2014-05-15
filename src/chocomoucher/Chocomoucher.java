/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chocomoucher;

import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilsonr
 */
class Chocomoucher {
    private ChocoMouche game;
    private boolean alive;
    private int[][] map, probs;
    private int lives;
    private Analizer analizer;
    
    public Chocomoucher( ChocoMouche theGame ) {
        game = theGame;
        lives = 3;
        alive = true;
    }

    private void restartGame() {
        analizer = new Analizer();
        alive = true;
        
        try {
            game.Start();
        } catch ( NoOpenGame | GameIsLocked ex) {
            System.out.println("Game Is Not Ready");
            alive = false;
        }
    }

    private void updateLastMove( Point lastMove) {
        int val = analizer.update(lastMove, game.getMap());
        if( val == 9 )
            lives--;
        if( lives==0 )
            alive =false;
    }
    
    private Point decideNextMove() {
        analizer.findProbabilities();
        List<Point> moves = analizer.bestMove();
        
        int random = (int)(Math.random() * (moves.size()-1));
        return moves.get(random);
    }
    
    public void play(){
        System.out.println("playing");
        lives = 3;
        restartGame();
        System.out.println("started");
        map = game.getMap();

        while(alive){
            try {
                Point move = decideNextMove();
                printMap();
                
                System.out.println("moving..." + move.x+" "+move.y);
                
                game.clickOn( move );
                
                if ( analizer.hasEnded() ){
                    continue;
                }
                
                updateLastMove(move);
                System.out.println("sdasdasd");
            } catch (NoOpenGame ex) {
                
            } catch (GameIsLocked ex) {
                
            }
        }
    }

    private void move(final Point move) {
        try{
            game.clickOn( move );
        }catch( NoOpenGame | GameIsLocked ex) {
            if ( analizer.hasEnded() ){
                System.out.println("restarting...");
                restartGame();
            }else{
                alive = false;
            }
        }
    }
    public void printMap(){
        if( map != null ){
            int count = 0;
            for( int i=0; i<8; i++){
                for( int j=0; j<9; j++){
                    System.out.print( map[i][j] +"("+analizer.probabilities[i][j]+")" +", " );
                }
                System.out.print("\n");
            }
        }
    }
}
