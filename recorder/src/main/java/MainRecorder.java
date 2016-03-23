
import Exceptions.ErrorInImageResources;
import Image.ImageHolder;
import controllers.RecorderController;
import models.BasicGame;
import models.Recorder;
import models.impl.ChocoMouche;
import views.RecorderView;

import java.util.Map;

public class MainRecorder {
    public static void main(String[] args) {
        RecorderView mainView = new RecorderView();
        Recorder recorder = new Recorder(new BasicGame() {
            public void resetGameVariables() {
            }
            public void updateGameVariables(ImageHolder image) throws ErrorInImageResources {
            }
            public Map<Object, Object> getGameProperties() {
                return null;
            }
            public String getDrawableMap() {
                return "map";
            }
        });
        RecorderController controller = new RecorderController(mainView, recorder);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}