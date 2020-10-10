package net.thumbtack.school.hiring.dto.responce;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class ErrorDtoResponse {
    private String error;

    public ErrorDtoResponse(ServerException error) {
        this.error = error.getErrorCode().getErrorCode();
    }

    public ErrorDtoResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(ServerException error) {
        this.error = error.getErrorCode().getErrorCode();
    }
}
