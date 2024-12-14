import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.MusicGame;
import model.Note;
import model.NoteProcessor;
import model.Song;
import model.enums.GameStates;
import model.enums.KeyCodes;
import model.observers.GameStatusObserver;
import model.observers.HudObserver;
import model.observers.NoteUpdateObserver;
import model.observers.PowerupObserver;

public class GameWindow implements GameStatusObserver, NoteUpdateObserver, HudObserver, PowerupObserver {

    @FXML
    private VBox vBoxLevelContainer, vBoxGameHUDContainer,
            vBoxMainMenu, vBoxInGameLogo, vBoxGameSubscene,
            vBoxPauseMenu, vBoxScoreMenu, vBoxMainMenuButtons;
    @FXML
    private HBox hBoxLevels;
    @FXML
    private Button btnLeft, btnRight;
    @FXML
    private ScrollPane spLevels;
    @FXML
    private Label lblInGameLevelName, lblScore, lblLevelCompletion, lblLevelDifficulty,
            lblLevelHighScore, lblFinalScore;
    @FXML
    private Region rgContainer;
    @FXML
    private StackPane stkGameContainer;
    @FXML
    private CheckBox chkCheatMode;
    @FXML
    Button btnPowerup1, btnPowerup2, btnPowerup3;
    private SubScene gameSubScene;
    private Group gameObjectGroup;
    private Timeline gameClock, logoFade;
    private int currentIndex = 0;
    private int scoreClockMS;
    private double gameClockMS;
    private HashMap<Note, Animation> noteAnimations = new HashMap<>();
    MusicGame game = MusicGame.getInstance();
    MusicPlayer musicPlayer = MusicPlayer.getInstance();

    // Array to make it easier to access the power-up buttons
    Button[] powerBtns = new Button[3];

    @SuppressWarnings("unused")
    public void initialize() {
        musicPlayer.startBackgroundMusic("BackgroundMusic.mp3");
        game.setGameStatusObserver(this);
        game.setNoteUpdateObserver(this);
        game.setHudObserver(this);
        game.getPowerupManager().setPowerupObserver(this);
        lblLevelDifficulty.setText("Difficulty: " + game.getSongManager().getSong(0).getDifficulty());
        lblLevelHighScore.setText("High Score: " + game.getSongManager().getSong(0).getHighScore());
        game.getSongManager().getSongs().forEach(s -> {
            Button button = new Button(s.getTitle());
            button.setOnAction(a -> {
                if (game.getState() != GameStates.PLAYING) {
                    game.startLevel(s.getId());
                    musicPlayer.stopBackgroundMusic();
                    musicPlayer.stopAndLoad(s.getSource(), () -> startGameClock(false),
                            () -> game.finishLevel());
                }
            });
            button.setPrefWidth(435);
            button.setMinHeight(100);
            button.setWrapText(true);
            button.setTextAlignment(TextAlignment.CENTER);
            hBoxLevels.getChildren().add(button);
        });
        gameSubScene = createSubScene();
        stkGameContainer.getChildren().add(0, gameSubScene);
        addDefaultBoxes();
        loadAndCheckSavedGame();
        powerBtns[0] = btnPowerup1;
        powerBtns[1] = btnPowerup2;
        powerBtns[2] = btnPowerup3;
    }

    @FXML
    void onViewLevels(ActionEvent event) {
        fadeView(vBoxLevelContainer, true);
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        game.processKeyPress(event.getCode().getCode());
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
        game.processKeyRelease(event.getCode().getCode());
    }

    @FXML
    private void onRightClicked() {
        if (currentIndex < hBoxLevels.getChildren().size() - 1) {
            currentIndex++;
            scrollToCurrentIndex();
        }
    }

    @FXML
    private void onLeftClicked() {
        if (currentIndex > 0) {
            currentIndex--;
            scrollToCurrentIndex();
        }
    }

    @SuppressWarnings("unused")
    private Button createLoadSavedLevelButton() {
        Button button = new Button("Load Save");
        button.getStyleClass().add("nice-buttons-smaller");
        button.setOnAction(a -> {
            if (game.getState() != GameStates.PLAYING && game.isSongSaved()) {
                game.getSongManager().loadAndStartSavedGame();
                musicPlayer.stopBackgroundMusic();
                musicPlayer.stopAndLoad(NoteProcessor.getInstance().getActiveSong().getSource(), () -> startGameClock(false),
                        () -> game.finishLevel());
            }
        });
        return button;
    }

