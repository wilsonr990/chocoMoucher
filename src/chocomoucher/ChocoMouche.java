
package chocomoucher;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import static java.awt.event.InputEvent.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilsonr
 */

public class ChocoMouche{
    private Robot r;
    private HashMap<String, Image> images;
    private Point gameLocation, mapLocation;
    private Dimension mapDimension, gameDimension, cellDimension;
    private int[][] map;

    ChocoMouche() throws NoOpenGame{
        try {
            r = new Robot();
            map = new int[8][9];
            for(int i=0; i<8; i++){
                for(int j=0; j<9; j++){
                    map[i][j] = -1;
                }
            }
            
            images = new HashMap<>();
            readImages();
            
            gameLocation = getGameLocation();
            
            gameDimension = images.get("base").getDimension();
            mapDimension = images.get("map").getDimension();
            cellDimension = new Dimension(mapDimension.width/8, mapDimension.height/9);
        } catch (AWTException ex) {
            Logger.getLogger(ChocoMouche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Start() throws NoOpenGame, GameIsLocked{
        try {
            if( gameDimension == null )
                throw new NoOpenGame();
            
            r.mouseMove(gameLocation.x + (gameDimension.width>>1), gameLocation.y + (gameDimension.height>>1));
            r.mousePress(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(BUTTON1_DOWN_MASK);
            Thread.sleep(500);
            r.mousePress(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(BUTTON1_DOWN_MASK);
            r.mouseMove(gameLocation.x -10, gameLocation.y -10);
            Thread.sleep(1000);

            mapLocation = getMapLocation();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChocoMouche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Update() throws NoOpenGame{
        try {
            if( gameDimension == null )
                throw new NoOpenGame();
            
            mapLocation = getMapLocation();
            map = new int[8][9];
        } catch (GameIsLocked ex) {
            throw new NoOpenGame();
        }
    }
    
    public void clickOn(Point p) throws NoOpenGame, GameIsLocked{
        try {
            if( gameDimension == null || mapLocation == null )
                throw new NoOpenGame();
            int cellPositionX = (int) (gameLocation.x + mapLocation.x + p.x*cellDimension.getWidth() + cellDimension.getWidth()/2);
            int cellPositionY = (int) (gameLocation.y + mapLocation.y + p.y*cellDimension.getHeight()+ cellDimension.getHeight()/2);
            r.mouseMove(cellPositionX, cellPositionY);
            r.mousePress(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mousePress(BUTTON1_DOWN_MASK);
            Thread.sleep(100);
            r.mouseRelease(BUTTON1_DOWN_MASK);
            Thread.sleep(100);

            updateMap( p );
        } catch (InterruptedException ex) {
            Logger.getLogger(ChocoMouche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    int[][] getMap() {
        return map;
    }

    private void readImages(){
        try {
            images.put("base", new Image( "images/idImage.png" ) );
            images.put("map", new Image( "images/map.png" ) );
            images.put("fly", new Image( "images/fly.png" ) );
            images.put("fly2", new Image( "images/fly2.png" ) );
            images.put("cell", new Image( "images/cell.png" ) );
            images.put("1", new Image( "images/1.png" ) );
            images.put("2", new Image( "images/2.png" ) );
            images.put("3", new Image( "images/3.png" ) );
            images.put("4", new Image( "images/4.png" ) );
            /*images.put("5", new Image( "images/5.png" ) );
            images.put("6", new Image( "images/6.png" ) );
            images.put("7", new Image( "images/7.png" ) );
            images.put("8", new Image( "images/8.png" ) );*/
        } catch (CantReadFile ex) {
            System.err.println("Can´t Load Images");
        }
    }
    
    private Point getGameLocation() throws NoOpenGame{
        try {
            Point location;
            Image screenImg;
            
            screenImg = new Image( (Rectangle)null );
            location = screenImg.findSubImage(images.get("base"));
            
            if(location != null)
                return location;
            throw new NoOpenGame();
        } catch (CantCaptureScreen ex) {
            System.err.println("Can´t Load Screen");
            return null;
        }
    }
    
    private Point getMapLocation() throws GameIsLocked {
        try {
            Point location;
            Image screenImg;
            
            screenImg = new Image( new Rectangle(gameLocation, gameDimension) );
            location = screenImg.findSubImage(images.get("map"));
            
            if(location != null)
                return location;
            throw new GameIsLocked();
        } catch (CantCaptureScreen ex) {
            System.err.println("Can´t Load Images");
            return null;
        }
    }
    
    private void updateMap(Point p) throws GameIsLocked {
        try {
            Image screenImg;
            Point cellPosition;
            
            int cellPositionX = (int) (gameLocation.x + mapLocation.x + p.x*cellDimension.getWidth());
            int cellPositionY = (int) (gameLocation.y + mapLocation.y + p.y*cellDimension.getHeight());
            cellPosition = new Point(cellPositionX, cellPositionY);
            
            screenImg = new Image( new Rectangle(cellPosition, cellDimension) );
            
            if(screenImg.findSubImage(images.get("1"))!=null )
                map[p.x][p.y] = 1;
            else if(screenImg.findSubImage(images.get("2"))!=null )
                map[p.x][p.y] = 2;
            else if(screenImg.findSubImage(images.get("3"))!=null )
                map[p.x][p.y] = 3;
            else if(screenImg.findSubImage(images.get("4"))!=null )
                map[p.x][p.y] = 4;
            /*else if(screenImg.findSubImage(symbols[5])!=null )
            map[p.x][p.y] = 5;
            else if(screenImg.findSubImage(symbols[6])!=null )
            map[p.x][p.y] = 6;
            else if(screenImg.findSubImage(symbols[7])!=null )
            map[p.x][p.y] = 7;
            else if(screenImg.findSubImage(symbols[8])!=null )
            map[p.x][p.y] = 8;*/
            else if(screenImg.findSubImage(images.get("fly"))!=null )
                map[p.x][p.y] = 9;
            else if(screenImg.findSubImage(images.get("fly2"))!=null )
                map[p.x][p.y] = 9;
            else if(screenImg.findSubImage(images.get("cell"))!=null )
                throw new GameIsLocked();
            else{
                map[p.x][p.y] = 0;
                for(int k=p.x-1; k<=p.x+1; k++)for(int l=p.y-1; l<=p.y+1; l++){
                    if(k>=0 && k<8 && l>=0 && l<9){
                        if(map[k][l]==-1)
                            updateMap( new Point(k,l) );
                    }
                }
            }
        } catch (CantCaptureScreen ex) {
            System.err.println("Can´t Load Images");
        }
    }
}
