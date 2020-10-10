package net.thumbtack.school.hiring.model;

public enum ConditionsRequirements {
    NECESSARY("Requirement is necessary"),
    NOT_NECESSARY("Requirement is not necessary");
    String message;

    private ConditionsRequirements(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
