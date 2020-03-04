package net.thumbtack.school.colors;

public class ColorException extends Exception {
    //REVU: постарайся не использовать сокращений и аббревиатур - лучше errorCode назвать, к примеру
    private ColorErrorCode CEC;

    public ColorException(ColorErrorCode CEC) {
        this.CEC = CEC;
    }

    public ColorErrorCode getErrorCode() {
        return CEC;
    }
}
