package net.thumbtack.school.hiring.model;

public enum ConditionsRequirements {
    NECESSARY(""),
    NOT_NECESSARY("");
    String message;

    private ConditionsRequirements(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
