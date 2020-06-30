package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoUpdateNameCompany {
    private String token;
    private String nameCompany;

    public DtoUpdateNameCompany(String token, String nameCompany) {
        this.token = token;
        this.nameCompany = nameCompany;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void validate() throws ServerException {
        if (token == null || token.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_TOKEN_EXCEPTION);
        } else if (nameCompany == null || nameCompany.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_NAME_EXCEPTION);
        }
    }
}
