
import controller.PlayerController;
import models.*;
import models.impl.ChocomouchePlayer;
import models.impl.ChocoMouche;
import views.PlayerView;

public class MainPlayer {
    public static void main(String[] args) {
        PlayerView mainView = new PlayerView();

        Game game = new ChocoMouche();
        Player player = new ChocomouchePlayer(game, new GameInterface());

        PlayerModel playerModel = new PlayerModel(game, player);
        Recorder recorderModel = new Recorder(game);
        recorderModel.setGameToRecord("LIVE");

        PlayerController controller = new PlayerController(mainView, game, playerModel, recorderModel);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}
