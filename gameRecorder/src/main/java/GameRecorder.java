
import controllers.MainController;
import models.Recorder;
import views.MainView;

public class GameRecorder {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        Recorder recorder = new Recorder();
        MainController controller = new MainController(mainView, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}