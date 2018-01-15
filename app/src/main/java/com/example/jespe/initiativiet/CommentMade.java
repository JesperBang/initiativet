package com.example.jespe.initiativiet;

/**
 * Created by Gustav Petersen on 13-01-2018.
 */

public class CommentMade {

    private String content, date, author;

    CommentMade(String content, String date, String author){

        this.content = content;
        this.author = author;
        this.date = date;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