    private void scrollToCurrentIndex() {
        double itemWidth = hBoxLevels.getChildren().get(0).getBoundsInParent().getWidth();
        double x = currentIndex * itemWidth;
        spLevels.setHvalue(x / (hBoxLevels.getWidth() - spLevels.getViewportBounds().getWidth()));
        lblLevelDifficulty.setText(
                "Difficulty: " + ((Song) game.getSongManager().getSongs().toArray()[currentIndex]).getDifficulty());
        setHighScoreText();
    }

    private void setHighScoreText() {
        lblLevelHighScore.setText(
                "High Score: " + ((Song) game.getSongManager().getSongs().toArray()[currentIndex]).getHighScore());
    }

    @FXML
    void onBackLevelClicked() {
        fadeView(vBoxLevelContainer, false);
    }

    @FXML
    void onExitLevel() {
        game.exitLevel();
    }

    @FXML
    void onSaveGameState(ActionEvent event) {
        game.saveGameForLater();
        game.exitLevel();
    }

    @FXML
    void onExitGame() {
        Stage stage = (Stage) vBoxMainMenu.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCheatChecked() {
        if (game.isCheatMode()) {
            game.setCheatMode(false);
        } else {
            game.setCheatMode(true);
        }
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

    @Override
    public void updateNoteGUI(List<Note> notes, boolean visible) {
        List<Node> nodesToRemove = new ArrayList<>();
        FilteredList<Node> guiNotes = gameObjectGroup.getChildren().filtered(n -> n.getUserData() instanceof Note);

        notes.forEach(note -> {
            if (guiNotes.filtered(guiNote -> guiNote.getUserData().equals(note)).isEmpty()) {
                Node box = addBox(note, 100, 100, false, true);
                Animation transition = animateNote(box);
                noteAnimations.put(note, transition);
                transition.play();
            }
        });

        for (Node guiNote : guiNotes) {
            guiNote.setVisible(visible);
            if (notes.stream().noneMatch(note -> note.equals(guiNote.getUserData()))) {
                nodesToRemove.add(guiNote);
                try {
                    noteAnimations.remove(noteAnimations.keySet().stream().filter(n -> n.equals(guiNote.getUserData())).findFirst()
                            .orElseThrow());
                } catch (NoSuchElementException ignored) {
                }
            }
        }

        gameObjectGroup.getChildren().removeAll(nodesToRemove);
    }

    @Override
    public void updateGUIStatus(GameStates status) {
        System.out.println("Game status: " + status + ". ");
        switch (status) {
            case PLAYING: // Go to game screen
                openGameGUI(false);
                game.getPowerupManager().clearPowerups();
                break;
            case RESUME:
                openGameGUI(true);
                startGameClock(true);
                noteAnimations.values().forEach(a -> a.play());
                musicPlayer.resumeTrack();
                break;
            case PAUSED: // Go to pause screen
                if (gameClock != null) {
                    gameClock.pause();
                }
                vBoxPauseMenu.setVisible(true);
                noteAnimations.values().forEach(a -> a.pause());
                musicPlayer.pauseTrack();
                musicPlayer.stopBackgroundMusic();
                break;
            case SCORES: // Go to score screen
                gameClock.stop();
                goToScoreMenu();
                break;
            case INACTIVE: // Go to main menu
                goToMainMenu();
                musicPlayer.stopTrack();
                musicPlayer.startBackgroundMusic("./src/media/BackgroundMusic.mp3");
                break;
            default:
                break;
        }
    }

    private void goToMainMenu() {
        if (gameClock != null) {
            gameClock.stop();
        }
        if (logoFade != null) {
            logoFade.stop();
        }
        loadAndCheckSavedGame();
        vBoxPauseMenu.setVisible(false);
        vBoxInGameLogo.setVisible(false);
        vBoxScoreMenu.setVisible(false);
        stkGameContainer.setVisible(false);
        stkGameContainer.setOpacity(0);
        vBoxScoreMenu.setOpacity(0);
    }

    private void loadAndCheckSavedGame() {
        boolean savedGame = game.updateSavedAndLoadGame(true);
        if (savedGame && vBoxMainMenuButtons.getChildren().size() == 4) {
            vBoxMainMenuButtons.getChildren().add(2, createLoadSavedLevelButton());
        }
        else if (!savedGame && vBoxMainMenuButtons.getChildren().size() == 5) {
            vBoxMainMenuButtons.getChildren().remove(2);
        }
    }

    private void goToScoreMenu() {
        lblFinalScore.setText("Score: " + game.getScoreManager().getScore());
        setHighScoreText();
        fadeView(vBoxScoreMenu, true);
    }

    @SuppressWarnings("unused")
    private void startGameClock(boolean resume) {
        System.out.println("Starting game clock");
        if (!resume) {
            if (game.getInitialTimecode() != 0) {
                gameClockMS = game.getInitialTimecode();
                musicPlayer.setCurrentTimeInMillis(game.getInitialTimecode());
                musicPlayer.startTrack();
            } else {
                gameClockMS = -6000;
            }
            scoreClockMS = 0;
            gameClock = new Timeline(new KeyFrame(Duration.millis(1), a -> {
                boolean visible = scoreClockMS > 4000;
                game.pollVisibleNotes(gameClockMS, visible);
                scoreClockMS++;
                if (scoreClockMS % 50 == 0) {
                    game.getScoreManager().updateScore();
                }
                if (gameClockMS < 0) {
                    gameClockMS++;
                } else if (gameClockMS == 0) {
                    gameClockMS++;
                    musicPlayer.startTrack();
                } else {
                    gameClockMS = musicPlayer.getCurrentTimeInMillis();
                }
            }));
            gameClock.setCycleCount(Timeline.INDEFINITE);
        }
        gameClock.play();
    }

    private Node addBox(Note note, int width, int height, boolean outline, boolean saveUserData) {
        return addBox(note, width, height, game.VISIBLE_NOTE_TIME, outline, saveUserData);
    }

    private Node addBox(Note note, int width, int height, int translateZ, boolean outline, boolean saveUserData) {
        KeyCodes key = note.getKeyCode();
        PhongMaterial phongMaterial = new PhongMaterial(Color.color(
                key.getColorRed(), key.getColorGreen(), key.getColorBlue()));
        Box box = new Box(width, height, note.getDuration());
        box.setDrawMode(outline ? DrawMode.LINE : DrawMode.FILL);
        box.setMaterial(phongMaterial);
        box.setTranslateX(key.getTranslateX());
        box.setTranslateY(key.getTranslateY());
        box.setTranslateZ(translateZ);
        box.setScaleZ(-1);
        if (saveUserData) {
            box.setUserData(note);
        }
        gameObjectGroup.getChildren().add(box);
        return box;
    }

    @SuppressWarnings("unused")
    private Animation animateNote(Node noteNode) {
        Note noteData = (Note) noteNode.getUserData();
        double fromZ = noteNode.getTranslateZ();
        double toZ = noteData.getDuration() / 2;
        double boxDepth = -noteData.getDuration();
        Duration travelDuration = Duration.millis(game.VISIBLE_NOTE_TIME);

        TranslateTransition translateTransition = new TranslateTransition(travelDuration, noteNode);
        translateTransition.setFromZ(fromZ);
        translateTransition.setToZ(toZ);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        ParallelTransition squishEffect = getSquishEffect(noteData, noteNode, toZ, boxDepth);
        SequentialTransition seqTransition = new SequentialTransition(translateTransition, squishEffect);

        return seqTransition;
    }

    private ParallelTransition getSquishEffect(Note noteData, Node noteNode, double toZ, double boxDepth) {
        ScaleTransition scaleTransition = new ScaleTransition(
                Duration.millis(noteData.getDuration()), noteNode);
        scaleTransition.setFromZ(-1);
        scaleTransition.setToZ(0);
        scaleTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition squishCompensation = new TranslateTransition(
                Duration.millis(noteData.getDuration()), noteNode);
        squishCompensation.setFromZ(toZ);
        double forwardCompensation = boxDepth * 0.5 * (1 - scaleTransition.getToZ());
        squishCompensation.setToZ(toZ + forwardCompensation);
        squishCompensation.setInterpolator(Interpolator.LINEAR);

        return new ParallelTransition(scaleTransition, squishCompensation);
    }

    private void addDefaultBoxes() {
        addBox(new Note(0, 0, KeyCodes.NUMPAD1), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD2), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD3), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD4), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD5), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD6), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD7), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD8), 125, 125, 0, true, false);
        addBox(new Note(0, 0, KeyCodes.NUMPAD9), 125, 125, 0, true, false);
    }

    private SubScene createSubScene() {
        gameObjectGroup = new Group();

        PointLight light = new PointLight(Color.color(0.6, 0.3, 0.4));
        light.setTranslateX(186);
        light.setTranslateY(0);
        light.setTranslateZ(-400);
        PointLight light2 = new PointLight(Color.color(0.4, 0.3, 0.6));
        light2.setTranslateX(560);
        light2.setTranslateY(0);
        light2.setTranslateZ(-400);

        AmbientLight ambientLight = new AmbientLight(Color.color(0.3, 0.3, 0.3));
        gameObjectGroup.getChildren().addAll(ambientLight, light, light2);

        SubScene subScene = new SubScene(gameObjectGroup, 560, 750, true,
                SceneAntialiasing.BALANCED);
        subScene.setFill(Color.ANTIQUEWHITE);
        PerspectiveCamera camera = new PerspectiveCamera(false);
        subScene.setCamera(camera);
        return subScene;
    }

    @SuppressWarnings("unused")
    private void openGameGUI(boolean resume) {
        if (!resume) {
            gameClockMS = -4000;
            lblLevelCompletion.setText("0%");
            lblScore.setText("0");
            vBoxInGameLogo.setVisible(true);
            vBoxInGameLogo.setOpacity(1);
            lblInGameLevelName.setText(NoteProcessor.getInstance().getActiveSong().getTitle());
            logoFade = new Timeline(new KeyFrame(Duration.millis(5),
                    i -> fadeView(vBoxInGameLogo, false)));
            logoFade.setDelay(Duration.seconds(4));
            logoFade.play();
        } else {
            vBoxPauseMenu.setVisible(false);
        }
        fadeView(stkGameContainer, true);
    }

    @Override
    public void updateHUD(int score) {
        lblScore.setText("" + score);
        int percentCompleted = (int) ((MusicPlayer.getInstance().getCurrentTimeInMillis()) /
        (MusicPlayer.getInstance().getTrackLengthInMillis()) * 100);
        lblLevelCompletion.setText(percentCompleted + "%");
    }

    @Override
    public void updateGUIPerspective(boolean enable) {
        PerspectiveCamera camera = (PerspectiveCamera) ((SubScene) stkGameContainer.getChildren().get(0)).getCamera();
        if (!enable) {
            camera.setTranslateY(0);
            camera.setTranslateZ(0);
            camera.getTransforms().clear();
        } else {
            camera.setTranslateY(-5);
            camera.setTranslateZ(-25);
            Rotate rotateX = new Rotate(-15, new Point3D(1, 0, 0));
            Rotate rotateY = new Rotate(0, new Point3D(0, 1, 0));
            Rotate rotateZ = new Rotate(0, new Point3D(0, 0, 1));
            camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
        }
    }

    @Override
    public void updatePowerups(Object[] powerups, boolean clearAll) {
        int btnNum = 0;
        // set the images for the available power-ups
        for (btnNum = 0; btnNum < powerups.length; btnNum++) {
            var button = powerBtns[btnNum];
            Image image = new Image("./media/" + powerups[btnNum] + ".png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(button.getWidth() - 5);
            imageView.setFitHeight(button.getHeight());
            imageView.setPreserveRatio(true);
            button.setOpacity(1);
            button.setGraphic(imageView);
        }
        // clear the image for empty power-ups slots
        for (int i = btnNum; i < 3; i++) {
            var button = powerBtns[btnNum];
            button.setOpacity(0.2);
            button.setGraphic(null);
        }
        if (clearAll) {
            for (var button : powerBtns) {
                button.setOpacity(0.2);
                button.setGraphic(null);
            }
        }
    }
}