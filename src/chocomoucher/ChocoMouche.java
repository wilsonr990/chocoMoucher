
package chocomoucher;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import static java.awt.event.InputEvent.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author wilsonr
 */

public class ChocoMouche{
    private Robot r;
    private HashMap<String, BufferedImage> images;
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
            
            gameDimension = new Dimension( images.get("base").getWidth(), images.get("base").getHeight());
            mapDimension = new Dimension(images.get("map").getWidth(), images.get("map").getHeight());
            cellDimension = new Dimension(mapDimension.width/8, mapDimension.height/9);
        } catch (AWTException ex) {
            Logger.getLogger(ChocoMouche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Start() throws NoOpenGame, gameIsLocked{
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
        } catch (gameIsLocked ex) {
            throw new NoOpenGame();
        }
    }
    
    public void clickOn(Point p) throws NoOpenGame, gameIsLocked{
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
            images.put("base", readImage( "images/idImage.png" ) );
            images.put("map", readImage( "images/map.png" ) );
            images.put("fly", readImage( "images/fly.png" ) );
            images.put("fly2", readImage( "images/fly2.png" ) );
            images.put("cell", readImage( "images/cell.png" ) );
            images.put("1", readImage( "images/1.png" ) );
            images.put("2", readImage( "images/2.png" ) );
            images.put("3", readImage( "images/3.png" ) );
            /*images.put("4", readImage( "images/4.png" ) );
            images.put("5", readImage( "images/5.png" ) );
            images.put("6", readImage( "images/6.png" ) );
            images.put("7", readImage( "images/7.png" ) );
            images.put("8", readImage( "images/8.png" ) );*/
        } catch (IOException ex) {
            System.err.println("Can´t Load All Images");
        }
    }
    
    private Point getGameLocation() throws NoOpenGame{
        Point location;        
        BufferedImage screenImg;

        screenImg = getScreenImage( null );
        location = getSubImageLocation(images.get("base"), screenImg);

        if(location != null)
            return location;
        throw new NoOpenGame();
    }
    
    private Point getMapLocation() throws gameIsLocked {
        Point location;
        BufferedImage screenImg;
        
        screenImg = getScreenImage( new Rectangle(gameLocation, gameDimension) );
        location = getSubImageLocation(images.get("map"), screenImg);
        
        if(location != null)
            return location;
        throw new gameIsLocked();
    }
    
    private void updateMap(Point p) throws gameIsLocked {
        BufferedImage screenImg;
        Point cellPosition;
        
        int cellPositionX = (int) (gameLocation.x + mapLocation.x + p.x*cellDimension.getWidth());
        int cellPositionY = (int) (gameLocation.y + mapLocation.y + p.y*cellDimension.getHeight());
        cellPosition = new Point(cellPositionX, cellPositionY);

        screenImg = getScreenImage( new Rectangle(cellPosition, cellDimension) );

        if(getSubImageLocation(images.get("1"), screenImg)!=null )
            map[p.x][p.y] = 1;
        else if(getSubImageLocation(images.get("2"), screenImg)!=null )
            map[p.x][p.y] = 2;
        else if(getSubImageLocation(images.get("3"), screenImg)!=null )
            map[p.x][p.y] = 3;
        /*else if(getSubImageLocation(symbols[4], screenImg)!=null )
            map[p.x][p.y] = 4;
        else if(getSubImageLocation(symbols[5], screenImg)!=null )
            map[p.x][p.y] = 5;
        else if(getSubImageLocation(symbols[6], screenImg)!=null )
            map[p.x][p.y] = 6;
        else if(getSubImageLocation(symbols[7], screenImg)!=null )
            map[p.x][p.y] = 7;
        else if(getSubImageLocation(symbols[8], screenImg)!=null )
            map[p.x][p.y] = 8;*/
        else if(getSubImageLocation(images.get("fly"), screenImg)!=null )
            map[p.x][p.y] = 9;
        else if(getSubImageLocation(images.get("fly2"), screenImg)!=null )
            map[p.x][p.y] = 9;
        else if(getSubImageLocation(images.get("cell"), screenImg)!=null )
            throw new gameIsLocked();
        else{
            map[p.x][p.y] = 0;
            for(int k=p.x-1; k<=p.x+1; k++)for(int l=p.y-1; l<=p.y+1; l++){
                if(k>=0 && k<8 && l>=0 && l<9){
                    if(map[k][l]==-1)
                        updateMap( new Point(k,l) );
                }
            }
        }
    }

    private Point getSubImageLocation(BufferedImage idImage, BufferedImage screenImg) {
        int validatedRows = 0;
        int pixelsOfScreenToExplore;
        int idSize = idImage.getWidth()*idImage.getHeight();
        int widthToExplore = screenImg.getWidth() - idImage.getWidth();
        int heigthToExplore = screenImg.getHeight() - idImage.getHeight();
        pixelsOfScreenToExplore = widthToExplore*heigthToExplore;
        for (int i = 0; i < pixelsOfScreenToExplore; i++) {
            int X = i % widthToExplore;
            int Y = i / widthToExplore;
            for (int j = 0; j <idSize; j++) {
                int x = j % idImage.getWidth();
                int y = j / idImage.getWidth();
                int screenRGB = screenImg.getRGB(X+x, Y+y);
                int idRGB = idImage.getRGB(x,y);
                if( idRGB == new Color(0,0,0).getRGB() )
                    continue;
                if (screenRGB == idRGB) {
                    if (x==idImage.getWidth()-1) {
                        validatedRows++;
                        if (validatedRows == idImage.getHeight()) {
                            return new Point(X,Y);
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return null;
    }

    private BufferedImage readImage( String url) throws IOException {
        Graphics g;
        BufferedImage idImage;

        idImage = ImageIO.read(new FileInputStream(url));
        return idImage;
    }

    private BufferedImage getScreenImage( Rectangle screenRectangle ) {
        BufferedImage image;
        Rectangle screenRect;
        if( screenRectangle == null ){
            Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
            screenRect = new Rectangle(dimScreen);
        }
        else{
            screenRect = screenRectangle;
        }
        image = r.createScreenCapture( screenRect );
        return image;
    }
}
