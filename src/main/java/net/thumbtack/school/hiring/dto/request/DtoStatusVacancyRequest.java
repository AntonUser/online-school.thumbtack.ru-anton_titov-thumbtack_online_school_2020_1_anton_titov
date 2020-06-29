package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorStrings;

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

    public void validate() throws NullPointerException {
        if (namePost == null || namePost.isEmpty()) {
            throw new NullPointerException(ErrorStrings.NAME_POST_ERROR.getStringMessage());
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new NullPointerException(ErrorStrings.TOKEN_ERROR.getStringMessage());
        }
    }
}
