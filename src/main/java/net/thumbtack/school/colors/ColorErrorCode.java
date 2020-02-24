package net.thumbtack.school.colors;

public enum ColorErrorCode {

    WRONG_COLOR_STRING("Error set wrong color!"),
    NULL_COLOR("Error instead of color passed null!");

    private String errorString;

    ColorErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

}