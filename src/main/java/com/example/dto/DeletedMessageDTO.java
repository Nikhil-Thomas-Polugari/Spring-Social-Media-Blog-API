package com.example.dto;

public class DeletedMessageDTO {

    private int messageId;
    private int postedBy;
    private String messageText;
    private long timePostedEpoch;

    public DeletedMessageDTO(int messageId, int postedBy, String messageText, long timePostedEpoch) {
        this.messageId = messageId;
        this.postedBy = postedBy;
        this.messageText = messageText;
        this.timePostedEpoch = timePostedEpoch;
    }

    // Getters and setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTimePostedEpoch() {
        return timePostedEpoch;
    }

    public void setTimePostedEpoch(long timePostedEpoch) {
        this.timePostedEpoch = timePostedEpoch;
    }
}
