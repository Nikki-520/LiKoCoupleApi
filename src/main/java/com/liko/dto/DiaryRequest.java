package com.liko.dto;

public class DiaryRequest {
    private String date;
    private String diaryDate;
    private String mood;
    private String content;
    private String images;

    public String getDiaryDate() {
        return diaryDate != null ? diaryDate : date;
    }

    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
