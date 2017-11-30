package com.example.jespe.initiativiet;

/**
 * Created by sammy on 30-11-2017.
 */

public class Lovforslag {

    String author, headline, category, description;

    Lovforslag(String author, String headline, String category, String description) {
        this.author = author;
        this.headline = headline;
        this.category = category;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getHeadline() {
        return headline;
    }
}

