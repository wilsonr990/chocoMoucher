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
            ChocoMoucher myInterface;
            myInterface = new ChocoMoucher();
            
            try {
                myInterface.Start();
            } catch (gameIsLocked ex) {
                Thread.sleep(100);
                myInterface.Update();
            }
            printMap( myInterface.getMap() );
            try {
                myInterface.clickOn( new Point(0,0) );
            } catch (gameIsLocked ex) {
                System.err.println("Trying Again");
                Thread.sleep(2000);
                myInterface.clickOn( new Point(0,0) );
            }
            printMap( myInterface.getMap() );
            try {
                myInterface.clickOn( new Point(0,8) );
            } catch (gameIsLocked ex) {
                System.err.println("Trying Again");
                Thread.sleep(200);
                myInterface.clickOn( new Point(0,8) );
            }
            
            printMap( myInterface.getMap() );
            try {
                myInterface.clickOn( new Point(7,0) );
            } catch (gameIsLocked ex) {
                System.err.println("Trying Again");
                Thread.sleep(200);
                myInterface.clickOn( new Point(7,0) );
            }
            printMap( myInterface.getMap() );
        } catch (NoOpenGame ex) {
            System.err.println("No Game Is Open");
        } catch (gameIsLocked ex1) {
            System.err.println("Game Was Closed");
        } catch (InterruptedException ex1) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    
}
