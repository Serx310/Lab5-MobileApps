package com.nagel.lab5.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String title;
    private final String author;
    private final String content;
    private final int time;

    public Recipe(String title, String author, String content, int time){
        this.title = title;
        this.author = author;
        this.content = content;
        this.time = time;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getContent(){
        return content;
    }
    public int getTime(){
        return time;
    }

}
