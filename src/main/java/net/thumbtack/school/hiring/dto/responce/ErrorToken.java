package net.thumbtack.school.hiring.dto.responce;

// REVU при чем тут токен ? Это класс для всех видов ошибок
// ErrorDtoResponse, например
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
