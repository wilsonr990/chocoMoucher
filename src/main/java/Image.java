/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author wilsonr
 */

public class Image {
    BufferedImage buffer;

    Image(Rectangle screenRectangle) throws CantCaptureScreen {
        try {
            Robot r = new Robot();
            if (screenRectangle == null) {
                Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
                buffer = r.createScreenCapture(new Rectangle(dimScreen));
            } else {
                buffer = r.createScreenCapture(screenRectangle);
            }
        } catch (AWTException ex) {
            throw new CantCaptureScreen();
        }
    }

    Image(String url) throws CantReadFile {
        try {
            URL resource = getClass().getClassLoader().getResource(url);
            if (resource != null) {
                buffer = ImageIO.read(new FileInputStream(new File(resource.getFile())));
            } else {
                throw new CantReadFile();
            }
        } catch (FileNotFoundException e) {
            throw new CantReadFile();
        } catch (IOException e) {
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
        return new Dimension(buffer.getWidth(), buffer.getHeight());
    }

    public Point findSubImage(Image img) {
        int widthDiference = buffer.getWidth() - img.getWidth();
        int heigthDiference = buffer.getHeight() - img.getHeight();
        if (widthDiference < 0 || heigthDiference < 0)
            return null;

        int validatedPixels = 0;
        int imgSize = img.getWidth() * img.getHeight();
        for (int i = 0; i < widthDiference * heigthDiference; i++) {
            int X = i % widthDiference;
            int Y = i / widthDiference;
            for (int j = 0; j < imgSize; j++) {
                int x = j % img.getWidth();
                int y = j / img.getWidth();

                if (buffer.getRGB(X + x, Y + y) == img.getRGB(x, y)
                        || img.getRGB(x, y) == Color.BLACK.getRGB()) {
                    validatedPixels++;
                    if (validatedPixels == imgSize)
                        return new Point(X, Y);
                } else if (img.getRGB(x, y) != Color.BLACK.getRGB()) {
                    validatedPixels = 0;
                    break;
                }
            }
        }
        return null;
    }

    public void saveImage(String name) throws IOException {
        File outputfile = new File(name);
        ImageIO.write(buffer, "png", outputfile);
    }
}
