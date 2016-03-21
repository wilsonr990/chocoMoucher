package models;

import java.util.Map;

/**
 * Created by wilsonr on 2/24/2016.
 */
public interface Player {
    void play();

    void reset();

    void update(Map<Object, Object> gameProperties);

    void decideMove();

    void move();
}
