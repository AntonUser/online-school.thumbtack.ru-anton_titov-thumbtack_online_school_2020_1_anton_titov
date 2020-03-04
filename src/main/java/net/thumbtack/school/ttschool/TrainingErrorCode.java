package net.thumbtack.school.ttschool;

public enum TrainingErrorCode {
    TRAINEE_WRONG_FIRSTNAME("firstname is null"),
    TRAINEE_WRONG_LASTNAME("lastname is null"),
    TRAINEE_WRONG_RATING("rating is false");
    private String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }
}
