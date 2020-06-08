package net.thumbtack.school.hiring.dto.responce;

public class ErrorToken {
    private String error;

    public ErrorToken(String error) {
        this.error = error;
    }

    public ErrorToken() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
