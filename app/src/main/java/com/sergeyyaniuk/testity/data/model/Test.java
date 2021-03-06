package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "tests",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id", childColumns = "user_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = {@Index(value = "id", unique = true), @Index("title"), @Index("user_id")})
public class Test {

    @Ignore
    public static final String FIELD_POPULARITY = "numRatings";

    @Ignore
    public static final String FIELD_AVG_RATING = "avgRating";

    @PrimaryKey()
    @NonNull
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "is_online")
    private boolean isOnline;

    @ColumnInfo(name = "number_of_questions")
    private int numberOfQuestions;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "date")
    private String date;

    @Ignore
    private int numRatings;

    @Ignore
    private double avgRating;

    @Ignore
    public Test() {
    }

    public Test(@NonNull String id, String title, String category, String language, String description, boolean isOnline,
                int numberOfQuestions, String userId, String userName, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.language = language;
        this.description = description;
        this.isOnline = isOnline;
        this.numberOfQuestions = numberOfQuestions;
        this.userId = userId;
        this.userName = userName;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
