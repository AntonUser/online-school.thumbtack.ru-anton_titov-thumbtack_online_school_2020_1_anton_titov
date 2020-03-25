package net.thumbtack.school.ttschool;

public enum TrainingErrorCode {
    TRAINEE_WRONG_FIRSTNAME("firstname is null"),
    TRAINEE_WRONG_LASTNAME("lastname is null"),
    TRAINEE_WRONG_RATING("rating is false"),
    GROUP_WRONG_ROOM("group wrong room"),
    GROUP_WRONG_NAME("group wrong name"),
    TRAINEE_NOT_FOUND("Trainee not found"),
    SCHOOL_WRONG_NAME("school wrong name"),
    DUPLICATE_GROUP_NAME("duplicate group name"),
    GROUP_NOT_FOUND("group not found"),
    DUPLICATE_TRAINEE("duplicate trainee"),
    EMPTY_TRAINEE_QUEUE("empty trainee queue");
    private String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }
}
