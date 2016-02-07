package models.impl;

import Exceptions.*;
import Image.ImageHolder;
import models.GameHandler;

import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author wilsonr
 */

public class ChocoMouche extends GameHandler {
    private int lives;
    private int turnPercentage;
    private HashMap<String, ImageHolder> images;
    private int[][] map;

    public ChocoMouche() {
        ResetGameVariables();
        printedMap = "CHOCOMOUCHE";
    }

    @Override
    protected void ResetGameVariables() {
        map = new int[8][9];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                map[i][j] = -1;
            }
        }
        turnPercentage = 100;
        lives = 3;
        images = new HashMap<String, ImageHolder>();
        readImages();
    }

    @Override
    protected void UpdateGameVariables(ImageHolder image) throws CantReadFile {
        ArrayList<ImageHolder> masked = image.getMaskedImages(new ImageHolder("mask.png"));

        lives = getLivesValue(masked.remove(74), masked.remove(65), masked.remove(56));
        turnPercentage = getTurnPercentageValue(masked.remove(72));
        for (int i = 0; i < 72; i++) {
            int x = i % 8;
            int y = i / 8;
            int imageValue = getImageValue(masked.get(i));
            if (map[x][y] == -1)
                if (imageValue == 0)
                    map[x][y] = 0;
                else
                    map[x][y] = imageValue;
            if (map[x][y] == 0)
                map[x][y] = imageValue;
        }
        printedMap = "lives: " + lives + "\n";
        printedMap += "turnPercentage: " + turnPercentage + "\n";
        for (int i = 0; i < 72; i++) {
            int x = i % 8;
            int y = i / 8;
            if (x == 0)
                printedMap += "\n";
            if (x >= 0)
                printedMap += " ";
            printedMap += map[x][y];
        }
        System.out.println(printedMap);
    }

    public int[][] getMap() {
        return map;
    }

    private void readImages() {
        try {
            images.put("f1", new ImageHolder("chocomouche/f1.png"));
            images.put("f2", new ImageHolder("chocomouche/f2.png"));
            images.put("f3", new ImageHolder("chocomouche/f3.png"));
            images.put("f4", new ImageHolder("chocomouche/f4.png"));
            images.put("a", new ImageHolder("chocomouche/a.png"));
            images.put("c", new ImageHolder("chocomouche/c.png"));
            images.put("l", new ImageHolder("chocomouche/l.png"));
            images.put("1", new ImageHolder("chocomouche/1.png"));
            images.put("2", new ImageHolder("chocomouche/2.png"));
            images.put("3", new ImageHolder("chocomouche/3.png"));
            images.put("4", new ImageHolder("chocomouche/4.png"));
            /*images.put("5", new Image.Image( "images/5.png" ) );
            images.put("6", new Image.Image( "images/6.png" ) );
            images.put("7", new Image.Image( "images/7.png" ) );
            images.put("8", new Image.Image( "images/8.png" ) );*/
        } catch (CantReadFile ex) {
            System.err.println("Can´t Load Images");
        }
    }

    private int getImageValue(ImageHolder p) {
        if (p.findSubImage(images.get("1")) != null
                || p.findSubImage(images.get("l")) != null)
            return 1;
        else if (p.findSubImage(images.get("2")) != null)
            return 2;
        else if (p.findSubImage(images.get("3")) != null)
            return 3;
        else if (p.findSubImage(images.get("4")) != null)
            return 4;
            /*else if(screenImg.findSubImage(symbols[5])!=null )
            return 5;
            else if(screenImg.findSubImage(symbols[6])!=null )
            return 6;
            else if(screenImg.findSubImage(symbols[7])!=null )
            return 7;
            else if(screenImg.findSubImage(symbols[8])!=null )
            return 8;*/
        else if (p.findSubImage(images.get("f1")) != null
                || p.findSubImage(images.get("f2")) != null
                || p.findSubImage(images.get("f4")) != null
                || p.findSubImage(images.get("f3")) != null)
            return 9;
        else if (p.findSubImage(images.get("c")) != null
                || p.findSubImage(images.get("a")) != null)
            return -1;
        else {
            return 0;
        }
    }

    private int getTurnPercentageValue(ImageHolder bar) {
        int x = 0;
        int baseColor = new Color(bar.getRGB(0, 2)).getBlue();
        while (++x < bar.getWidth()) {
            if (baseColor - new Color(bar.getRGB(x, 2)).getBlue() > 50)
                break;
        }
        return (x * 100) / bar.getWidth();
    }

    private int getLivesValue(ImageHolder l1, ImageHolder l2, ImageHolder l3) {
        return getImageValue(l1) + getImageValue(l2) + getImageValue(l3);
    }
}
