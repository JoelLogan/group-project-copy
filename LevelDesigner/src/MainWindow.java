import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.LevelDesigner;
import model.Note;
import model.enums.KeyCodes;
import model.enums.SongDifficulties;
import model.observers.ErrorObserver;

public class MainWindow implements ErrorObserver {

    private Stage mainStage;
    @FXML
    public Label lblName, lblSongName, lblFirstPoint, lblSecondPoint, lblCurrentPos;
    @FXML
    public VBox vBoxEditorScreen, vBoxOpenButton, vBoxEditorBox;
    @FXML
    public ComboBox<SongDifficulties> cBoxSongDifficulty;
    private LevelDesigner levelDesigner;

    private static final int NUM_BARS = 9;
    private static final double BAR_HEIGHT = 35;
    private static final int CANVAS_WIDTH = 2000;
    private static final int MARKER_INTERVAL_MS = 10000;
    private static final int MARKER_HEIGHT = 10;
    private static final int LABEL_OFFSET = 15;
    private boolean warnOnNotes = true;
    private double tempStartNote = -1;
    private int tempCurrentBar = -1;

    public void initialize(Stage mainStage) {
        this.mainStage = mainStage;
        levelDesigner = LevelDesigner.getInstance();
        levelDesigner.setErrorObserver(this);
        cBoxSongDifficulty.getItems().addAll(SongDifficulties.values());
    }

