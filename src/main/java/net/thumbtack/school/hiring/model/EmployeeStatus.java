package net.thumbtack.school.hiring.model;

public enum EmployeeStatus {
    ACTIVE("employee is active"),
    NOT_ACTIVE("employee is not active");
    String message;

    private EmployeeStatus(String errorCode) {
        this.message = errorCode;
    }

    public String getMessage() {
        return message;
    }
}
