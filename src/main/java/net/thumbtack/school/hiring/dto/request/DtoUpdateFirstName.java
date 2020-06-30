package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoUpdateFirstName {
    private String token;
    private String firstName;

    public DtoUpdateFirstName(String token, String firstName) {
        this.token = token;
        this.firstName = firstName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void validate() throws ServerException {
        if (token == null || token.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_TOKEN_EXCEPTION);
        } else if (firstName == null || firstName.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_FIRST_NAME_EXCEPTION);
        }
    }
}
