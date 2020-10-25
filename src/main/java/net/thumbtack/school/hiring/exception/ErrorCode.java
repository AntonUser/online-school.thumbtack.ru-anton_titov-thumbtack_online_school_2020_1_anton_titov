package net.thumbtack.school.hiring.exception;

public enum ErrorCode {
    // не надо слово EXCEPTION использовать. Никакого смысла оно тут не несет
    // лучше конкретнее
    // REVU EMPTY_FIRST_NAME
    EMPTY_FIRST_NAME("first name is null"),
    EMPTY_LAST_NAME("last name is null"),
    EMPTY_LOGIN("login is null"),
    EMPTY_PASSWORD("password is null"),
    EMAIL_EXCEPTION("email is not entered or is entered incorrectly"),
    EMPTY_NAME("name is null"),
    EMPTY_ADDRESS("address is null"),
    ERRONEOUS_SKILL("skill more than 5 or less than 1"),
    SALARY_EXCEPTION("salary is negative"),
    SKILL_BORDER("skill assessment value transcends"),
    AUTHORIZATION_EXCEPTION("you are not logged in, only logged in users have access to this method"),
    SERVER_STOPPED("error, server stopped"),
    SERVER_STARTED("error, server is already running"),
    EMPTY_TOKEN("Token field is NULL or empty"),
    EMPTY_PATRONYMIC("Patronymic field is NULL or empty"),
    EMPTY_NAME_POST("namePost is NULL"),
    EMPTY_DEMANDS_LIST("demand list is empty"),
    EMPTY_NAME_SKILL("nameSkill is NULL"),
    EMPTY_NAME_VACANCY("nameVacancy is NULL"),
    EMPTY_NAME_DEMAND("nameDemand is NULL"),
    EMPTY_SKILLS_LIST("skill list is empty"),
    EMPLOYER_EXCEPTION("employer for the specified id not found"),
    EMPLOYEE_EXCEPTION("employee for the specified id not found"),
    NOT_FOUND_VACANCY_ID("this employer did not find such a vacancy with the given id"),
    NOT_FOUND_VACANCY_NAME("this employer did not find such a vacancy with the given name"),
    JSON_EXCEPTION("JSON does not match the desired type"),
    BUSY_LOGIN_EXCEPTION("this login is already taken"),
    NOT_FOUND_USER("user for the specified login not found"),
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
