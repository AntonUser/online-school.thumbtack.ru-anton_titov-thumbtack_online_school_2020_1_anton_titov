package net.thumbtack.school.hiring.dto.response;

public class DtoRegisterResponse {
    private String token;

    public DtoRegisterResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
