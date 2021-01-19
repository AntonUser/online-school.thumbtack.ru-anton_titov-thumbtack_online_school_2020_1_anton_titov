package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Skill;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestServer {
    Server server = new Server();
    Gson gson = new Gson();

    @Test
    public void testStartServer() {
        try {
            server.startServer();
            server.startServer();
            fail();
            server.stopServer();
        } catch (IOException e) {
            e.getStackTrace();
        } catch (ServerException e) {
            assertEquals(ErrorCode.SERVER_STARTED, e.getErrorCode());
        }
    }

    @Ignore
    @Test
    public void testStopServer() {

    }

    @Test
    public void testLogInEmployer() {
        try {
            server.startServer();
            EmployerDtoRegisterRequest employerDtoRegisterRequest1 = new EmployerDtoRegisterRequest("Nicolay", "Ivanov", "Ivanovich", "RCode", "1258wef+", "RCode", "g.Omsk, ul.Mira 188", "rcode@cm.ru");
            String token = server.registerEmployer(gson.toJson(employerDtoRegisterRequest1));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(token, DtoRegisterResponse.class);
            server.logOutEmployer(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));

            String token2 = server.logInEmployer(gson.toJson(new DtoLoginRequest(employerDtoRegisterRequest1.getLogin(), employerDtoRegisterRequest1.getPassword())));
            DtoLoginResponse dtoLoginResponse2 = gson.fromJson(token2, DtoLoginResponse.class);
            String error = server.logInEmployer(gson.toJson(new DtoLoginRequest(employerDtoRegisterRequest1.getLogin(), employerDtoRegisterRequest1.getPassword())));
            ErrorDtoResponse errorDtoResponse = gson.fromJson(error, ErrorDtoResponse.class);
            assertEquals("you are already logged in", errorDtoResponse.getError());
            server.logOutEmployer(gson.toJson(new DtoToken(dtoLoginResponse2.getToken())));

            String error2 = server.logInEmployer(gson.toJson(new DtoLoginRequest("pup", employerDtoRegisterRequest1.getPassword())));
            ErrorDtoResponse errorDtoResponse2 = gson.fromJson(error2, ErrorDtoResponse.class);
            assertEquals("user for the specified login not found", errorDtoResponse2.getError());

            String error3 = server.logInEmployer(gson.toJson(new DtoLoginRequest(employerDtoRegisterRequest1.getLogin(), "fhhfhfhfhf")));
            ErrorDtoResponse errorDtoResponse3 = gson.fromJson(error3, ErrorDtoResponse.class);
            assertEquals("wrong password", errorDtoResponse3.getError());

            String token4 = server.logInEmployer(gson.toJson(new DtoLoginRequest(employerDtoRegisterRequest1.getLogin(), employerDtoRegisterRequest1.getPassword())));
            DtoLoginResponse dtoLoginResponse = gson.fromJson(token4, DtoLoginResponse.class);
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoLoginResponse.getToken())));
            server.stopServer();
        } catch (IOException | ServerException e) {
            e.getStackTrace();
        }
    }

    @Test
    public void testLogInEmployee() {
        try {
            server.startServer();
            List<DtoSkill> skills = new ArrayList<>();
            skills.add(new DtoSkill("java", 5));
            skills.add(new DtoSkill("python", 5));

            EmployeeDtoRegisterRequest employeeDtoRegisterRequest = new EmployeeDtoRegisterRequest("Nicolay", "Ivanov", "Ivanovich", "kolkol", "ddKol34324+", "nicola@cm.ru", skills);
            String token = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(token, DtoRegisterResponse.class);
            server.logOutEmployee(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));

            String token2 = server.logInEmployee(gson.toJson(new DtoLoginRequest(employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword())));
            DtoLoginResponse dtoLoginResponse2 = gson.fromJson(token2, DtoLoginResponse.class);
            String error = server.logInEmployee(gson.toJson(new DtoLoginRequest(employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword())));
            ErrorDtoResponse errorDtoResponse = gson.fromJson(error, ErrorDtoResponse.class);
            assertEquals("you are already logged in", errorDtoResponse.getError());
            server.logOutEmployee(gson.toJson(new DtoToken(dtoLoginResponse2.getToken())));

            String error2 = server.logInEmployee(gson.toJson(new DtoLoginRequest("pup", employeeDtoRegisterRequest.getPassword())));
            ErrorDtoResponse errorDtoResponse2 = gson.fromJson(error2, ErrorDtoResponse.class);
            assertEquals("user for the specified login not found", errorDtoResponse2.getError());

            String error3 = server.logInEmployee(gson.toJson(new DtoLoginRequest(employeeDtoRegisterRequest.getLogin(), "fhhfhfhfhf")));
            ErrorDtoResponse errorDtoResponse3 = gson.fromJson(error3, ErrorDtoResponse.class);
            assertEquals("wrong password", errorDtoResponse3.getError());

            String token4 = server.logInEmployee(gson.toJson(new DtoLoginRequest(employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword())));
            DtoLoginResponse dtoLoginResponse = gson.fromJson(token4, DtoLoginResponse.class);
            server.removeAccountEmployee(gson.toJson(new DtoToken(dtoLoginResponse.getToken())));
            server.stopServer();
        } catch (IOException | ServerException e) {
            e.getStackTrace();
        }
    }
   /* @Ignore
    @Test
    public void testRegisterEmployee() {
        List<Skill> attainments = new ArrayList<>();
        attainments.add(new Skill("language Java", 4));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest = new EmployeeDtoRegisterRequest("Nicolay", "Pushkin", "Nicolaevich", "Kolyok78", "dddd", "kolyan@mtech.ru", attainments);
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
    }*/

    @Test
    public void testGetVacanciesNotLess() {
        Set<DtoSkill> skills = new HashSet<>();
        skills.add(new DtoSkill("java", 5));
        skills.add(new DtoSkill("python", 5));
        skills.add(new DtoSkill("kotlin", 3));
        skills.add(new DtoSkill("css", 5));
        skills.add(new DtoSkill("angular", 4));
        skills.add(new DtoSkill("html", 4));
        DtoSkills dtoSkills = new DtoSkills();
        dtoSkills.setSkills(skills);
        EmployerDtoRegisterRequest employerDtoRegisterRequest1 = new EmployerDtoRegisterRequest("Ivan", "Petrov", "Ivanovich", "iTKing", "12345678+", "ITKing", "g.Omsk, ul.Mira 188", "kingit@cm.ru");
        EmployerDtoRegisterRequest employerDtoRegisterRequest2 = new EmployerDtoRegisterRequest("Egor", "Korzh", "Alexeevich", "supCod", "22Advslk#", "Super Code", "g.Omsk, ul.Neftesavodskaya d.2", "superCods@fj.com");
        List<DtoRequirement> requirements1 = new ArrayList<>();
        List<DtoRequirement> requirements2 = new ArrayList<>();
        List<DtoRequirement> requirements3 = new ArrayList<>();
        List<DtoRequirement> requirements4 = new ArrayList<>();

        requirements1.add(new DtoRequirement("java", 5, "NECESSARY"));
        requirements1.add(new DtoRequirement("html", 3, "NECESSARY"));
        requirements1.add(new DtoRequirement("css", 3, "NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("full-stak", 50000, requirements1));

        requirements2.add(new DtoRequirement("java", 2, "NECESSARY"));
        requirements2.add(new DtoRequirement("kotlin", 2, "NOT_NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("tester", 40000, requirements2));

        requirements3.add(new DtoRequirement("python", 5, "NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("developer", 50000, requirements3));

        requirements4.add(new DtoRequirement("html", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("css", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("angular", 5, "NOT_NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("front-end developer", 80000, requirements4));
        try {
            server.startServer();
            String token1 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest1));
            String token2 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest2));
            DtoTokenResponse dtoTokenResponse1 = gson.fromJson(token1, DtoTokenResponse.class);
            DtoTokenResponse dtoTokenResponse2 = gson.fromJson(token2, DtoTokenResponse.class);
            dtoSkills.setSkills(skills);
            String result = server.getVacanciesNotLess(gson.toJson(dtoSkills));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(result, DtoVacanciesResponse.class);
            assertEquals(3, dtoVacanciesResponse.getVacanciesList().size());
            DtoAddVacancyRequest dtoAddVacancyRequest = employerDtoRegisterRequest1.getVacancyByName("full-stak");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest.getId(), dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest2 = employerDtoRegisterRequest1.getVacancyByName("tester");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest2.getId(), dtoAddVacancyRequest2.getNamePost(), dtoAddVacancyRequest2.getSalary(), dtoAddVacancyRequest2.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest3 = employerDtoRegisterRequest2.getVacancyByName("developer");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest3.getId(), dtoAddVacancyRequest3.getNamePost(), dtoAddVacancyRequest3.getSalary(), dtoAddVacancyRequest3.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse1.getToken())));
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse2.getToken())));
            server.stopServer();
        } catch (ServerException | IOException se) {
            se.getStackTrace();
        }
    }

    @Test
    public void getVacanciesListObligatoryDemand() {
        Set<DtoSkill> skills = new HashSet<>();
        skills.add(new DtoSkill("java", 5));
        skills.add(new DtoSkill("python", 5));
        skills.add(new DtoSkill("kotlin", 3));
        skills.add(new DtoSkill("css", 5));
        skills.add(new DtoSkill("angular", 4));
        skills.add(new DtoSkill("html", 4));
        DtoSkills dtoSkills = new DtoSkills();
        dtoSkills.setSkills(skills);
        EmployerDtoRegisterRequest employerDtoRegisterRequest1 = new EmployerDtoRegisterRequest("Vasily", "Petrov", "Ivanovich", "kng45222", "12345678+", "ITKing", "g.Omsk, ul.Mira 188", "kingit@cm.ru");
        EmployerDtoRegisterRequest employerDtoRegisterRequest2 = new EmployerDtoRegisterRequest("Dmitry", "Korzh", "Alexeevich", "scoooooo5552", "22Advslk#", "Super Code", "g.Omsk, ul.Neftesavodskaya d.2", "superCods@fj.com");
        List<DtoRequirement> requirements1 = new ArrayList<>();
        List<DtoRequirement> requirements2 = new ArrayList<>();
        List<DtoRequirement> requirements3 = new ArrayList<>();
        List<DtoRequirement> requirements4 = new ArrayList<>();
        requirements1.add(new DtoRequirement("java", 5, "NECESSARY"));
        requirements1.add(new DtoRequirement("html", 3, "NECESSARY"));
        requirements1.add(new DtoRequirement("css", 3, "NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("full-stak", 50000, requirements1));

        requirements2.add(new DtoRequirement("java", 2, "NECESSARY"));
        requirements2.add(new DtoRequirement("kotlin", 2, "NOT_NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("tester", 40000, requirements2));

        requirements3.add(new DtoRequirement("python", 5, "NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("developer", 50000, requirements3));

        requirements4.add(new DtoRequirement("html", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("css", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("angular", 5, "NOT_NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("front-end developer", 80000, requirements4));
        try {
            server.startServer();
            String token1 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest1));
            String token2 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest2));
            DtoTokenResponse dtoTokenResponse1 = gson.fromJson(token1, DtoTokenResponse.class);
            DtoTokenResponse dtoTokenResponse2 = gson.fromJson(token2, DtoTokenResponse.class);
            dtoSkills.setSkills(skills);
            String result = server.getVacanciesObligatoryDemand(gson.toJson(dtoSkills));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(result, DtoVacanciesResponse.class);
            assertEquals(2, dtoVacanciesResponse.getVacanciesList().size());
            DtoAddVacancyRequest dtoAddVacancyRequest = employerDtoRegisterRequest1.getVacancyByName("full-stak");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest.getId(), dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest3 = employerDtoRegisterRequest2.getVacancyByName("developer");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest3.getId(), dtoAddVacancyRequest3.getNamePost(), dtoAddVacancyRequest3.getSalary(), dtoAddVacancyRequest3.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse1.getToken())));
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse2.getToken())));
            server.stopServer();
        } catch (ServerException | IOException se) {
            se.getStackTrace();
        }
    }

    @Test
    public void getVacanciesListOnlyName() {
        Set<DtoSkill> skills = new HashSet<>();
        skills.add(new DtoSkill("java", 5));
        skills.add(new DtoSkill("python", 5));
        skills.add(new DtoSkill("kotlin", 3));
        skills.add(new DtoSkill("css", 5));
        skills.add(new DtoSkill("angular", 4));
        skills.add(new DtoSkill("html", 4));
        DtoSkills dtoSkills = new DtoSkills();
        dtoSkills.setSkills(skills);
        EmployerDtoRegisterRequest employerDtoRegisterRequest1 = new EmployerDtoRegisterRequest("Vasily", "Petrov", "Ivanovich", "kng45222", "12345678+", "ITKing", "g.Omsk, ul.Mira 188", "kingit@cm.ru");
        EmployerDtoRegisterRequest employerDtoRegisterRequest2 = new EmployerDtoRegisterRequest("Dmitry", "Korzh", "Alexeevich", "scoooooo5552", "22Advslk#", "Super Code", "g.Omsk, ul.Neftesavodskaya d.2", "superCods@fj.com");
        List<DtoRequirement> requirements1 = new ArrayList<>();
        List<DtoRequirement> requirements2 = new ArrayList<>();
        List<DtoRequirement> requirements3 = new ArrayList<>();
        List<DtoRequirement> requirements4 = new ArrayList<>();
        List<DtoRequirement> requirements5 = new ArrayList<>();
        requirements1.add(new DtoRequirement("java", 5, "NECESSARY"));
        requirements1.add(new DtoRequirement("html", 3, "NECESSARY"));
        requirements1.add(new DtoRequirement("css", 3, "NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("full-stak", 50000, requirements1));

        requirements2.add(new DtoRequirement("java", 2, "NECESSARY"));
        requirements2.add(new DtoRequirement("kotlin", 2, "NOT_NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("tester", 40000, requirements2));

        requirements3.add(new DtoRequirement("ruby", 5, "NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("developer", 50000, requirements3));

        requirements4.add(new DtoRequirement("html", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("css", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("angular", 5, "NOT_NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("front-end developer", 80000, requirements4));

        requirements5.add(new DtoRequirement("kotlin", 5, "NECESSARY"));
        requirements5.add(new DtoRequirement("html", 5, "NECESSARY"));
        requirements5.add(new DtoRequirement("spring", 5, "NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("web-developer", 80000, requirements5));
        try {
            server.startServer();
            String token1 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest1));
            String token2 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest2));
            DtoTokenResponse dtoTokenResponse1 = gson.fromJson(token1, DtoTokenResponse.class);
            DtoTokenResponse dtoTokenResponse2 = gson.fromJson(token2, DtoTokenResponse.class);
            dtoSkills.setSkills(skills);
            String result = server.getVacanciesOnlyName(gson.toJson(dtoSkills));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(result, DtoVacanciesResponse.class);
            assertEquals(3, dtoVacanciesResponse.getVacanciesList().size());
            DtoAddVacancyRequest dtoAddVacancyRequest = employerDtoRegisterRequest1.getVacancyByName("full-stak");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest.getId(), dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest3 = employerDtoRegisterRequest1.getVacancyByName("tester");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest3.getId(), dtoAddVacancyRequest3.getNamePost(), dtoAddVacancyRequest3.getSalary(), dtoAddVacancyRequest3.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest5 = employerDtoRegisterRequest2.getVacancyByName("front-end developer");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest5.getId(), dtoAddVacancyRequest5.getNamePost(), dtoAddVacancyRequest5.getSalary(), dtoAddVacancyRequest5.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse1.getToken())));
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse2.getToken())));
            server.stopServer();
        } catch (ServerException | IOException se) {
            se.getStackTrace();
        }
    }

    @Test
    public void getVacanciesWithOneDemand() {
        Set<DtoSkill> skills = new HashSet<>();
        skills.add(new DtoSkill("java", 5));
        skills.add(new DtoSkill("python", 5));
        skills.add(new DtoSkill("kotlin", 3));
        skills.add(new DtoSkill("css", 5));
        skills.add(new DtoSkill("angular", 4));
        skills.add(new DtoSkill("html", 4));
        DtoSkills dtoSkills = new DtoSkills();
        dtoSkills.setSkills(skills);
        EmployerDtoRegisterRequest employerDtoRegisterRequest1 = new EmployerDtoRegisterRequest("Vasily", "Petrov", "Ivanovich", "kng45222", "12345678+", "ITKing", "g.Omsk, ul.Mira 188", "kingit@cm.ru");
        EmployerDtoRegisterRequest employerDtoRegisterRequest2 = new EmployerDtoRegisterRequest("Dmitry", "Korzh", "Alexeevich", "scoooooo5552", "22Advslk#", "Super Code", "g.Omsk, ul.Neftesavodskaya d.2", "superCods@fj.com");
        List<DtoRequirement> requirements1 = new ArrayList<>();
        List<DtoRequirement> requirements2 = new ArrayList<>();
        List<DtoRequirement> requirements3 = new ArrayList<>();
        List<DtoRequirement> requirements4 = new ArrayList<>();
        List<DtoRequirement> requirements5 = new ArrayList<>();
        requirements1.add(new DtoRequirement("java", 5, "NECESSARY"));
        requirements1.add(new DtoRequirement("html", 3, "NECESSARY"));
        requirements1.add(new DtoRequirement("css", 3, "NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("full-stak", 50000, requirements1));

        requirements2.add(new DtoRequirement("java", 2, "NECESSARY"));
        requirements2.add(new DtoRequirement("kotlin", 2, "NOT_NECESSARY"));
        employerDtoRegisterRequest1.addVacancy(new DtoAddVacancyRequest("tester", 40000, requirements2));

        requirements3.add(new DtoRequirement("python", 5, "NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("developer", 50000, requirements3));

        requirements4.add(new DtoRequirement("html", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("css", 4, "NECESSARY"));
        requirements4.add(new DtoRequirement("angular", 5, "NOT_NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("front-end developer", 80000, requirements4));

        requirements5.add(new DtoRequirement("kotlin", 5, "NECESSARY"));
        requirements5.add(new DtoRequirement("html", 5, "NECESSARY"));
        requirements5.add(new DtoRequirement("angular", 5, "NECESSARY"));
        employerDtoRegisterRequest2.addVacancy(new DtoAddVacancyRequest("web-developer", 80000, requirements5));
        try {
            server.startServer();
            String token1 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest1));
            String token2 = server.registerEmployer(gson.toJson(employerDtoRegisterRequest2));
            DtoTokenResponse dtoTokenResponse1 = gson.fromJson(token1, DtoTokenResponse.class);
            DtoTokenResponse dtoTokenResponse2 = gson.fromJson(token2, DtoTokenResponse.class);
            dtoSkills.setSkills(skills);
            String result = server.getVacanciesWithOneDemand(gson.toJson(dtoSkills));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(result, DtoVacanciesResponse.class);
            assertEquals(4, dtoVacanciesResponse.getVacanciesList().size());
            DtoAddVacancyRequest dtoAddVacancyRequest = employerDtoRegisterRequest1.getVacancyByName("full-stak");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest.getId(), dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest3 = employerDtoRegisterRequest1.getVacancyByName("tester");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest3.getId(), dtoAddVacancyRequest3.getNamePost(), dtoAddVacancyRequest3.getSalary(), dtoAddVacancyRequest3.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest4 = employerDtoRegisterRequest2.getVacancyByName("developer");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest4.getId(), dtoAddVacancyRequest4.getNamePost(), dtoAddVacancyRequest4.getSalary(), dtoAddVacancyRequest4.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            DtoAddVacancyRequest dtoAddVacancyRequest5 = employerDtoRegisterRequest2.getVacancyByName("front-end developer");
            if (!dtoVacanciesResponse.getVacanciesList().contains(new DtoVacancyResponse(dtoAddVacancyRequest5.getId(), dtoAddVacancyRequest5.getNamePost(), dtoAddVacancyRequest5.getSalary(), dtoAddVacancyRequest5.getRequirements(), "ACTIVE"))) {
                assert false;
            }
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse1.getToken())));
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoTokenResponse2.getToken())));
            server.stopServer();
        } catch (ServerException | IOException se) {
            se.getStackTrace();
        }
    }

    @Test
    public void testGetEmployeesNotLess() {
        ArrayList<DtoSkill> attainments1 = new ArrayList<>();
        attainments1.add(new DtoSkill("java", 5));
        attainments1.add(new DtoSkill("python", 3));
        attainments1.add(new DtoSkill("javascript", 5));
        attainments1.add(new DtoSkill("c++", 2));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest1 = new EmployeeDtoRegisterRequest("Vano", "Patrovsky", "Egorovich", "vano_45", "d4ferg4", "vano@mail.ru", attainments1);

        ArrayList<DtoSkill> attainments2 = new ArrayList<>();
        attainments2.add(new DtoSkill("html", 4));
        attainments2.add(new DtoSkill("react", 3));
        attainments2.add(new DtoSkill("css", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest2 = new EmployeeDtoRegisterRequest("Kemel", "Altybekov", "Abdurasulovich", "kema_12", "dfbce5S5v0", "kemalt@noname.ru", attainments2);

        ArrayList<DtoSkill> attainments3 = new ArrayList<>();
        attainments3.add(new DtoSkill("html", 4));
        attainments3.add(new DtoSkill("javascript", 4));
        attainments3.add(new DtoSkill("css", 4));
        attainments3.add(new DtoSkill("php", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest3 = new EmployeeDtoRegisterRequest("Ivan", "Ivanov", "Ivanovich", "vanka_45", "sjykaiveiu", "van@noname.ru", attainments3);

        ArrayList<DtoSkill> attainments4 = new ArrayList<>();
        attainments4.add(new DtoSkill("kotlin", 4));
        attainments4.add(new DtoSkill("java", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest4 = new EmployeeDtoRegisterRequest("Makar", "Plehow", "Nicolaewich", "pleh_oo", "s4klks454m", "pleh@noname.ru", attainments4);

        try {
            server.startServer();
            String token1 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest1));
            String token2 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest2));
            String token3 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest3));
            String token4 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest4));

            List<DtoRequirement> requirements = new ArrayList<>();
            requirements.add(new DtoRequirement("html", 3, "NECESSARY"));
            requirements.add(new DtoRequirement("javascript", 4, "NECESSARY"));
            String response = server.getEmployeesNotLess(gson.toJson(new DtoRequirements(null, requirements)));

            assertTrue(response.contains("\"attainmentsList\":[{\"name\":\"html\",\"level\":4},{\"name\":\"javascript\",\"level\":4},{\"name\":\"css\",\"level\":4},{\"name\":\"php\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"vanka_45\",\"password\":\"sjykaiveiu\",\"email\":\"van@noname.ru\",\"lastName\":\"Ivanov\",\"firstName\":\"Ivan\",\"patronymic\":\"Ivanovich\""));

            server.removeAccountEmployee(token1);
            server.removeAccountEmployee(token2);
            server.removeAccountEmployee(token3);
            server.removeAccountEmployee(token4);
            server.stopServer();
        } catch (ServerException | IOException ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void testGetEmployeesObligatoryDemand() {
        ArrayList<DtoSkill> attainments1 = new ArrayList<>();
        attainments1.add(new DtoSkill("java", 5));
        attainments1.add(new DtoSkill("python", 3));
        attainments1.add(new DtoSkill("javascript", 5));
        attainments1.add(new DtoSkill("c++", 2));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest1 = new EmployeeDtoRegisterRequest("Vano", "Patrovsky", "Egorovich", "vano_45", "d4ferg4", "vano@mail.ru", attainments1);

        ArrayList<DtoSkill> attainments2 = new ArrayList<>();
        attainments2.add(new DtoSkill("html", 4));
        attainments2.add(new DtoSkill("react", 3));
        attainments2.add(new DtoSkill("css", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest2 = new EmployeeDtoRegisterRequest("Kemel", "Altybekov", "Abdurasulovich", "kema_12", "dfbce5S5v0", "kemalt@noname.ru", attainments2);

        ArrayList<DtoSkill> attainments3 = new ArrayList<>();
        attainments3.add(new DtoSkill("html", 4));
        attainments3.add(new DtoSkill("javascript", 4));
        attainments3.add(new DtoSkill("css", 4));
        attainments3.add(new DtoSkill("php", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest3 = new EmployeeDtoRegisterRequest("Ivan", "Ivanov", "Ivanovich", "vanka_45", "sjykaiveiu", "van@noname.ru", attainments3);

        ArrayList<DtoSkill> attainments4 = new ArrayList<>();
        attainments4.add(new DtoSkill("kotlin", 4));
        attainments4.add(new DtoSkill("java", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest4 = new EmployeeDtoRegisterRequest("Makar", "Plehow", "Nicolaewich", "pleh_oo", "s4klks454m", "pleh@noname.ru", attainments4);

        try {
            server.startServer();
            String token1 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest1));
            String token2 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest2));
            String token3 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest3));
            String token4 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest4));

            List<DtoRequirement> requirements = new ArrayList<>();
            requirements.add(new DtoRequirement("html", 3, "NECESSARY"));
            requirements.add(new DtoRequirement("java", 4, "NOT_NECESSARY"));
            requirements.add(new DtoRequirement("php", 2, "NOT_NECESSARY"));
            String response = server.getEmployeesObligatoryDemand(gson.toJson(new DtoRequirements(null, requirements)));
            assertEquals("{\"employeeList\":[{\"attainmentsList\":[{\"name\":\"html\",\"level\":4},{\"name\":\"react\",\"level\":3},{\"name\":\"css\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"kema_12\",\"password\":\"dfbce5S5v0\",\"email\":\"kemalt@noname.ru\",\"lastName\":\"Altybekov\",\"firstName\":\"Kemel\",\"patronymic\":\"Abdurasulovich\"},{\"attainmentsList\":[{\"name\":\"html\",\"level\":4},{\"name\":\"javascript\",\"level\":4},{\"name\":\"css\",\"level\":4},{\"name\":\"php\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"vanka_45\",\"password\":\"sjykaiveiu\",\"email\":\"van@noname.ru\",\"lastName\":\"Ivanov\",\"firstName\":\"Ivan\",\"patronymic\":\"Ivanovich\"}]}", response);
            server.removeAccountEmployee(token1);
            server.removeAccountEmployee(token2);
            server.removeAccountEmployee(token3);
            server.removeAccountEmployee(token4);
            server.stopServer();
        } catch (ServerException | IOException ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void testGetEmployeesOnlyName() {
        ArrayList<DtoSkill> attainments1 = new ArrayList<>();
        attainments1.add(new DtoSkill("java", 5));
        attainments1.add(new DtoSkill("python", 3));
        attainments1.add(new DtoSkill("javascript", 5));
        attainments1.add(new DtoSkill("c++", 2));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest1 = new EmployeeDtoRegisterRequest("Vano", "Patrovsky", "Egorovich", "vano_45", "d4ferg4", "vano@mail.ru", attainments1);

        ArrayList<DtoSkill> attainments2 = new ArrayList<>();
        attainments2.add(new DtoSkill("html", 4));
        attainments2.add(new DtoSkill("react", 3));
        attainments2.add(new DtoSkill("css", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest2 = new EmployeeDtoRegisterRequest("Kemel", "Altybekov", "Abdurasulovich", "kema_12", "dfbce5S5v0", "kemalt@noname.ru", attainments2);

        ArrayList<DtoSkill> attainments3 = new ArrayList<>();
        attainments3.add(new DtoSkill("html", 4));
        attainments3.add(new DtoSkill("javascript", 4));
        attainments3.add(new DtoSkill("css", 4));
        attainments3.add(new DtoSkill("php", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest3 = new EmployeeDtoRegisterRequest("Ivan", "Ivanov", "Ivanovich", "vanka_45", "sjykaiveiu", "van@noname.ru", attainments3);

        ArrayList<DtoSkill> attainments4 = new ArrayList<>();
        attainments4.add(new DtoSkill("kotlin", 4));
        attainments4.add(new DtoSkill("java", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest4 = new EmployeeDtoRegisterRequest("Makar", "Plehow", "Nicolaewich", "pleh_oo", "s4klks454m", "pleh@noname.ru", attainments4);

        try {
            server.startServer();
            String token1 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest1));
            String token2 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest2));
            String token3 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest3));
            String token4 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest4));

            List<DtoRequirement> requirements = new ArrayList<>();
            requirements.add(new DtoRequirement("html", 3, "NECESSARY"));
            requirements.add(new DtoRequirement("react", 3, "NOT_NECESSARY"));
            String response = server.getEmployeesOnlyName(gson.toJson(new DtoRequirements(null, requirements)));
            assertTrue(response.contains("\"attainmentsList\":[{\"name\":\"html\",\"level\":4},{\"name\":\"react\",\"level\":3},{\"name\":\"css\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"kema_12\",\"password\":\"dfbce5S5v0\",\"email\":\"kemalt@noname.ru\",\"lastName\":\"Altybekov\",\"firstName\":\"Kemel\",\"patronymic\":\"Abdurasulovich\""));
            server.removeAccountEmployee(token1);
            server.removeAccountEmployee(token2);
            server.removeAccountEmployee(token3);
            server.removeAccountEmployee(token4);
            server.stopServer();
        } catch (ServerException | IOException ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void testGetEmployeesWithOneDemand() {
        ArrayList<DtoSkill> attainments1 = new ArrayList<>();
        attainments1.add(new DtoSkill("java", 5));
        attainments1.add(new DtoSkill("python", 3));
        attainments1.add(new DtoSkill("javascript", 5));
        attainments1.add(new DtoSkill("c++", 2));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest1 = new EmployeeDtoRegisterRequest("Vano", "Patrovsky", "Egorovich", "vano_45", "d4ferg4", "vano@mail.ru", attainments1);

        ArrayList<DtoSkill> attainments2 = new ArrayList<>();
        attainments2.add(new DtoSkill("html", 4));
        attainments2.add(new DtoSkill("react", 3));
        attainments2.add(new DtoSkill("css", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest2 = new EmployeeDtoRegisterRequest("Kemel", "Altybekov", "Abdurasulovich", "kema_12", "dfbce5S5v0", "kemalt@noname.ru", attainments2);

        ArrayList<DtoSkill> attainments3 = new ArrayList<>();
        attainments3.add(new DtoSkill("html", 4));
        attainments3.add(new DtoSkill("javascript", 4));
        attainments3.add(new DtoSkill("css", 4));
        attainments3.add(new DtoSkill("php", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest3 = new EmployeeDtoRegisterRequest("Ivan", "Ivanov", "Ivanovich", "vanka_45", "sjykaiveiu", "van@noname.ru", attainments3);

        ArrayList<DtoSkill> attainments4 = new ArrayList<>();
        attainments4.add(new DtoSkill("kotlin", 4));
        attainments4.add(new DtoSkill("java", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest4 = new EmployeeDtoRegisterRequest("Makar", "Plehow", "Nicolaewich", "pleh_oo", "s4klks454m", "pleh@noname.ru", attainments4);

        try {
            server.startServer();
            String token1 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest1));
            String token2 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest2));
            String token3 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest3));
            String token4 = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest4));

            List<DtoRequirement> requirements = new ArrayList<>();
            requirements.add(new DtoRequirement("html", 3, "NECESSARY"));
            requirements.add(new DtoRequirement("react", 3, "NOT_NECESSARY"));
            requirements.add(new DtoRequirement("kotlin", 4, "NECESSARY"));
            String response = server.getEmployeesWithOneDemand(gson.toJson(new DtoRequirements(null, requirements)));

            assertTrue(response.contains("\"attainmentsList\":[{\"name\":\"html\",\"level\":4},{\"name\":\"javascript\",\"level\":4},{\"name\":\"css\",\"level\":4},{\"name\":\"php\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"vanka_45\",\"password\":\"sjykaiveiu\",\"email\":\"van@noname.ru\",\"lastName\":\"Ivanov\",\"firstName\":\"Ivan\",\"patronymic\":\"Ivanovich\""));
            assertTrue(response.contains("\"attainmentsList\":[{\"name\":\"html\",\"level\":4},{\"name\":\"react\",\"level\":3},{\"name\":\"css\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"kema_12\",\"password\":\"dfbce5S5v0\",\"email\":\"kemalt@noname.ru\",\"lastName\":\"Altybekov\",\"firstName\":\"Kemel\",\"patronymic\":\"Abdurasulovich\""));
            assertTrue(response.contains("\"attainmentsList\":[{\"name\":\"kotlin\",\"level\":4},{\"name\":\"java\",\"level\":5}],\"status\":\"ACTIVE\",\"login\":\"pleh_oo\",\"password\":\"s4klks454m\",\"email\":\"pleh@noname.ru\",\"lastName\":\"Plehow\",\"firstName\":\"Makar\",\"patronymic\":\"Nicolaewich\""));

            server.removeAccountEmployee(token1);
            server.removeAccountEmployee(token2);
            server.removeAccountEmployee(token3);
            server.removeAccountEmployee(token4);
            server.stopServer();
        } catch (ServerException | IOException ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void testGetAllVacancyByToken() {
        EmployerDtoRegisterRequest employerDtoRegisterRequest = new EmployerDtoRegisterRequest("Ivan", "Kropotkov", "Ivanovich", "vano4556", "fdhh!v5", "MegaSoft", "g. Omsk, ul Mira 4", "megasoftina@blabla.com");
        List<DtoRequirement> dtoRequirements1 = new ArrayList<>();
        dtoRequirements1.add(new DtoRequirement("java", 4, "NECESSARY"));
        dtoRequirements1.add(new DtoRequirement("kotlin", 5, "NOT_NECESSARY"));
        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("developer", 50500, dtoRequirements1));

        List<DtoRequirement> dtoRequirements2 = new ArrayList<>();
        dtoRequirements2.add(new DtoRequirement("html", 5, "NECESSARY"));
        dtoRequirements2.add(new DtoRequirement("javascript", 5, "NECESSARY"));
        dtoRequirements2.add(new DtoRequirement("css", 4, "NECESSARY"));

        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("front-end developer", 40000, dtoRequirements2));

        List<DtoRequirement> dtoRequirements3 = new ArrayList<>();
        dtoRequirements3.add(new DtoRequirement("react", 4, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("html", 5, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("javascript", 5, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("css", 4, "NECESSARY"));
        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("full-stack developer", 60000, dtoRequirements3));

        try {
            server.startServer();
            String tokenJson = server.registerEmployer(gson.toJson(employerDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(tokenJson, DtoRegisterResponse.class);
            DtoToken dtoToken = new DtoToken(dtoRegisterResponse.getToken());
            String allVacanciesJson = server.getAllVacancyByToken(gson.toJson(dtoToken));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(allVacanciesJson, DtoVacanciesResponse.class);
            assertEquals(3, dtoVacanciesResponse.getVacanciesList().size());
            server.removeAccountEmployer(gson.toJson(dtoToken));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetActivityVacanciesByToken() {
        EmployerDtoRegisterRequest employerDtoRegisterRequest = new EmployerDtoRegisterRequest("Ivan", "Kropotkov", "Ivanovich", "vano4556", "fdhh!v5", "MegaSoft", "g. Omsk, ul Mira 4", "megasoftina@blabla.com");
        List<DtoRequirement> dtoRequirements1 = new ArrayList<>();
        dtoRequirements1.add(new DtoRequirement("java", 4, "NECESSARY"));
        dtoRequirements1.add(new DtoRequirement("kotlin", 5, "NOT_NECESSARY"));
        DtoAddVacancyRequest dtoAddVacancyRequest = new DtoAddVacancyRequest("developer", 50500, dtoRequirements1);
        employerDtoRegisterRequest.addVacancy(dtoAddVacancyRequest);

        List<DtoRequirement> dtoRequirements2 = new ArrayList<>();
        dtoRequirements2.add(new DtoRequirement("html", 5, "NECESSARY"));
        dtoRequirements2.add(new DtoRequirement("javascript", 5, "NECESSARY"));
        dtoRequirements2.add(new DtoRequirement("css", 4, "NECESSARY"));

        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("front-end developer", 40000, dtoRequirements2));

        List<DtoRequirement> dtoRequirements3 = new ArrayList<>();
        dtoRequirements3.add(new DtoRequirement("react", 4, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("html", 5, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("javascript", 5, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("css", 4, "NECESSARY"));
        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("full-stack developer", 60000, dtoRequirements3));

        try {
            server.startServer();
            String tokenJson = server.registerEmployer(gson.toJson(employerDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(tokenJson, DtoRegisterResponse.class);
            DtoToken dtoToken = new DtoToken(dtoRegisterResponse.getToken());
            DtoReplaceStatusVacancyRequest dtoReplaceStatusVacancyRequest = new DtoReplaceStatusVacancyRequest(dtoAddVacancyRequest.getId(), dtoRegisterResponse.getToken());
            server.disableVacancy(gson.toJson(dtoReplaceStatusVacancyRequest));
            String allVacanciesJson = server.getActivityVacanciesByToken(gson.toJson(dtoToken));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(allVacanciesJson, DtoVacanciesResponse.class);
            assertEquals(2, dtoVacanciesResponse.getVacanciesList().size());
            server.removeAccountEmployer(gson.toJson(dtoToken));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNotActivityVacanciesByToken() {
        EmployerDtoRegisterRequest employerDtoRegisterRequest = new EmployerDtoRegisterRequest("Ivan", "Kropotkov", "Ivanovich", "vano4556", "fdhh!v5", "MegaSoft", "g. Omsk, ul Mira 4", "megasoftina@blabla.com");
        List<DtoRequirement> dtoRequirements1 = new ArrayList<>();
        dtoRequirements1.add(new DtoRequirement("java", 4, "NECESSARY"));
        dtoRequirements1.add(new DtoRequirement("kotlin", 5, "NOT_NECESSARY"));
        DtoAddVacancyRequest dtoAddVacancyRequest = new DtoAddVacancyRequest("developer", 50500, dtoRequirements1);
        employerDtoRegisterRequest.addVacancy(dtoAddVacancyRequest);

        List<DtoRequirement> dtoRequirements2 = new ArrayList<>();
        dtoRequirements2.add(new DtoRequirement("html", 5, "NECESSARY"));
        dtoRequirements2.add(new DtoRequirement("javascript", 5, "NECESSARY"));
        dtoRequirements2.add(new DtoRequirement("css", 4, "NECESSARY"));

        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("front-end developer", 40000, dtoRequirements2));

        List<DtoRequirement> dtoRequirements3 = new ArrayList<>();
        dtoRequirements3.add(new DtoRequirement("react", 4, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("html", 5, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("javascript", 5, "NECESSARY"));
        dtoRequirements3.add(new DtoRequirement("css", 4, "NECESSARY"));
        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("full-stack developer", 60000, dtoRequirements3));

        try {
            server.startServer();
            String tokenJson = server.registerEmployer(gson.toJson(employerDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(tokenJson, DtoRegisterResponse.class);
            DtoToken dtoToken = new DtoToken(dtoRegisterResponse.getToken());
            DtoReplaceStatusVacancyRequest dtoReplaceStatusVacancyRequest = new DtoReplaceStatusVacancyRequest(dtoAddVacancyRequest.getId(), dtoRegisterResponse.getToken());
            server.disableVacancy(gson.toJson(dtoReplaceStatusVacancyRequest));
            String allVacanciesJson = server.getNotActivityVacanciesByToken(gson.toJson(dtoToken));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(allVacanciesJson, DtoVacanciesResponse.class);
            assertEquals(1, dtoVacanciesResponse.getVacanciesList().size());
            server.removeAccountEmployer(gson.toJson(dtoToken));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddVacancy() {
        EmployerDtoRegisterRequest employerDtoRegisterRequest = new EmployerDtoRegisterRequest("Nicolay", "Shmidt", "Alexandrovich", "Nicolka435", "fdhh!v5", "CodeFox", "g. Omsk, ul Mira 45", "codefox@blabla.com");
        List<DtoRequirement> requirementList1 = new ArrayList<>();
        requirementList1.add(new DtoRequirement("java", 5, "NECESSARY"));
        DtoAddVacancyRequest dtoAddVacancyRequest1 = new DtoAddVacancyRequest("Developer", 50000, requirementList1);
        employerDtoRegisterRequest.addVacancy(dtoAddVacancyRequest1);
        try {
            server.startServer();
            String tokenJson = server.registerEmployer(gson.toJson(employerDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(tokenJson, DtoRegisterResponse.class);
            String allVacanciesJson = server.getAllVacancyByToken(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));
            DtoVacanciesResponse dtoVacanciesResponse = gson.fromJson(allVacanciesJson, DtoVacanciesResponse.class);
            assertEquals(1, dtoVacanciesResponse.getVacanciesList().size());

            List<DtoRequirement> requirementList2 = new ArrayList<>();
            requirementList2.add(new DtoRequirement("python", 5, "NECESSARY"));
            DtoAddVacancyRequestWithUserToken dtoAddVacancyRequest2 = new DtoAddVacancyRequestWithUserToken("python - developer", 80000, requirementList2, dtoRegisterResponse.getToken());
            server.addVacancy(gson.toJson(dtoAddVacancyRequest2));
            String allVacanciesJson1 = server.getAllVacancyByToken(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));
            DtoVacanciesResponse dtoVacanciesResponse1 = gson.fromJson(allVacanciesJson1, DtoVacanciesResponse.class);
            assertEquals(2, dtoVacanciesResponse1.getVacanciesList().size());
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddSkillForEmployee() {
        List<DtoSkill> skills = new ArrayList<>();
        skills.add(new DtoSkill("java", 5));
        skills.add(new DtoSkill("kotlin", 4));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest = new EmployeeDtoRegisterRequest("Kirill", "Lebovsky", "Andreevich", "kirya34", "df5r89", "kirilka@lll.com", skills);
        try {
            server.startServer();
            String tokenJson = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(tokenJson, DtoRegisterResponse.class);

            AddSkillRequest dtoSkillRequest = new AddSkillRequest("html", 5, dtoRegisterResponse.getToken());
            String responseJson = server.addSkillForEmployee(gson.toJson(dtoSkillRequest));
            DtoAttainmentsResponse dtoAttainmentsResponse = gson.fromJson(responseJson, DtoAttainmentsResponse.class);
            assertEquals(new DtoAttainmentsResponse("html", 5), dtoAttainmentsResponse);

            DtoRemoveSkillRequest dtoRemoveSkillRequest = new DtoRemoveSkillRequest("html", dtoRegisterResponse.getToken());
            String responseToken = server.removeSkillForEmployee(gson.toJson(dtoRemoveSkillRequest));
            DtoToken dtoToken = gson.fromJson(responseToken, DtoToken.class);
            assertEquals(dtoRegisterResponse.getToken(), dtoToken.getToken());

            server.removeAccountEmployee(gson.toJson(dtoToken));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateEmployee() {
        List<DtoSkill> skills = new ArrayList<>();
        skills.add(new DtoSkill("java", 5));
        skills.add(new DtoSkill("kotlin", 4));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest = new EmployeeDtoRegisterRequest("Kirill", "Lebovsky", "Andreevich", "kirya34", "df5r89", "kirilka@lll.com", skills);

        try {
            server.startServer();
            String registerResponse = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(registerResponse, DtoRegisterResponse.class);

            DtoUpdateEmployeeRequest dtoUpdateEmployeeRequest = new DtoUpdateEmployeeRequest(null, null, null, "er564654", null, null, null, dtoRegisterResponse.getToken(), "NOT_ACTIVE");
            String responseString = server.updateEmployee(gson.toJson(dtoUpdateEmployeeRequest));
            ErrorDtoResponse errorDtoResponse = gson.fromJson(responseString, ErrorDtoResponse.class);
            assertEquals("there is a separate method for updating the status", errorDtoResponse.getError());
            server.removeAccountEmployee(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateEmployer() {
        List<DtoRequirement> dtoRequirements = new ArrayList<>();
        dtoRequirements.add(new DtoRequirement("java", 5, "NECESSARY"));
        dtoRequirements.add(new DtoRequirement("html", 3, "NECESSARY"));
        dtoRequirements.add(new DtoRequirement("css", 3, "NECESSARY"));
        EmployerDtoRegisterRequest employerDtoRegisterRequest = new EmployerDtoRegisterRequest("Vasily", "Petrov",
                "Ivanovich", "kng45222", "12345678+", "ITKing", "g.Omsk, ul.Mira 188", "kingit@cm.ru");
        employerDtoRegisterRequest.addVacancy(new DtoAddVacancyRequest("full-stak", 50000, dtoRequirements));

        try {
            server.startServer();
            String response = server.registerEmployer(gson.toJson(employerDtoRegisterRequest));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(response, DtoRegisterResponse.class);
            DtoUpdateEmployerRequest dtoUpdateEmployerRequest = new DtoUpdateEmployerRequest("Afonya", "Larin", "Vasilevich", employerDtoRegisterRequest.getLogin(), employerDtoRegisterRequest.getPassword(), employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(), "bobic@blabla.com", UUID.randomUUID().toString(), true);
            String errorResponse = server.updateEmployer(gson.toJson(dtoUpdateEmployerRequest));
            ErrorDtoResponse errorDtoResponse = gson.fromJson(errorResponse, ErrorDtoResponse.class);

            assertEquals("user was not found for the given id", errorDtoResponse.getError());
            server.removeAccountEmployer(gson.toJson(new DtoToken(dtoRegisterResponse.getToken())));
            server.stopServer();
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateSkillEmployee() {
        ArrayList<DtoSkill> attainments1 = new ArrayList<>();
        attainments1.add(new DtoSkill("java", 5));
        attainments1.add(new DtoSkill("python", 5));
        attainments1.add(new DtoSkill("ruby", 5));
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest1 = new EmployeeDtoRegisterRequest("Vano", "Patrovsky", "Egorovich", "vano_45", "d4ferg4", "vano@mail.ru", attainments1);
        try {
            server.startServer();
            String registerResponse = server.registerEmployee(gson.toJson(employeeDtoRegisterRequest1));
            DtoRegisterResponse dtoRegisterResponse = gson.fromJson(registerResponse, DtoRegisterResponse.class);
            DtoSkillRequest dtoSkillRequest = new DtoSkillRequest(dtoRegisterResponse.getToken(), "html", 4, "ruby");
            server.updateSkillEmployee(gson.toJson(dtoSkillRequest));
            List<DtoRequirement> requirements = new ArrayList<>();
            requirements.add(new DtoRequirement("java", 3, "NECESSARY"));
            requirements.add(new DtoRequirement("python", 4, "NECESSARY"));
            String response = server.getEmployeesWithOneDemand(gson.toJson(new DtoRequirements(null, requirements)));
            DtoEmployeesResponse dtoEmployeesResponse = gson.fromJson(response, DtoEmployeesResponse.class);
            assertTrue(dtoEmployeesResponse.getEmployeeList().get(0).getAttainmentsList().contains(new Skill("html", 4)));
        } catch (ServerException | IOException e) {
            e.printStackTrace();
        }
    }
}