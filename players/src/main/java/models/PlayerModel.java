package models;

import Exceptions.CantCaptureScreen;
import Exceptions.ErrorInImageResources;
import Image.ImageHolder;

import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 2/7/2016.
 */
public class PlayerModel {
    private final Player player;
    protected Game game;
    private Thread thread;

    public PlayerModel(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public void startPlaying() {
        System.out.println("Start");
        if (thread == null || !thread.isAlive()) {
            initializeThread();
        }
    }

    private void initializeThread() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    game.reset();
                    player.reset();
                    while (!isInterrupted()) {
                        ImageHolder image = new ImageHolder((Rectangle) null);
                        if (game.gameDetected()) {
                            image.getSubImage(new Rectangle(game.getLocation(), game.getDimension()));
                            player.play();
                        }
                        game.feed(image);
                        if (game.gameEnded()) {
                            showMessageDialog(null, "Game Ended or closed!");
                            break;
                        }
                    }
                } catch (CantCaptureScreen cantCaptureScreen) {
                    showMessageDialog(null, "Cant take screenshot!");
                } catch (ErrorInImageResources errorInImageResources) {
                    showMessageDialog(null, "Cant manage files!");
                }
                showMessageDialog(null, "Game Ended!");
                System.out.println("Stopped");
            }
        };
        thread.start();
    }

    public void stopPlaying() {
        System.out.println("Stop");
        thread.interrupt();
    }

    public String getGameStatus() {
        return game.getGameStatus()
                + "\n"
                + player.getPlayerStatus()
                + "\n"
                + game.getGameProperties().toString().replace(",", "\n")
                + game.getDrawableMap();
    }
}
