package net.thumbtack.school.hiring.model;

public enum VacancyStatus {
    ACTIVE("vacancy is active"),
    NOT_ACTIVE("vacancy is not active");
    String message;

    private VacancyStatus(String errorCode) {
        this.message = errorCode;
    }

    public String getMessage() {
        return message;
    }
}

