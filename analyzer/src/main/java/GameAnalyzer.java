
import controllers.AnalyzerController;
import models.Analyzer;
import views.AnalyzerView;

public class GameAnalyzer {
    public static void main(String[] args) {
        AnalyzerView mainView = new AnalyzerView();
        Analyzer analyzer = new Analyzer();
        AnalyzerController controller = new AnalyzerController(mainView, analyzer);

        mainView.setController(controller);
        mainView.setVisible(true);
    }
}