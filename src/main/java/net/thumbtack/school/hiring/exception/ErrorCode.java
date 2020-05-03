package net.thumbtack.school.hiring.exception;

public enum ErrorCode {
    NULL_FIRST_NAME_EXCEPTION("first name is null"),
    NULL_LAST_NAME_EXCEPTION("last name is null"),
    NULL_LOGIN_EXCEPTION("login is null"),
    NULL_PASSWORD_EXCEPTION("password is null"),
    EMAIL_EXCEPTION("email is not entered or is entered incorrectly"),
    NULL_NAME_EXCEPTION("name is null"),
    NULL_ADDRESS_EXCEPTION("address is null"),
    REPEATING_EMPLOYEE("employee to not unique"),
    REPEATING_EMPLOYER("employer to not unique");
    String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
