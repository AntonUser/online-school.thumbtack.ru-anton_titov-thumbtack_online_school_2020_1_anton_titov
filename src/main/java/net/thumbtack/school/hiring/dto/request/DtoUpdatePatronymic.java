package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoUpdatePatronymic {
    private String token;
    private String patronymic;

    public DtoUpdatePatronymic(String token, String firstName) {
        this.token = token;
        this.patronymic = firstName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void validate() throws ServerException {
        if (token == null || token.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_TOKEN_EXCEPTION);
        } else if (patronymic == null || patronymic.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_PATRONYMIC_EXCEPTION);
        }
    }
}
