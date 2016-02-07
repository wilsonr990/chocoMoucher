
import controllers.RecorderController;
import models.Recorder;
import models.impl.ChocoMouche;
import views.RecorderView;

public class MainRecorder {
    public static void main(String[] args) {
        RecorderView mainView = new RecorderView();
        Recorder recorder = new Recorder(new ChocoMouche());
        RecorderController controller = new RecorderController(mainView, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}