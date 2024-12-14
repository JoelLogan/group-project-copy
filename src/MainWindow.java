import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import model.MusicGame;

public class MainWindow {

    @FXML
    private VBox vboxHelpScreen, vboxAboutScreen;
    @FXML
    private TextArea txtAreaAbout, txtAreaHelp;
    private boolean gameOpen = false;

    public void initialize() {
        initializeAboutText();
        initializeHelpText();
    }

    @SuppressWarnings("unused")
    @FXML
    void onStartClicked(ActionEvent event) throws Exception {
        if (gameOpen) {
            return;
        }
        gameOpen = true;
        var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        var scene = new Scene(loader.load());

        var stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("BJU Beats - Game");
        stage.getIcons().add(new Image("./media/SmallLogo.png"));

        stage.setOnCloseRequest(e -> {
            ((GameWindow) loader.getController()).game.pauseLevel();
            MusicGame.getInstance().reset();
            gameOpen = false;
        });

        stage.show();
    }

    @FXML
    void onAboutClicked(ActionEvent event) {
        fadeView(vboxAboutScreen, true);
    }

    @FXML
    void onCloseAboutClicked(ActionEvent event) {
        fadeView(vboxAboutScreen, false);
    }

    @FXML
    void onHelpClicked(ActionEvent event) {
        fadeView(vboxHelpScreen, true);
    }

    @FXML
    void onCloseHelpClicked(ActionEvent event) {
        fadeView(vboxHelpScreen, false);
    }

    private void initializeAboutText() {
        StringBuilder aboutText = new StringBuilder();
        aboutText.append("Developers:").append(System.lineSeparator())
                 .append("                    · Joel Logan").append(System.lineSeparator())
                 .append("                    · Josiah Zempel").append(System.lineSeparator())
                 .append("                    · Bob Lee").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("Logo.png - Modified Template From @dstudioo On Canva").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("XP.png | Source: https://www.flaticon.com/free-icon/double-point_5659484").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("FREE_MISS.png | Source: https://www.flaticon.com/free-icon/no-music-sign-of-musical-note-with-a-slash_40372").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("ROTATE.png | Source: https://iconduck.com/icons/298505/rotate-camera").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("Track1 - Cinematic Experience by Alex-Productions | https://onsound.eu/").append(System.lineSeparator())
                 .append("Music promoted by https://www.free-stock-music.com").append(System.lineSeparator())
                 .append("Creative Commons / Attribution 3.0 Unported License (CC BY 3.0)").append(System.lineSeparator())
                 .append("https://creativecommons.org/licenses/by/3.0/deed.en_US").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("Track2 - Funny Bubbles by Keys of Moon | https://soundcloud.com/keysofmoon").append(System.lineSeparator())
                 .append("Music promoted by https://www.free-stock-music.com").append(System.lineSeparator())
                 .append("Creative Commons / Attribution 4.0 International (CC BY 4.0)").append(System.lineSeparator())
                 .append("https://creativecommons.org/licenses/by/4.0/").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("Track3 - Three Sheets To The Wind by Scott Buckley |").append(System.lineSeparator())
                 .append("https://soundcloud.com/scottbuckley").append(System.lineSeparator())
                 .append("Music promoted by https://www.free-stock-music.com").append(System.lineSeparator())
                 .append("Creative Commons / Attribution 4.0 International (CC BY 4.0)").append(System.lineSeparator())
                 .append("https://creativecommons.org/licenses/by/4.0/").append(System.lineSeparator()).append(System.lineSeparator())
                 .append("BackgroundMusic - New Heights by Corporate Music Zone |").append(System.lineSeparator())
                 .append("https://corporate-music-zone.bandcamp.com").append(System.lineSeparator())
                 .append("Music promoted by https://www.free-stock-music.com").append(System.lineSeparator());
        txtAreaAbout.setText(aboutText.toString());
    }

    private void initializeHelpText() {
        StringBuilder helpText = new StringBuilder();
        helpText.append("Game Instructions").append(System.lineSeparator()).append(System.lineSeparator())
                .append(" Click the Start Game button. Select your level and the notes from the song will start moving towards you. ")
                .append(" Select the key on your number pad that corresponds to the box on screen whenever it is touching the front boxes on screen. ")
                .append(" If you push/hold the button at that right time, you will earn points, but if you press at the wrong time, you will lose points. ")
                .append(" You can use ESC to pause or leave the level. But you press ESC again when game is paused, the music and game will resume. ")
                .append(System.lineSeparator())
                .append(" There are three built-in levels (easy, medium, and hard). ")
                .append(" Choose the level you want to play and get the power-up items during the game. ")
                .append(" They may change your perspective, double incoming scores, and cover your misclicks. ")
                .append(" Check your score after playing game and challenge yourself for higher scores!")
                .append(System.lineSeparator())
                .append(" If you want to create your own level, you can do so by opening the LevelDesigner project.")
                .append(System.lineSeparator())
                .append(" Enjoy the game and have fun!").append(System.lineSeparator());
        txtAreaHelp.setText(helpText.toString());
    }

    @SuppressWarnings("unused")
    private void fadeView(Node node, boolean fadeIn) {
        double changeInOpacity;
        if (fadeIn) {
            node.setVisible(true);
            changeInOpacity = 0.01;
        } else {
            changeInOpacity = -0.01;
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5),
                i -> {
                    node.setOpacity(node.getOpacity() + changeInOpacity);
                    if (node.getOpacity() <= 0.001) {
                        node.setVisible(false);
                    }
                }));
        timeline.setCycleCount(100);
        timeline.play();
    }
}