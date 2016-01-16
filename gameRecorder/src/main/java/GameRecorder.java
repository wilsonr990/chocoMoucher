
import controllers.MainController;
import views.MainView;

public class GameRecorder {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        MainController controller = new MainController(mainView);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}