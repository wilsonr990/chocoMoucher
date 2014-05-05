
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
    private Point gameLocation;
    private int gameHeigth;
    private int gameWidth;

    ChocoMoucher(){
        try {
            r = new Robot();
            
            chocoMoucheImg = getGameInstance();
            if( chocoMoucheImg !=null ){
                gameWidth = chocoMoucheImg.getWidth();
                gameHeigth = chocoMoucheImg.getHeight();
            }
        } catch (AWTException ex) {
            Logger.getLogger(ChocoMoucher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void Start(){
        if( chocoMoucheImg !=null ){
            try {
                r.mouseMove(gameLocation.x + (gameWidth>>1), gameLocation.y + (gameHeigth>>1));
                r.mousePress(BUTTON1_DOWN_MASK);
		Thread.sleep(100);
                r.mouseRelease(BUTTON1_DOWN_MASK);
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
        
        screenImg = getScreenImage();
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
            Logger.getLogger(ChocoMoucher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private BufferedImage getScreenImage() {
        BufferedImage image;
        Dimension dimScreen;
        dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screen = new Rectangle(dimScreen);
        image = r.createScreenCapture( screen );
        return image;
    }
}
