package chocomoucher;

import java.awt.AWTException;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilsonr
 */
public class main {
    
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
    
    public static void main(String[] args){
        try {
            ChocoMouche gameInterface;
            gameInterface = new ChocoMouche();
            
            Chocomoucher gamer;
            gamer = new Chocomoucher( gameInterface );
         
            gamer.play();
        } catch (NoOpenGame ex) {
            System.err.println("No Game Is Open");
        }
    }
    
}
