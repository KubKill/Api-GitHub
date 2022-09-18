package com.atipera.api.git_data.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomException {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String status;
    private String Message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
