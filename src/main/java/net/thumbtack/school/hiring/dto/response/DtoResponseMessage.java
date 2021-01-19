package net.thumbtack.school.hiring.dto.response;

public class DtoResponseMessage {
    private String message;

    public DtoResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
