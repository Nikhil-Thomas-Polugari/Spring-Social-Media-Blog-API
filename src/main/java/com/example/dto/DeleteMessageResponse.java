package com.example.dto;

public class DeleteMessageResponse {

    private int deletedRows;
    private DeletedMessageDTO deletedMessage;

    public DeleteMessageResponse(int deletedRows, DeletedMessageDTO deletedMessage) {
        this.deletedRows = deletedRows;
        this.deletedMessage = deletedMessage;
    }

    // Getters and setters
    public int getDeletedRows() {
        return deletedRows;
    }

    public void setDeletedRows(int deletedRows) {
        this.deletedRows = deletedRows;
    }

    public DeletedMessageDTO getDeletedMessage() {
        return deletedMessage;
    }

    public void setDeletedMessage(DeletedMessageDTO deletedMessage) {
        this.deletedMessage = deletedMessage;
    }
}
