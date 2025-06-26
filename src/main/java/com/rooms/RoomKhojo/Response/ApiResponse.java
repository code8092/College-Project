package com.rooms.RoomKhojo.Response;

import java.util.Map;

public class ApiResponse<T> {
    private String message;
    private T body;
    private Map<String, String> errors;
    private int status;

    public ApiResponse(String message, T body, Map<String, String> errors, int status) {
        this.message = message;
        this.body = body;
        this.errors = errors;
        this.status = status;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getBody() { return body; }
    public void setBody(T body) { this.body = body; }

    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> errors) { this.errors = errors; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
