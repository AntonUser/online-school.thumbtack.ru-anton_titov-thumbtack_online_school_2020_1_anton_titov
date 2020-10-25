package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoRemoveVacancyRequest {
    private String token;
    private String namePost;

    public DtoRemoveVacancyRequest(String token, String namePost) {
        this.token = token;
        this.namePost = namePost;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public void validate() throws ServerException {
        if (token == null || token.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        } else if (namePost == null || namePost.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME_POST);
        }
    }
}
