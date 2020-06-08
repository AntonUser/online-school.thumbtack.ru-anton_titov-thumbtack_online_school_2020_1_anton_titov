package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.dto.request.DtoAddVacancyRequest;
import net.thumbtack.school.hiring.dto.request.DtoDemands;
import net.thumbtack.school.hiring.dto.request.DtoLoginRequest;
import net.thumbtack.school.hiring.dto.request.EmployerDtoRegisterRequest;
import net.thumbtack.school.hiring.dto.responce.DtoEmployeesResponse;
import net.thumbtack.school.hiring.dto.responce.DtoLoginResponse;
import net.thumbtack.school.hiring.dto.responce.DtoRegisterResponse;
import net.thumbtack.school.hiring.dto.responce.ErrorToken;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployerService {
    private DataBase dataBase;
    private EmployeeDao employeeDao;
    private List<Employee> allEmployee;
    private DtoDemands dtoDemands;
    private List<Employee> outList;

    public EmployerService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String registerEmployer(String json) {
        EmployerDtoRegisterRequest employerDtoRegisterRequest;
        Employer employer;
        Gson gson = new Gson();
        employerDtoRegisterRequest = new Gson().fromJson(json, EmployerDtoRegisterRequest.class);
        try {
            employer = new Employer(employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(),
                    employerDtoRegisterRequest.getEmail(), UUID.randomUUID().toString(),
                    employerDtoRegisterRequest.getFirstName(), employerDtoRegisterRequest.getPatronymic(),
                    employerDtoRegisterRequest.getLogin(), employerDtoRegisterRequest.getPassword());
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken("Одно из полей employer - null"));
        }
        EmployerDao eDao = new EmployerDao(dataBase);
        try {
            eDao.save(employer);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken("Повторяющийся employer"));
        }

        return new Gson().toJson(new DtoRegisterResponse(employer.getId()));
    }

    public String loginEmployer(String json) {
        DtoLoginRequest dtoLoginRequest;
        Employer employer;
        Gson gson = new Gson();
        EmployerDao eDao = new EmployerDao(dataBase);
        dtoLoginRequest = new Gson().fromJson(json, DtoLoginRequest.class);
        if (dtoLoginRequest.getLogin() == null || dtoLoginRequest.getPassword() == null) {
            return gson.toJson(new ErrorToken("логин или пароль пустой"));
        }
        employer = eDao.getByLoginAndPassword(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        if (employer == null) {
            return gson.toJson(new ErrorToken("не верный логин или пароль"));
        }
        return gson.toJson(new DtoLoginResponse(employer.getId()));
    }

    public String addVacancy(String vacancyJson) {
        DtoAddVacancyRequest dtoAddVacancyRequest = new Gson().fromJson(vacancyJson, DtoAddVacancyRequest.class);
        if (dtoAddVacancyRequest.getNamePost().isEmpty() ||
                dtoAddVacancyRequest.getSalary() < 0 ||
                dtoAddVacancyRequest.getToken().isEmpty()) {
            return new Gson().toJson(new ErrorToken("Одно из полей, кроме, обязанностей, имееет  неверное значение"));
        }
        VacancyDao vacancyDao = new VacancyDao(dataBase);
        Vacancy vacancy = new Vacancy(dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getDemands(), dtoAddVacancyRequest.getToken());
        vacancyDao.save(vacancy);
        DemandSkillDao demandSkillDao = new DemandSkillDao(dataBase);
        demandSkillDao.saveSubList(vacancy.getNamesDemands());
        return new Gson().toJson(new ErrorToken());
    }

    public String getEmployeeListNotLess(String demandsJson) {
        init(demandsJson);
        int i = 0;
        for (Employee employee : allEmployee) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : dtoDemands.getDemands()) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill()) && demand.getSkill() <= attainments.getSkill() && demand.isNecessary()) {
                        i++;
                    }
                }
            }
            if (i == dtoDemands.getDemands().size()) {
                outList.add(employee);
            }
        }
        return new Gson().toJson(new DtoEmployeesResponse(outList));
    }

    public String getEmployeeObligatoryDemand(String demandsJson) {
        init(demandsJson);
        int i = 0, count = 0;
        for (Employee employee : allEmployee) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : dtoDemands.getDemands()) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill()) && demand.getSkill() <= attainments.getSkill()) {
                        i++;
                    }
                    if (demand.isNecessary()) {
                        count++;
                    }
                }
            }
            if (i == count) {
                outList.add(employee);
            }
        }
        return new Gson().toJson(new DtoEmployeesResponse(outList));
    }

    public String getEmployee(String demandsJson) {
        init(demandsJson);
        int i = 0;
        for (Employee employee : allEmployee) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : dtoDemands.getDemands()) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill())) {
                        i++;
                    }

                }
            }
            if (i == dtoDemands.getDemands().size()) {
                outList.add(employee);
            }
        }
        return new Gson().toJson(new DtoEmployeesResponse(outList));
    }

    public String getEmployeeWithOneDemand(String demandsJson) {
        init(demandsJson);
        int i = 0;
        for (Employee employee : allEmployee) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : dtoDemands.getDemands()) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill()) && demand.getSkill() <= attainments.getSkill() && demand.isNecessary()) {
                        i++;
                    }
                }
            }
            if (i != 0) {
                outList.add(employee);
            }
        }
        return new Gson().toJson(new DtoEmployeesResponse(outList));
    }

    private void init(String demandsJson) {
        employeeDao = new EmployeeDao(dataBase);
        allEmployee = employeeDao.getAll();
        dtoDemands = new Gson().fromJson(demandsJson, DtoDemands.class);
        outList = new ArrayList<>();
    }
}