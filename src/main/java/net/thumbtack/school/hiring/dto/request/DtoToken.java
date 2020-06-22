package net.thumbtack.school.hiring.dto.request;

public class DtoToken {
    private String token;

    public DtoToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
