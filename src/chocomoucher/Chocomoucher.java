/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chocomoucher;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilsonr
 */
class Chocomoucher {
    private final ChocoMouche game;
    private boolean alive;
    private Point move, lastMove;
    private char[] map, probs;
    private int lives;
    
    public Chocomoucher( ChocoMouche theGame ) {
        game = theGame;
        move = new Point(0,0);
        lives = 3;
    }

    private void updateLastMove() {
        lastMove = move;
        char valueInLastMove = map[lastMove.x+lastMove.y*8];
        
        
    }
    
    private void decideNextMove() {
    }
    
    public void play() {
        try {
            try {
                game.Start();
            } catch (gameIsLocked ex) {
                Thread.sleep(100);
                game.Update();
            }
            lives = 3;
            map = game.getMap();
            
            while(alive){
                try {
                    game.clickOn( move );
                } catch (gameIsLocked ex) {
                    System.err.println("Trying Again");
                    Thread.sleep(200);
                    game.clickOn( move );
                }
                updateLastMove();
                decideNextMove();
            }
            
        }catch ( NoOpenGame | gameIsLocked ex) {
            System.err.println("Game Is Not Ready");
        }catch (InterruptedException ex1) {
            Logger.getLogger(Chocomoucher.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
