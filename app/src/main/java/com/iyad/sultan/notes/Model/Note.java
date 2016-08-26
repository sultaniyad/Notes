package com.iyad.sultan.notes.Model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Administrator on 8/23/2016.
 */
public class Note extends RealmObject {
    private String title;
    private String description;
    private Date Date ;
    private Boolean isFavorite;
}
