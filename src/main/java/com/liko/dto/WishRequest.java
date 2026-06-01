package com.liko.dto;

public class WishRequest {
    private String title;
    private String desc;
    private String description;
    private Boolean done;
    private Boolean isDone;
    private String date;
    private String doneDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description != null ? description : desc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getIsDone() {
        return isDone != null ? isDone : done;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getDoneDate() {
        return doneDate != null ? doneDate : date;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
