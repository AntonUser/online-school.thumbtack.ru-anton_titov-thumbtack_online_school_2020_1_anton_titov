package net.thumbtack.school.hiring.exception;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestServerException {
    @Test
    public void testGetErrorCode() {
        ErrorCode errorCode = ErrorCode.EMPTY_FIRST_NAME;
        ServerException serverException = new ServerException(errorCode);
        assertEquals(errorCode, serverException.getErrorCode());
    }
}
