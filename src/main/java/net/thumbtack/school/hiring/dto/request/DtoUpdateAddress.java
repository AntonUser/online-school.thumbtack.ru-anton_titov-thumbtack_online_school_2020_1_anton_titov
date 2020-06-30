package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoUpdateAddress {
    private String token;
    private String address;

    public DtoUpdateAddress(String token, String address) {
        this.token = token;
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String nameCompany) {
        this.address = nameCompany;
    }

    public void validate() throws ServerException {
        if (token == null || token.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_TOKEN_EXCEPTION);
        } else if (address == null || address.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_ADDRESS_EXCEPTION);
        }
    }
}
