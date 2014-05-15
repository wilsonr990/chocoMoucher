/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chocomoucher;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author wilsonr
 */

public class Image {
    BufferedImage buffer;
    
    Image( Rectangle screenRectangle ) throws CantCaptureScreen {
        try {
            Robot r = new Robot();
            if( screenRectangle == null ){
                Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
                buffer = r.createScreenCapture( new Rectangle(dimScreen) );
            }
            else{
                buffer = r.createScreenCapture( screenRectangle );
            }
        } catch (AWTException ex) {
            throw new CantCaptureScreen();
        }
    }
    
    Image( String url ) throws CantReadFile {
        try {
            buffer = ImageIO.read(new FileInputStream(url));
        } catch (IOException ex) {
            throw new CantReadFile();
        }
    }
    
    public int getWidth() {
        return buffer.getWidth();
    }

    public int getHeight() {
        return buffer.getHeight();
    }

    public int getRGB(int x, int y) {
        return buffer.getRGB(x, y);
    }
    
    public Dimension getDimension() {
        return new Dimension( buffer.getWidth(), buffer.getHeight() );
    }
    
    public Point findSubImage( Image img ) {
        int validatedRows = 0;
        int pixelsOfScreenToExplore;
        int imgSize = img.getWidth()*img.getHeight();
        int widthToExplore = buffer.getWidth() - img.getWidth();
        int heigthToExplore = buffer.getHeight() - img.getHeight();
        pixelsOfScreenToExplore = widthToExplore*heigthToExplore;
        for (int i = 0; i < pixelsOfScreenToExplore; i++) {
            int X = i % widthToExplore;
            int Y = i / widthToExplore;
            for (int j = 0; j <imgSize; j++) {
                int x = j % img.getWidth();
                int y = j / img.getWidth();
                int screenRGB = buffer.getRGB(X+x, Y+y);
                int idRGB = img.getRGB(x,y);
                if( idRGB == new Color(0,0,0).getRGB() )
                    continue;
                if (screenRGB == idRGB) {
                    if (x==img.getWidth()-1) {
                        validatedRows++;
                        if (validatedRows == img.getHeight()) {
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
}
