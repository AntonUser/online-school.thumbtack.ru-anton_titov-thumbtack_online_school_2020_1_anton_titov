package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;

import java.io.*;
import java.util.Scanner;

//REVU: добавь end-to-end тесты для методов сервера
public class Server {
    private EmployeeService employeeService;
    private EmployerService employerService;
    private boolean conditionServer;

    public Server() {
        this.employeeService = new EmployeeService();
        this.employerService = new EmployerService();
    }

    public void startServer(String savedDataFileName) throws ServerException, IOException {
        if (conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STARTED_EXCEPTION);
        }
        conditionServer = true;
        DataBase dataBase;
        File savedFile = new File(savedDataFileName);
        if (savedFile.length() != 0) {
            try (FileReader fileReader = new FileReader(savedFile)) {
                Scanner scanner = new Scanner(fileReader);
                String dataString = scanner.next();
                dataBase = new Gson().fromJson(dataString, DataBase.class);
                DataBase.setInstance(dataBase);
            }
        }
    }

    public void stopServer(String saveDataFileName) throws IOException, ServerException {
        if (!conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STOPPED_EXCEPTION);
        }
        conditionServer = false;
        DataBase dataBase = DataBase.getInstance();
        File savedFile = new File(saveDataFileName);
        try (FileWriter fileWriter = new FileWriter(savedFile)) {
            fileWriter.write(new Gson().toJson(dataBase));
        }
        DataBase.cleanDataBase();
    }

    public String registerEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.registerEmployee(requestJsonString);
    }

    public String loginEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.loginEmployee(requestJsonString);
    }

    public String registerEmployer(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.registerEmployer(requestJsonString);
    }

    public String loginEmployer(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.loginEmployer(requestJsonString);
    }

    public String addVacancy(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.addVacancy(requestJsonString);
    }

    public String addSkillEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.addEmployeeSkill(requestJsonString);
    }

    public String getAllDemandsSkills(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getAllDemandSkills(requestJsonString);
    }

    //4 геттера на вакансии
    public String getVacanciesNotLess(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getVacanciesNotLess(requestJsonString);
    }

    public String getVacanciesObligatoryDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getVacanciesObligatoryDemand(requestJsonString);
    }

    public String getVacancies(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getVacancies(requestJsonString);
    }

    public String getVacanciesWithOneDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getVacanciesWithOneDemand(requestJsonString);
    }

    //4 геттера на сотрудников
    public String getEmployeesNotLess(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeeListNotLess(requestJsonString);
    }

    public String getEmployeesObligatoryDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeeListObligatoryDemand(requestJsonString);
    }

    public String getEmployees(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployee(requestJsonString);
    }

    public String getEmployeesWithOneDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeeWithOneDemand(requestJsonString);
    }

    //3 геттера на вакансии для компании(свои)
    public String getAllVacancyByToken(String requestJsonString) throws ServerException {//1
        validateActivityServer();
        return employerService.getAllVacancies(requestJsonString);
    }

    public String getActivityVacanciesByToken(String requestStringJson) throws ServerException {//2
        validateActivityServer();
        return employerService.getActivityVacancies(requestStringJson);
    }

    public String getNotActivityVacanciesByToken(String requestStringJson) throws ServerException {//3
        validateActivityServer();
        return employerService.getNotActivityVacancies(requestStringJson);
    }

    public String setStatusEmployee(String requestJson) throws ServerException {
        validateActivityServer();
        return employeeService.setStatus(requestJson);
    }

    public String setStatusVacancy(String requestJson) throws ServerException {
        validateActivityServer();
        return employerService.setVacancyStatus(requestJson);
    }

    public String updateSkillEmployee(String newRequestJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeeSkill(newRequestJson);
    }

    public String updateDemands(String newRequestJson) throws ServerException {
        validateActivityServer();
        return employerService.updateDemandsInVacancy(newRequestJson);
    }

    // REVU: должен быть 1 метод updateEmployee
    // и если поле null, в запросе, то оно не обновляется
    // иначе - обновляется
    //обновление данных профиля у пользователей
    public String updateEmployeeFirstName(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeeFirstName(tokenJson);
    }

    public String updateEmployeePatronymic(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeePatronymic(tokenJson);
    }

    public String updateEmployeeLastName(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeeLastName(tokenJson);
    }

    public String updateEmployeeEmail(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeeEmail(tokenJson);
    }

    public String updateEmployeePassword(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeePassword(tokenJson);
    }

    public String updateEmployerFirstName(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerFirstName(tokenJson);
    }

    public String updateEmployerPatronymic(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerPatronymic(tokenJson);
    }

    public String updateEmployerLastName(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerLastName(tokenJson);
    }

    public String updateEmployerEmail(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerEmail(tokenJson);
    }

    public String updateEmployerAddress(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerAddress(tokenJson);
    }

    public String updateEmployerNameCompany(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerNameCompany(tokenJson);
    }

    public String updateEmployerPassword(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployerPassword(tokenJson);
    }

    //
    public String removeSkillEmployee(String json) throws ServerException {
        validateActivityServer();
        return employeeService.removeEmployeeSkill(json);
    }

    //удаление вакансии
    public String removeVacancy(String json) throws ServerException {
        validateActivityServer();
        return employerService.removeVacancy(json);
    }

    public String exitEmployee(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.setAccountStatus(tokenJson);
    }

    public String exitEmployer(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.setAccountStatus(tokenJson);
    }

    public String removeAccountEmployee(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.removeEmployee(tokenJson);
    }

    public String removeAccountEmployer(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.removeAccountEmployer(tokenJson);
    }

    private void validateActivityServer() throws ServerException {
        if (!conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STOPPED_EXCEPTION);
        }
    }
}
