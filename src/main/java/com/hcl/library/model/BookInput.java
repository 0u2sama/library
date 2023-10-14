package com.hcl.library.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookInput {

    private String author;
    private String title;
    private String category;

    public BookInput(@JsonProperty("author") String author , @JsonProperty("title") String title , @JsonProperty("category") String category) {

        this.author = author;
        this.title = title;
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
