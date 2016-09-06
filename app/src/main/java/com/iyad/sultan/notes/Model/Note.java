package com.iyad.sultan.notes.Model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Administrator on 8/23/2016.
 */
public class Note extends RealmObject {

    private String title;
    private String description;
    private String Date;
    private Boolean isFavorite;
    private int noteColor;

    /**
     * 1 2 3 4 5
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public int getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(int noteColor) {
        this.noteColor = noteColor;
    }
}
