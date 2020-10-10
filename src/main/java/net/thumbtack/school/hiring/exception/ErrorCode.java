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
    REPEATING_EMPLOYER("employer to not unique"),
    ERRONEOUS_SKILL_EXCEPTION("skill more than 5 or less than 1"),
    GET_USER_EXCEPTION("user with such data is absent or incorrect login and password"),
    SALARY_EXCEPTION("salary is negative"),
    SKILL_BORDER_EXCEPTION("skill assessment value transcends"),
    AUTHORIZATION_EXCEPTION("you are not logged in, only logged in users have access to this method"),
    SERVER_STOPPED_EXCEPTION("error, server stopped"),
    SERVER_STARTED_EXCEPTION("error, server is already running"),
    NULL_TOKEN_EXCEPTION("Token field is NULL or empty"),
    NULL_PATRONYMIC_EXCEPTION("Patronymic field is NULL or empty"),
    NULL_NAME_POST_EXCEPTION("namePost is NULL"),
    DEMANDS_LIST_EXCEPTION("demand list is empty"),
    NULL_NAME_SKILL_EXCEPTION("nameSkill is NULL"),
    NULL_NAME_VACANCY("nameVacancy is NULL"),
    NULL_NAME_DEMAND("nameDemand is NULL"),
    SKILLS_LIST_EXCEPTION("skill list is empty"),
    EMPLOYER_EXCEPTION("employer for the specified id not found"),
    EMPLOYEE_EXCEPTION("employee for the specified id not found"),
    VACANCY_ID_EXCEPTION("this employer did not find such a vacancy with the given id"),
    VACANCY_NAME_EXCEPTION("this employer did not find such a vacancy with the given name"),
    JSON_EXCEPTION("JSON does not match the desired type"),
    BUSY_LOGIN_EXCEPTION("this login is already taken"),
    USER_EXCEPTION("user for the specified login not found"),
    WRONG_PASSWORD("wrong password"),
    DEMAND_EXCEPTION("no requirement found for the given name"),
    SKILL_EXCEPTION("no skill found for the given name");
    String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
