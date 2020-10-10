package net.thumbtack.school.hiring.model;

public enum ConditionsRequirements {
    NECESSARY("Requirement is necessary"),
    NOT_NECESSARY("Requirement is not necessary");
	// REVU а точно здесь нужна эта текстовая строка ?
	// getMessage нигде не вызывается. Думаю, что она просто не нужна
    String message;

    private ConditionsRequirements(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
