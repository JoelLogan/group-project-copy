import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

    private static MusicPlayer instance = null;
    private MediaPlayer mediaPlayer;
    private MediaPlayer backgroundPlayer;

    private MusicPlayer() {
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void reset() {
        instance = null;
    }

    public void pauseTrack() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resumeTrack() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void stopAndLoad(String trackLocation, Runnable onReady, Runnable onFinish) {
        stopTrack();
        try {
            File file = new File(trackLocation);
            if (!file.exists()) {
                throw new IllegalArgumentException("File not found: " + trackLocation);
            }
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(onReady);
            mediaPlayer.setOnEndOfMedia(onFinish);
            System.out.println("Loading track: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error playing track: " + trackLocation);
            e.printStackTrace();
        }
    }

    public void startTrack() {
        mediaPlayer.play();
    }

    public void stopTrack() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void startBackgroundMusic(String trackLocation) {
        stopTrack();
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
        Media media = new Media(getClass().getResource("/media/BackgroundMusic.mp3").toString());
        backgroundPlayer = new MediaPlayer(media);
        backgroundPlayer.setVolume(0.75);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        backgroundPlayer.play();
    }

    // Stop the background music
    public void stopBackgroundMusic() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }

    public void setCurrentTimeInMillis(double time) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.millis(time));
        }
    }

    public double getCurrentTimeInMillis() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentTime().toMillis();
        }
        return 0;
    }

    public double getTrackLengthInMillis() {
        if (mediaPlayer != null) {
            return mediaPlayer.getMedia().getDuration().toMillis();
        }
        return 0;
    }

    // Volume control(0.0 ~ 1.0)
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(Math.max(0.0, Math.min(1.0, volume)));
        }
    }

    // current Volume
    public double getVolume() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }
        return 0.0;
    }

    public MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            throw new IllegalStateException("No track loaded");
        }
        return mediaPlayer;
    }

    public double getPlaySpeed() {
        if (mediaPlayer != null) {
            return mediaPlayer.getRate();
        }
        return 0.0;
    }

    public void modifyPlaySpeed(double speed) {
        if (mediaPlayer != null) {
            mediaPlayer.setRate(speed);
        }
    }
}
