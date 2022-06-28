package com.example.groupbuy.utils.messageUtils;

public class Message<T> {
    private int status;
    private String message;
    private T data;

    Message(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    Message(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
