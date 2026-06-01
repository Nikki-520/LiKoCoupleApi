package com.liko.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private Long id;
    private Long coupleId;
    private Long fromId;
    private Long toId;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private String fromName;
    private String toName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(Long coupleId) {
        this.coupleId = coupleId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFrom() {
        return fromName;
    }

    public String getTo() {
        return toName;
    }

    public Boolean getRead() {
        return isRead;
    }

    public String getDate() {
        if (createdAt == null) {
            return null;
        }
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
