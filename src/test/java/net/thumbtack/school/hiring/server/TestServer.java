package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.EmployeeDtoRegisterRequest;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Attainments;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestServer {
    Server server = new Server();
    Gson gson = new Gson();

    @Test
    public void testStartServer() {
        try {
            server.startServer("dat.txt");
            server.startServer("dat.txt");
            fail();
        } catch (IOException e) {
            e.getStackTrace();
        } catch (ServerException e) {
            assertEquals(ErrorCode.SERVER_STARTED_EXCEPTION, e.getErrorCode());
        }
    }

    @Ignore
    @Test
    public void testStopServer() {

    }

    @Test
    public void testRegisterEmployee() {
        List<Attainments> attainments = new ArrayList<>();
        attainments.add(new Attainments("язык Java", 4));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest = new EmployeeDtoRegisterRequest("Nicolay", "Pushkin", "Nicolaevich", "Kolyok78", "dddd", "kolyan@mtech.ru", true, attainments);
        try {
            server.startServer("dat.txt");
            server.registerEmployee(gson.toJson(employeeDtoRegisterRequest));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
        try {
            server.stopServer("dat.txt");
            server.startServer("dat.txt");
        } catch (ServerException | IOException e) {
            e.getStackTrace();
        }

    }

}
