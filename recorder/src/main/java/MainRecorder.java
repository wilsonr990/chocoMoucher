
import controllers.RecorderController;
import models.GameHandler;
import models.Recorder;
import views.RecorderView;

public class MainRecorder {
    public static void main(String[] args) {
        RecorderView mainView = new RecorderView();
        Recorder recorder = new Recorder( new GameHandler() );
        RecorderController controller = new RecorderController(mainView, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}