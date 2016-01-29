
import controllers.RecorderController;
import models.Recorder;
import views.RecorderView;

public class GameRecorder {
    public static void main(String[] args) {
        RecorderView mainView = new RecorderView();
        Recorder recorder = new Recorder();
        RecorderController controller = new RecorderController(mainView, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}