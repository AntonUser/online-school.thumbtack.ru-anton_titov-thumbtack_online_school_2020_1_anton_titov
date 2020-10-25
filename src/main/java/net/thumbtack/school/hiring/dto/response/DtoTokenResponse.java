package net.thumbtack.school.hiring.dto.response;

public class DtoTokenResponse {
    private String token;

    public DtoTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
