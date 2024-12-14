
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class GUIApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
      
        var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        var scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("BJU Beats - Launcher"); // Title of main window
        stage.getIcons().add(new Image("./media/SmallLogo.png"));
        stage.show();
    }

}