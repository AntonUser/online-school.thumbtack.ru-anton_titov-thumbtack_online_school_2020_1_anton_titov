package net.thumbtack.school.hiring.dto.request;

public class DtoStatusVacancyRequest extends DtoStatusEmployeeRequest {
    private String namePost;

    public DtoStatusVacancyRequest(String token, boolean status, String namePost) {
        super(token, status);
        this.namePost = namePost;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }
}
