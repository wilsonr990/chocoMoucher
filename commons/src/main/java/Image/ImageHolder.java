package Image;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Exceptions.CantCaptureScreen;
import Exceptions.ErrorInImageResources;
import Exceptions.FileAlreadyExists;

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
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * @author wilsonr
 */

public class ImageHolder {
    BufferedImage buffer;

    public ImageHolder(Rectangle screenRectangle) throws CantCaptureScreen {
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

    public ImageHolder(String url) throws ErrorInImageResources {
        try {
            URL resource = getClass().getClassLoader().getResource(url);
            if (resource != null) {
                buffer = ImageIO.read(new FileInputStream(new File(resource.getFile())));
            } else {
                throw new ErrorInImageResources();
            }
        } catch (FileNotFoundException e) {
            throw new ErrorInImageResources();
        } catch (IOException e) {
            throw new ErrorInImageResources();
        }
    }

    public ImageHolder(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public ImageHolder(int w, int h) {
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    public ImageHolder(File file) throws ErrorInImageResources {
        try {
            buffer = ImageIO.read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ErrorInImageResources();
        } catch (IOException e) {
            throw new ErrorInImageResources();
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

    public void setRGB(int x, int y, int rgb) {
        buffer.setRGB(x, y, rgb);
    }

    public Dimension getDimension() {
        return new Dimension(buffer.getWidth(), buffer.getHeight());
    }

    public Point findSubImage(ImageHolder img) {
        int widthDifference = buffer.getWidth() - img.getWidth() + 1;
        int heigthDifference = buffer.getHeight() - img.getHeight() + 1;
        if (widthDifference <= 0 || heigthDifference <= 0)
            return null;

        int validatedPixels = 0;
        int imgSize = img.getWidth() * img.getHeight();
        for (int i = 0; i < widthDifference * heigthDifference; i++) {
            int X = i % widthDifference;
            int Y = i / widthDifference;

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

    public void saveImage(String name) throws IOException, FileAlreadyExists {
        File outputfile = new File(name);
        if (outputfile.isFile()) {
            throw new FileAlreadyExists();
        }
        ImageIO.write(buffer, "png", outputfile);
    }

    public void getSubImage(Rectangle rectangle) {
        buffer = buffer.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public ImageHolder difference(ImageHolder img) {
        if (img == null) return this;
        ImageHolder result = new ImageHolder(buffer.getWidth(), buffer.getHeight());

        int imgSize = buffer.getWidth() * buffer.getHeight();
        for (int j = 0; j < imgSize; j++) {
            int x = j % buffer.getWidth();
            int y = j / buffer.getWidth();

            int rgbDiff = buffer.getRGB(x, y) - img.getRGB(x, y);
            if (rgbDiff == 0)
                result.setRGB(x, y, 0);
            else
                result.setRGB(x, y, buffer.getRGB(x, y));
        }
        return result;
    }

    public ImageHolder Similarity(ImageHolder img) {
        if (img == null) return this;
        ImageHolder result = new ImageHolder(buffer.getWidth(), buffer.getHeight());

        int imgSize = buffer.getWidth() * buffer.getHeight();
        for (int j = 0; j < imgSize; j++) {
            int x = j % buffer.getWidth();
            int y = j / buffer.getWidth();

            if (buffer.getRGB(x, y) == img.getRGB(x, y))
                result.setRGB(x, y, buffer.getRGB(x, y));
            else
                result.setRGB(x, y, 0);
        }
        return result;
    }

    public ArrayList<ImageHolder> getMaskedImages(ImageHolder mask) {
        ArrayList<ImageHolder> result = new ArrayList<ImageHolder>();
        if (mask == null) return result;

        int imgSize = mask.getWidth() * mask.getHeight();
        for (int j = 0; j < imgSize; j++) {
            int x = j % mask.getWidth();
            int y = j / mask.getWidth();

            if (mask.getRGB(x, y) == Color.WHITE.getRGB()) {
                if (mask.getRGB(x + 1, y) == Color.BLACK.getRGB() && mask.getRGB(x, y + 1) == Color.BLACK.getRGB()) {
                    int w = 1, h = 1;
                    while (true) {
                        if (mask.getRGB(x - w - 1, y - h - 1) == Color.WHITE.getRGB()) {
                            w++;
                            h++;
                        } else if (mask.getRGB(x - w - 1, y - h) == Color.WHITE.getRGB())
                            w++;
                        else if (mask.getRGB(x - w, y - h - 1) == Color.WHITE.getRGB())
                            h++;
                        else
                            break;
                    }
                    result.add(new ImageHolder(buffer.getSubimage(x - w, y - h, w, h)));
                }
            }
        }
        return result;
    }
}