    @SuppressWarnings("unused")
    @FXML
    void onNewSong(ActionEvent event) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5),
                i -> {
                    displayAlert(
                            "Save the file in the automatically opened file dialog. (Don't change the save directory)");
                }));
        timeline.setDelay(Duration.millis(500));
        timeline.play();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open BJU Beats File");
        fileChooser.setInitialDirectory(Paths.get("..", "src", "data").toFile());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("BJU Beats Song", "*.ser"));
        openFileManager(false);
    }

    @FXML
    void onOpenSong(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open BJU Beats File");
        fileChooser.setInitialDirectory(Paths.get("..", "src", "data").toFile());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("BJU Beats Song", "*.ser"));
        openFileManager(true);
    }

    @SuppressWarnings("unused")
    @FXML
    void onOpenMediaFile(ActionEvent event) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5),
                i -> {
                    displayAlert(
                            "Move your audio file to the location opened in the file dialogue then click the open button.");
                }));
        timeline.setDelay(Duration.millis(500));
        timeline.play();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File");
        fileChooser.setInitialDirectory(Paths.get("..", "src", "media").toFile());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Media File", "*.mp3", "*.wav"));
        File newMediaFile = fileChooser.showOpenDialog(mainStage);
        String newMediaFileName = newMediaFile.getName();
        if (newMediaFile != null) {
            levelDesigner.getCurrentSong().setSource(newMediaFileName);
            levelDesigner.saveCurrentSong();
            openSongEditor(
                    Paths.get("..", "src", "media", levelDesigner.getCurrentSong().getSource().substring(12)).toFile(),
                    true);
        }
    }

    @FXML
    void onSaveFile(ActionEvent event) {
        levelDesigner.saveCurrentSong();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            tempStartNote = -1;
            tempCurrentBar = -1;
            lblFirstPoint.setText("");
            lblSecondPoint.setText("");
        }
    }

    @FXML
    void onChangeDifficulty(ActionEvent event) {
        levelDesigner.getCurrentSong().setDifficulty(cBoxSongDifficulty.getValue());
    }

    @FXML
    void onNoteWarnChecked(ActionEvent event) {
        warnOnNotes = !warnOnNotes;
    }

    void onMouseMoved(MouseEvent event) {
        if (event.getSource() instanceof Canvas) {
            GraphicsContext gc = ((Canvas) event.getSource()).getGraphicsContext2D();
            double currentCanvasWidth = gc.getCanvas().getWidth();
            double pixelsPerMs = currentCanvasWidth / levelDesigner.getCurrentSong().getLength();
            lblCurrentPos.setText(String.format("%.2f", (event.getX() / pixelsPerMs) / 1000));
        }
    }

    private void openFileManager(boolean open) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("BJU Beats File Dialogue");
        fileChooser.setInitialDirectory(Paths.get("..", "src", "data").toFile());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("BJU Beats Song", "*.ser"));
        if (open) {
            File newSongFile = fileChooser.showOpenDialog(mainStage);
            if (newSongFile != null) {
                levelDesigner.loadSong(newSongFile);
                openSongEditor(Paths.get("..", "src", "media", levelDesigner.getCurrentSong().getSource().substring(12))
                        .toFile(), open);
            }
        } else {
            File newSongFile = fileChooser.showSaveDialog(mainStage);
            if (newSongFile != null) {
                levelDesigner.newSong(newSongFile);
                levelDesigner.getCurrentSong().setTitle(newSongFile.getName().replace(".ser", ""));
                openSongEditor(null, open);
            }
        }
    }

    private void openSongEditor(File songFile, boolean open) {
        lblSongName.setText("Track Name: " + levelDesigner.getCurrentSong().getSource().substring(12));
        vBoxEditorScreen.setVisible(true);
        lblName.setText("Name: " + levelDesigner.getCurrentSong().getTitle());
        cBoxSongDifficulty.setValue(levelDesigner.getCurrentSong().getDifficulty());
        if (open && songFile.exists()) {
            Media media = new Media(songFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                Duration duration = media.getDuration();
                long durationInMilliseconds = (long) duration.toMillis();
                levelDesigner.getCurrentSong().setLength(durationInMilliseconds);
                populateSongEditor();
            });
            mediaPlayer.setOnError(() -> {
                displayAlert("Error loading media file");
            });
            vBoxEditorBox.setVisible(true);
        } else {
            vBoxOpenButton.setVisible(true);
        }
    }

    private void populateSongEditor() {
        StackPane stackPane = new StackPane();
        Rectangle rect = new Rectangle(950, 400);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(400);
        if (levelDesigner.getCurrentSong() != null) {
            VBox vBox = new VBox(7.5);
            List<Canvas> bars = createClickableEditor();
            bars.forEach(c -> {
                GraphicsContext gc = c.getGraphicsContext2D();
                drawBar(gc, bars.indexOf(c));
                int barIndex = bars.indexOf(c);
                drawNotes(gc, levelDesigner.notes.get(barIndex));
                c.setOnMouseMoved(this::onMouseMoved);
            });
            vBox.getChildren().addAll(bars);
            scrollPane.setContent(vBox);
        }
        stackPane.getChildren().addAll(rect, scrollPane);
        vBoxEditorBox.getChildren().add(stackPane);
    }

    private List<Canvas> createClickableEditor() {
        ArrayList<Canvas> bars = new ArrayList<>();
        for (int i = 0; i < NUM_BARS; i++) {
            int barIndex = i;
            Canvas canvas = new Canvas(CANVAS_WIDTH, BAR_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            drawBar(gc, barIndex);
            canvas.setOnMouseClicked(event -> handleClick(event, barIndex, gc));
            bars.add(canvas);
        }
        return bars;
    }

    private void handleClick(MouseEvent event, int barIndex, GraphicsContext gc) {
        double currentCanvasWidth = gc.getCanvas().getWidth();
        double pixelsPerMs = currentCanvasWidth / levelDesigner.getCurrentSong().getLength();
        double timeCode = event.getX() / pixelsPerMs;
        if (event.isControlDown()) {
            removeSelectedNote(barIndex, gc, timeCode);
            return;
        }
        if (tempCurrentBar != barIndex) {
            tempStartNote = -1;
            tempCurrentBar = -1;
            lblFirstPoint.setText("");
        }
        if (tempStartNote == -1) {
            tempStartNote = timeCode;
            tempCurrentBar = barIndex;
            lblFirstPoint.setText(String.format("%.2f", timeCode / 1000));
            lblSecondPoint.setText("");
        } else {
            double startTime = Math.min(tempStartNote, timeCode);
            double entTime = Math.max(tempStartNote, timeCode);
            if (!isSpaceFilled(startTime, entTime, barIndex)) {
                levelDesigner.notes.get(barIndex).add(
                        new Note(startTime, entTime - startTime,
                                KeyCodes.valueOf("NUMPAD" + String.valueOf(barIndex + 1))));
                lblSecondPoint.setText(String.format("%.2f", timeCode / 1000));
            }
            tempStartNote = -1;
            drawBar(gc, barIndex);
            drawNotes(gc, levelDesigner.notes.get(barIndex));
        }
    }

    private void removeSelectedNote(int barIndex, GraphicsContext gc, double timeCode) {
        levelDesigner.notes.get(barIndex).removeIf(n -> {
            double noteStartX = n.getTimeCode();
            double noteEndX = noteStartX + n.getDuration();
            return timeCode >= noteStartX && timeCode <= noteEndX;
        });
        drawBar(gc, barIndex);
        drawNotes(gc, levelDesigner.notes.get(barIndex));
    }

    private void drawBar(GraphicsContext gc, int barIndex) {
        double currentCanvasWidth = gc.getCanvas().getWidth();
        double pixelsPerMs = currentCanvasWidth / levelDesigner.getCurrentSong().getLength();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, currentCanvasWidth, BAR_HEIGHT);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, currentCanvasWidth, BAR_HEIGHT);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 2, BAR_HEIGHT);
        gc.fillRect(currentCanvasWidth - 2, 0, 2, BAR_HEIGHT);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        for (int time = 0; time <= levelDesigner.getCurrentSong().getLength(); time += MARKER_INTERVAL_MS) {
            double x = time * pixelsPerMs;
            gc.strokeLine(x, 0, x, MARKER_HEIGHT);
            gc.setFill(Color.BLACK);
            gc.fillText((time / 1000) + "s", x + 2, MARKER_HEIGHT + LABEL_OFFSET);
        }
        gc.setFill(Color.RED);
        gc.fillText(String.valueOf(barIndex + 1), 15, BAR_HEIGHT / 2 + 5);
    }

    private void drawNotes(GraphicsContext gc, List<Note> notes) {
        double currentCanvasWidth = gc.getCanvas().getWidth();
        double pixelsPerMs = currentCanvasWidth / levelDesigner.getCurrentSong().getLength();
        gc.setFill(new Color(0, 0, 1, 0.5));
        for (Note note : notes) {
            double scaledX = note.getTimeCode() * pixelsPerMs;
            double scaledWidth = note.getDuration() * pixelsPerMs;
            gc.fillRect(scaledX, 0, scaledWidth, BAR_HEIGHT);
        }
    }

    private boolean isSpaceFilled(double startX, double endX, int barIndex) {
        if (Math.abs(startX - endX) > 7000) {
            if (warnOnNotes) {
                displayAlert("Notes cannot be longer than 7 seconds." + System.lineSeparator() +
                        "(This message can be disabled in the main menu)");
            }
            return true;
        }
        for (Note note : levelDesigner.notes.get(barIndex)) {
            double noteStartX = note.getTimeCode();
            double noteEndX = noteStartX + note.getDuration();
            if ((startX >= noteStartX && startX < noteEndX) || (endX > noteStartX && endX <= noteEndX)
                    || (startX <= noteStartX && endX >= noteEndX)) {
                return true;
            }
        }
        return false;
    }

    private void displayAlert(String text) {
        var alert = new Alert(AlertType.INFORMATION, text);
        alert.setHeaderText(null);
        alert.show();
    }

    @Override
    public void notifyError(String message) {
        displayAlert(message);
    }

}