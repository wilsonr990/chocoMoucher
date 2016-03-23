package models;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by wilsonr on 2/1/2016.
 */
public class GameInterface {
    private static Robot r;

    public static boolean MoveMouseTo(Point p) {
        try {
            getRobot().mouseMove(p.x, p.y);
            return true;
        } catch (AWTException e) {
            return false;
        }
    }

    public static boolean MouseClick() {
        try {
            getRobot().mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            getRobot().mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
            return true;
        } catch (InterruptedException e) {
            return false;
        } catch (AWTException e) {
            return false;
        }
    }

    public static Robot getRobot() throws AWTException {
        if(r==null)
            r=new Robot();
        return r;
    }

    public void clickOn(Point p) {
        /*try {
            if (gameDimension == null || mapLocation == null)
                throw new NoOpenGame();
            int cellPositionX = (int) (gameLocation.x + mapLocation.x + p.x * cellDimension.getWidth() + cellDimension.getWidth() / 2);
            int cellPositionY = (int) (gameLocation.y + mapLocation.y + p.y * cellDimension.getHeight() + cellDimension.getHeight() / 2);
            r.mouseMove(cellPositionX, cellPositionY);
            r.mousePress(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mousePress(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(BUTTON1_DOWN_MASK);
            Thread.sleep(680);

            updateMap(p);
        } catch (InterruptedException ex) {
            Logger.getLogger(models.impl.ChocoMouche.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
