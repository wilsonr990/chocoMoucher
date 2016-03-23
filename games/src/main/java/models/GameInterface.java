package models;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by wilsonr on 2/1/2016.
 */
public class GameInterface {
    private static Robot r;

    public GameInterface() {
        try {
            r = new Robot();
        } catch (AWTException e) {
        }
    }


    public static void MoveMouseTo(Point p) {
        r.mouseMove(p.x, p.y);
    }

    public static void MouseClick() {
        try {
            r.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
