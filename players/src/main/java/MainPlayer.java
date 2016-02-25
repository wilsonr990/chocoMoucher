
import controller.PlayerController;
import models.GameInterface;
import models.Recorder;
import models.ChocomoucherAnalizer;
import models.Player;
import models.impl.ChocoMouche;
import views.PlayerView;

public class MainPlayer {
    public static void main(String[] args) {
        PlayerView mainView = new PlayerView();

        ChocoMouche chocoMouche = new ChocoMouche();
        Player player = new ChocomoucherAnalizer(chocoMouche, new GameInterface());
        Recorder recorder = new Recorder(chocoMouche);
        recorder.setGameToRecord("LIVE");
        PlayerController controller = new PlayerController(mainView, player, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}
