package net.thumbtack.school.hiring.Exception;

public class ServerException extends Throwable {
    //REVU: private - все поля должны быть private
    ErrorCode errorCode;
    public ServerException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
