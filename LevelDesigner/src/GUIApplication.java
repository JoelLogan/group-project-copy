
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        var scene = new Scene(loader.load());

        MainWindow mainWindow = loader.getController();
        mainWindow.initialize(stage);

        stage.setResizable(false);

        stage.setScene(scene);
        stage.setTitle("BJU Beats - Level Designer"); // Title of main window
        stage.show();
    }

}