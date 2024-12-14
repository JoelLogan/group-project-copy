package model;

import java.io.Serializable;
import java.util.List;

import model.enums.SongDifficulties;

public class Song implements Serializable {
    private static final long serialVersionUID = 3591161874482209567L;
    private String source, title;
    private double length;
    private List<Note> notes;
    private int id;
    private SongDifficulties difficulty;
    private int highScore;

    public Song (String source, String title, double length, List<Note> notes, SongDifficulties difficulty, int id, int highScore) {
        this.source = source;
        this.title = title;
        this.length = length;
        this.notes = notes;
        this.difficulty = difficulty;
        this.id = id;
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String location) {
        this.source = "./src/media/" + location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public SongDifficulties getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(SongDifficulties difficulty) {
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
