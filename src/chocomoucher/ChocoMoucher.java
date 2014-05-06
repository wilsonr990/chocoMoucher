
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author wilsonr
 */
public class ChocoMoucher{
    private Robot r;
    private BufferedImage chocoMoucheImg;
    private Point gameLocation, mapLocation;
    private char[] map;
    BufferedImage[] symbols;
    private Dimension mapDimension, gameDimension;

    ChocoMoucher(){
        symbols = new BufferedImage[12];
        symbols[0] = readImage( "images/cell.png" );
        symbols[1] = readImage( "images/1.png" );
        symbols[2] = readImage( "images/2.png" );
        symbols[3] = readImage( "images/3.png" );
        symbols[4] = readImage( "images/4.png" );
        symbols[5] = readImage( "images/5.png" );
        symbols[6] = readImage( "images/6.png" );
        symbols[7] = readImage( "images/7.png" );
        symbols[8] = readImage( "images/8.png" );
        symbols[10] = readImage( "images/!.png" );
        symbols[11] = readImage( "images/fly.png" );
        try {
            r = new Robot();
            
            chocoMoucheImg = getGameInstance();
            if( chocoMoucheImg !=null ){
                gameDimension = new Dimension( chocoMoucheImg.getWidth(), chocoMoucheImg.getHeight());
            }
        } catch (AWTException ex) {
            Logger.getLogger(ChocoMoucher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void Start(){
        if( chocoMoucheImg !=null ){
            try {
                r.mouseMove(gameLocation.x + (gameDimension.width>>1), gameLocation.y + (gameDimension.height>>1));
                r.mousePress(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                r.mouseRelease(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                
                initMap();
                
                if( map != null ){
                    int count = 0;
                    for (char cell: map){
                        System.out.print( cell + ", " );
                        if(++count == 8){
                            count=0;
                            System.out.print( "\n" );
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ChocoMoucher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("Game is Not Openned");
        }
    }
    
    void initMap(){
        if( chocoMoucheImg !=null ){
            BufferedImage screenImg, mapImage;
            
            screenImg = getScreenImage( new Rectangle(gameLocation, gameDimension) );
            mapImage = readImage( "images/map.png" );
            
            mapDimension = new Dimension(mapImage.getWidth(), mapImage.getHeight());
            
            Point p = getSubImageLocation(mapImage, screenImg);
            if(p != null){
                mapLocation = p;
                map = new char[72];
            }
        }
        else{
            System.out.println("Game is Not Openned");
        }
    }
    
    private void updateMap(Point p) {
        if( chocoMoucheImg !=null ){
            BufferedImage screenImg;

            Point relative;
            relative = new Point(gameLocation.x + mapLocation.x + p.x*mapDimension.width/8, gameLocation.y + mapLocation.y + p.y*mapDimension.height/9);

            screenImg = getScreenImage( new Rectangle(relative, new Dimension(mapDimension.width/8, mapDimension.height/9)) );

            if(getSubImageLocation(symbols[1], screenImg)!=null )
                map[p.x+p.y*8] = '1';
            else if(getSubImageLocation(symbols[2], screenImg)!=null )
                map[p.x+p.y*8] = '2';
            else if(getSubImageLocation(symbols[3], screenImg)!=null )
                map[p.x+p.y*8] = '3';/*
            else if(getSubImageLocation(symbols[4], screenImg)!=null )
                map[p.x+p.y*8] = '4';
            else if(getSubImageLocation(symbols[5], screenImg)!=null )
                map[p.x+p.y*8] = '5';
            else if(getSubImageLocation(symbols[6], screenImg)!=null )
                map[p.x+p.y*8] = '6';
            else if(getSubImageLocation(symbols[7], screenImg)!=null )
                map[p.x+p.y*8] = '7';
            else if(getSubImageLocation(symbols[8], screenImg)!=null )
                map[p.x+p.y*8] = '8';*/
            else if(getSubImageLocation(symbols[11], screenImg)!=null )
                map[p.x+p.y*8] = '9';
            else{
                map[p.x+p.y*8] = 'p';
                for(int k=p.x-1; k<=p.x+1; k++)for(int l=p.y-1; l<=p.y+1; l++){
                    if(k>=0 && k<8 && l>=0 && l<9){
                        if(map[k+l*8]==(char)(0))
                            updateMap( new Point(k,l) );
                    }
                }
            }
        }
        else{
            System.out.println("Game is Not Openned");
        }
    }
    
    void clickOn(Point p){
        if( chocoMoucheImg !=null ){
            try {
                r.mouseMove(gameLocation.x + mapLocation.x + p.x*mapDimension.width/8 + mapDimension.width/16, gameLocation.y + mapLocation.y + p.y*mapDimension.height/9 + mapDimension.height/18);
                r.mousePress(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                r.mouseRelease(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                r.mousePress(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                r.mouseRelease(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                
                updateMap( p );
                
                if( map != null ){
                    int count = 0;
                    for (char cell: map){
                        System.out.print( cell + ", " );
                        if(++count == 8){
                            count=0;
                            System.out.print( "\n" );
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ChocoMoucher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("Game is Not Openned");
        }
    }
    
    private BufferedImage getGameInstance(){
        BufferedImage screenImg;
        BufferedImage idImage;
        
        screenImg = getScreenImage( null );
        idImage = readImage( "images/idImage.png" );
        
        Point p = getSubImageLocation(idImage, screenImg);
        if(p != null){
            gameLocation = p;
            return screenImg.getSubimage(p.x, p.y, idImage.getWidth(), idImage.getHeight());
        }
        return null;
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

    private BufferedImage readImage( String url) {
        try {
            Graphics g;
            BufferedImage idImage;
            
            idImage = ImageIO.read(new FileInputStream(url));
            return idImage;
        } catch (IOException ex) {
            System.out.println("Cannot read: " + url);
        }
        return null;
    }

    private BufferedImage getScreenImage( Rectangle screenRectangle ) {
        BufferedImage image;
        Rectangle screenRect;
        if( screenRectangle == null ){
            Dimension dimScreen;
            dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
            screenRect = new Rectangle(dimScreen);
        }
        else{
            screenRect = screenRectangle;
        }
        image = r.createScreenCapture( screenRect );
        return image;
    }
}
