package chocomoucher;

import java.awt.AWTException;
import java.awt.Point;

/**
 *
 * @author wilsonr
 */
public class main {
    public static void main(String[] args) throws InterruptedException{
        ChocoMoucher myInterface;
        myInterface = new ChocoMoucher();
        
        myInterface.Start();
        Thread.sleep(2000);
        
        myInterface.clickOn( new Point(0,0) );
        Thread.sleep(100);
        myInterface.clickOn( new Point(0,8) );
        Thread.sleep(100);
        myInterface.clickOn( new Point(7,0) );
    }
    
}
