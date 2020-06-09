package net.thumbtack.school.hiring.dto.request;

public class DtoStatusEmployeeRequest {
    private String token;
    private boolean status;

    public DtoStatusEmployeeRequest(String token, boolean status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
