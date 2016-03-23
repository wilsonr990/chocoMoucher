package models;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 2/7/2016.
 */
public abstract class BasicPlayer implements Player {
    private boolean decided;
    private boolean updated;
    private boolean moved;

    public enum Status {
        WaitingGame, UpdatingGame, DecidingMove, Moving
    }

    protected Game game;
    private Status gameState;

    public BasicPlayer(Game game) {
        decided = false;
        updated = false;
        moved = false;
        this.game = game;
        this.gameState = Status.WaitingGame;
    }

    public void play() {
        if (gameState == Status.UpdatingGame) {
            updated = update();
        } else if (gameState == Status.DecidingMove) {
            decided = getBestMoves();
        } else if (gameState == Status.Moving) {
            moved = move();
        }
        updateStatus();
    }

    public String getPlayerStatus() {
        return String.valueOf(gameState);
    }

    private void updateStatus() {
        switch (gameState) {
            case WaitingGame:
                if (game.playingGame()) {
                    updated = false;
                    gameState = Status.UpdatingGame;
                }
                break;
            case UpdatingGame:
                if (!game.playingGame())
                    gameState = Status.WaitingGame;
                else if (updated) {
                    decided = false;
                    gameState = Status.DecidingMove;
                }
                break;
            case DecidingMove:
                if (!game.playingGame())
                    gameState = Status.WaitingGame;
                else if (decided) {
                    gameState = Status.Moving;
                } else
                    gameState = Status.UpdatingGame;
                break;
            case Moving:
                if (!game.playingGame())
                    gameState = Status.WaitingGame;
                else if (moved)
                    gameState = Status.UpdatingGame;
                break;
            default:
                gameState = Status.WaitingGame;
        }
    }
}
