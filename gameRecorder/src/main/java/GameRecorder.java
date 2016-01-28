
import controllers.MainController;
import models.RecorderLogic;
import views.MainView;

public class GameRecorder {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        RecorderLogic recorder = new RecorderLogic();
        MainController controller = new MainController(mainView, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}