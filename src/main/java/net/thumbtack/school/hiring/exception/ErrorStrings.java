package net.thumbtack.school.hiring.exception;

public enum ErrorStrings {
    FIRST_NAME_ERROR("Поле first name имеет значение NULL или пусто"),
    LAST_NAME_ERROR("Поле last name имеет значение NULL или пусто"),
    EMAIL_ERROR("Поле email имеет значение NULL или пусто"),
    LOGIN_ERROR("Поле login имеет значение NULL или пусто"),
    PASSWORD_ERROR("Поле password имеет значение NULL или пусто"),
    ADDRESS_ERROR("Поле address имеет значение NULL или пусто"),
    NAME_ERROR("Поле name имеет значение NULL или пусто"),
    NAME_POST_ERROR("Поле namePost имеет значение NULL или пусто"),
    TOKEN_ERROR("Поле token имеет значение NULL или пусто"),
    DEMANDS_LIST_ERROR("список требований пуст"),
    DEMAND_NAME_ERROR("поле nameDemand или oldNameDemand"),
    NAME_VACANCY_ERROR("поле nameVacancy имеет значение NULL или пусто"),
    VACANCY_ERROR("такой вакансии у данного работодателя не найдено"),
    AUTHORISATION_ERROR("неверный логин или пароль"),
    SKILLS_LIST_ERROR("список умений пуст"),
    NAME_SKILL_ERROR("поле nameSkill или oldNameSkill имеет значение NULL или пусто"),
    EMPLOYEE_ERROR("пользователь с заданным id не найден");
    private String stringMessage;

    ErrorStrings(String stringMessage) {
        this.stringMessage = stringMessage;
    }

    public String getStringMessage() {
        return stringMessage;
    }
}
