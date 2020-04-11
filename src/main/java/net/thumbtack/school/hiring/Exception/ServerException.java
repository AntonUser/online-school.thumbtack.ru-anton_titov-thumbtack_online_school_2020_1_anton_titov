package net.thumbtack.school.hiring.Exception;

public class ServerException extends Throwable {
    ErrorCode errorCode;
    public ServerException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
