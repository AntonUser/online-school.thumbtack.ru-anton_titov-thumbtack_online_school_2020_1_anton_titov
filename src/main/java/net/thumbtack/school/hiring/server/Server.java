package net.thumbtack.school.hiring.server;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.listmultimapadapter.ListMultimapAdapter;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
            throw new ServerException(ErrorCode.SERVER_STARTED);
        }
        conditionServer = true;
        DataBase dataBase;
        File savedFile = new File(savedDataFileName);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ArrayListMultimap.class, new ListMultimapAdapter());
        if (savedFile.length() != 0) {
            try (FileReader fileReader = new FileReader(savedFile)) {
                Scanner scanner = new Scanner(fileReader);
                String dataString = scanner.nextLine();
                dataBase = new Gson().fromJson(dataString, DataBase.class);
                DataBase.setInstance(dataBase);
            }
        }
    }

    public void stopServer(String saveDataFileName) throws IOException, ServerException {
        if (!conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STOPPED);
        }
        conditionServer = false;
        String writeJson;
        DataBase dataBase = DataBase.getInstance();
        File savedFile = new File(saveDataFileName);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ArrayListMultimap.class, new ListMultimapAdapter());
        Gson gson = gsonBuilder.create();
        try (FileWriter fileWriter = new FileWriter(savedFile)) {
            writeJson = gson.toJson(dataBase);
            fileWriter.write(writeJson);
        }
        dataBase.cleanDataBase();
    }

    public String registerEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.registerEmployee(requestJsonString);
    }

    public String logInEmployee(String requestJsonString) throws ServerException {
        validateActivityServer();
        return employeeService.loginEmployee(requestJsonString);
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

    /* public String addVacancy(String requestJsonString) throws ServerException {
         validateActivityServer();
         return employerService.addVacancy(requestJsonString);
     }

     public String addSkillEmployee(String requestJsonString) throws ServerException {
         validateActivityServer();
         return employeeService.addEmployeeSkill(requestJsonString);
     }
 */


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
  /* public String getEmployeesNotLess(String requestJsonString) throws ServerException {
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
    }*/

    public String updateSkillEmployee(String newRequestJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployeeSkill(newRequestJson);
    }

  /*  public String updateDemands(String newRequestJson) throws ServerException {
        validateActivityServer();
        return employerService.updateDemandsInVacancy(newRequestJson);
    }*/

   /* public String updateEmployee(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.updateEmployee(tokenJson);
    }*/

    /*public String updateEmployer(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.updateEmployer(tokenJson);
    }
*/
    //
   /* public String removeSkillEmployee(String json) throws ServerException {
        validateActivityServer();
        return employeeService.removeEmployeeSkill(json);
    }
*/
    //удаление вакансии
/*    public String removeVacancy(String json) throws ServerException {
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
    }*/

    public String removeAccountEmployee(String tokenJson) throws ServerException {
        validateActivityServer();
        return employeeService.removeEmployee(tokenJson);
    }

  /*  public String removeAccountEmployer(String tokenJson) throws ServerException {
        validateActivityServer();
        return employerService.removeAccountEmployer(tokenJson);
    }*/

    private void validateActivityServer() throws ServerException {
        if (!conditionServer) {
            throw new ServerException(ErrorCode.SERVER_STOPPED);
        }
    }
}
