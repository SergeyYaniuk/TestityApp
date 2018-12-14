package com.sergeyyaniuk.testity.ui.find.findList;

import android.text.TextUtils;

public class Filters {

    private String category = null;
    private String language = null;
    private String sortBy = null;
    private String author = null;

    public Filters() {}

    public boolean hasCategory() {
        return !(TextUtils.isEmpty(category));
    }

    public boolean hasLanguage() {
        return !(TextUtils.isEmpty(language));
    }

    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }

    public boolean hasAuthor(){
        return !(TextUtils.isEmpty(author));
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
