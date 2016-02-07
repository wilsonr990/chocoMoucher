import Exceptions.CantCaptureScreen;
import Exceptions.NoOpenGame;
import models.impl.ChocoMouche;

import java.io.IOException;

/**
 *
 * @author wilsonr
 */
public class Main {
    
    static public void printMap( char[] map){
        int count = 0;
        for (char cell: map){
            System.out.print( cell + ", " );
            if(++count == 8){
                count=0;
                System.out.print( "\n" );
            }
        }
    }
    
    public static void main(String[] args) throws CantCaptureScreen, IOException {
        int i=0;
//        playGame();
    }

    private static void playGame() {
        try {
            ChocoMouche gameInterface;
            gameInterface = new ChocoMouche();

            ChocomouchePlayer gamer;
            gamer = new ChocomouchePlayer( gameInterface );

            gamer.play();
        } catch (NoOpenGame ex) {
            System.err.println("No Game Is Open");
        }
    }

}
