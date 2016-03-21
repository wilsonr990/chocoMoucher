package models;

import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 2/7/2016.
 */
public abstract class BasicPlayer implements Player {
    public enum Status {
        DetectingGame, UpdatingGame, DecidingMove, Moving
    }

    protected Game game;
    private Status gameState;

    public BasicPlayer(Game game) {
        this.game = game;
        this.gameState = Status.DetectingGame;
    }

    public void play() {
        updateStatus();
        if (gameState == Status.UpdatingGame) {
            Map<Object, Object> gameProperties = game.getGameProperties();
            update(gameProperties);
        }
        else if(gameState == Status.DecidingMove) {
            decideMove();
        }
        else if(gameState == Status.Moving) {
            move();
        }
    }

    private void updateStatus() {
        switch (gameState) {
            case DetectingGame:
                if (game.gameDetected() && !game.gameEnded())
                    gameState = Status.UpdatingGame;
                break;
            case UpdatingGame:
                if (game.gameEnded())
                    gameState = Status.DetectingGame;
                else
                    gameState = Status.DecidingMove;
                break;
            case DecidingMove:
                if (game.gameEnded())
                    gameState = Status.DetectingGame;
                else
                    gameState = Status.Moving;
                break;
            case Moving:
                if (game.gameEnded())
                    gameState = Status.DetectingGame;
                else
                    gameState = Status.UpdatingGame;
                break;
            default:
                gameState = Status.DetectingGame;
        }
    }
}
