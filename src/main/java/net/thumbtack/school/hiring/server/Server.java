package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;

import java.io.IOException;

//REVU: добавь end-to-end тесты для методов сервера
public class Server {
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private boolean conditionServer;

    public Server() {
        this.employeeService = new EmployeeService();
        this.employerService = new EmployerService();
    }

    public void startServer() throws ServerException, IOException {
        if (conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STARTED);
        }
        conditionServer = true;
    }

    public void stopServer() throws IOException, ServerException {
        if (!conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STOPPED);
        }
        conditionServer = false;
    }
    //обновление данных работодателя и соискателя(добавить)

    public String registerEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.registerEmployee(requestJsonString);
    }

    public String logInEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.loginEmployee(requestJsonString);
    }

    public String setAccountEmployeeStatusEnable(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.setAccountStatusEnable(tokenJson);
    }

    public String setAccountEmployeeStatusDisable(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.setAccountStatusDisable(tokenJson);
    }

    public String logOutEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.logOut(requestJsonString);
    }

    public String registerEmployer(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.registerEmployer(requestJsonString);
    }

    public String logInEmployer(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.loginEmployer(requestJsonString);
    }

    public String logOutEmployer(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.logOut(requestJsonString);
    }

    public String enableVacancy(String requestJson) {
        return employerService.enableVacancy(requestJson);
    }

    public String disableVacancy(String requestJson) {
        return employerService.disableVacancy(requestJson);
    }

    public String addVacancy(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.addVacancy(requestJsonString);
    }

    public String addSkillForEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.addSkill(requestJsonString);
    }
    public String removeSkillForEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.removeEmployeeSkill(requestJsonString);
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

    public String getVacanciesOnlyName(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getVacanciesOnlyName(requestJsonString);
    }

    public String getVacanciesWithOneDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.getVacanciesWithOneDemand(requestJsonString);
    }

    //4 геттера на сотрудников
    public String getEmployeesNotLess(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeesListNotLess(requestJsonString);
    }

    public String getEmployeesObligatoryDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeesListObligatoryDemand(requestJsonString);
    }

    public String getEmployeesOnlyName(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeesListOnlyName(requestJsonString);
    }

    public String getEmployeesWithOneDemand(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employerService.getEmployeesListWithOneDemand(requestJsonString);
    }

    //3 геттера на вакансии для компании(свои)
    public String getAllVacancyByToken(String requestJsonString) throws ServerException {//1
        validateActivityServer();
        return employerService.getAllVacanciesByToken(requestJsonString);
    }

    public String getActivityVacanciesByToken(String requestStringJson) throws ServerException {//2
        validateActivityServer();
        return employerService.getAllActiveVacanciesByToken(requestStringJson);
    }

    public String getNotActivityVacanciesByToken(String requestStringJson) throws ServerException {//3
        validateActivityServer();
        return employerService.getAllNotActiveVacanciesByToken(requestStringJson);
    }

    public String updateEmployee(String requestJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployee(requestJson);
    }

    public String updateSkillEmployee(String newRequestJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeeSkill(newRequestJson);
    }

    public String updateEmployer(String requestJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployer(requestJson);
    }

    public String removeAccountEmployee(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.removeAccountEmployee(tokenJson);
    }

    public String removeAccountEmployer(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.removeAccountEmployer(tokenJson);
    }

    private void validateActivityServer() throws ServerException {
        if (!conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STOPPED);
        }
    }
}
