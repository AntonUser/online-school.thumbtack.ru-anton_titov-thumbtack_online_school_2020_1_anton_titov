package net.thumbtack.school.hiring.dto.responce;

public class DtoLoginResponse {
    private String token;


    public DtoLoginResponse(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
