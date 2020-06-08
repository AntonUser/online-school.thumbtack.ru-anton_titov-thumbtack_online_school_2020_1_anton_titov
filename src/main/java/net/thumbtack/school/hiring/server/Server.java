package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;

public class Server {
    private DataBase dataBase;
    EmployeeService employeeService;
    EmployerService employerService;

    public Server() {
        dataBase = dataBase.getInstance();
        this.employeeService = new EmployeeService(dataBase);
        this.employerService = new EmployerService(dataBase);
    }

    //при выходе с сервера удалять инфу о работадателе и работнике
    public void startServer(String savedDataFileName) {
    }

    public void stopServer(String saveDataFileName) {
    }

    // public String <имя-метода>(String requestJsonString)
    public String registerEmployee(String requestJsonString) {
        return employeeService.registerEmployee(requestJsonString);
    }

    public String loginEmployee(String requestJsonString) {
        return employeeService.loginEmployee(requestJsonString);
    }

    public String registerEmployer(String requestJsonString) {
        return employerService.registerEmployer(requestJsonString);
    }

    public String loginEmployer(String requestJsonString) {
        return employerService.loginEmployer(requestJsonString);
    }

    public String addVacancy(String requestJsonString) {
        return employerService.addVacancy(requestJsonString);
    }

    public String addSkillEmployee(String requestJsonString) {
        return employeeService.addEmployeeSkill(requestJsonString);
    }

    public String getAllDemandsSkills(String requestJsonString) {
        return employeeService.getAllDemandSkills();
    }

    //4 геттера на вакансии
    public String getVacanciesNotLess(String requestJsonString) {
        return employeeService.getVacanciesNotLess(requestJsonString);
    }

    public String getVacanciesObligatoryDemand(String requestJsonString) {
        return employeeService.getVacanciesObligatoryDemand(requestJsonString);
    }

    public String getVacancies(String requestJsonString) {
        return employeeService.getVacancies(requestJsonString);
    }

    public String getVacanciesWithOneDemand(String requestJsonString) {
        return employeeService.getVacanciesWithOneDemand(requestJsonString);
    }

    //4 геттера на сотрудников
    public String getEmployeesNotLess(String requestJsonString) {
        return employerService.getEmployeeListNotLess(requestJsonString);
    }

    public String getEmployeesObligatoryDemand(String requestJsonString) {
        return employerService.getEmployeeObligatoryDemand(requestJsonString);
    }

    public String getEmployees(String requestJsonString) {
        return employerService.getEmployee(requestJsonString);
    }

    public String getEmployeeWithOneDemand(String requestJsonString) {
        return employerService.getEmployeeWithOneDemand(requestJsonString);
    }

}
