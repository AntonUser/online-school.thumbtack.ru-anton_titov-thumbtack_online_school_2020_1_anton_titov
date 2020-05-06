package net.thumbtack.school.hiring.exception;

public class ServerException extends Throwable {
    private ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
