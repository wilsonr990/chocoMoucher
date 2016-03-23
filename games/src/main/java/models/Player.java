package models;

/**
 * Created by wilsonr on 2/24/2016.
 */
public interface Player {
    void play();

    void reset();

    boolean update();

    boolean getBestMoves();

    boolean move();

    String getPlayerStatus();
}
