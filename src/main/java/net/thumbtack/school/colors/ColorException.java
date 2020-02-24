package net.thumbtack.school.colors;

public class ColorException extends Exception {
    private ColorErrorCode CEC;

    public ColorException(ColorErrorCode CEC) {
        this.CEC = CEC;
    }

    public ColorErrorCode getErrorCode() {
        return CEC;
    }
}
