package net.thumbtack.school.hiring.Exception;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestServerException {
    @Test
    public void testGetErrorCode() {
        ErrorCode errorCode = ErrorCode.NULL_FIRST_NAME_EXCEPTION;
        ServerException serverException = new ServerException(errorCode);
        assertEquals(errorCode, serverException.getErrorCode());
    }
}
