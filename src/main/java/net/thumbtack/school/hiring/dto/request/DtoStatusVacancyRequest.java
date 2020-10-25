package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

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

    public void validate() throws ServerException {
        if (namePost == null || namePost.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME_POST);
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }
}
