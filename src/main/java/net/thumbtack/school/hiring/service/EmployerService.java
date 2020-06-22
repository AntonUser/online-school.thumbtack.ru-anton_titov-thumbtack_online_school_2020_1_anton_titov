package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployerService {
    private DataBase dataBase;
    private EmployeeDao employeeDao;
    private Gson gson;
    private VacancyDao vacancyDao;

    public EmployerService(DataBase dataBase) {
        this.dataBase = dataBase;
        employeeDao = new EmployeeDao(dataBase);
        gson = new Gson();
        vacancyDao = new VacancyDao(dataBase);
    }

    public String registerEmployer(String json) {
        EmployerDtoRegisterRequest employerDtoRegisterRequest;
        Employer employer;
        employerDtoRegisterRequest = gson.fromJson(json, EmployerDtoRegisterRequest.class);
        if (employerDtoRegisterRequest.getFirstName().isEmpty() || employerDtoRegisterRequest.getLastName().isEmpty() ||
                employerDtoRegisterRequest.getPatronymic().isEmpty() || employerDtoRegisterRequest.getEmail().isEmpty() ||
                employerDtoRegisterRequest.getLogin().isEmpty() || employerDtoRegisterRequest.getPassword().isEmpty() ||
                employerDtoRegisterRequest.getAddress().isEmpty() || employerDtoRegisterRequest.getName().isEmpty()) {
            return gson.toJson(new ErrorToken("Одно из полей employer имеет ошибочное значение"));
        }
        try {
            employer = new Employer(employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(),
                    employerDtoRegisterRequest.getEmail(), UUID.randomUUID().toString(),
                    employerDtoRegisterRequest.getFirstName(), employerDtoRegisterRequest.getPatronymic(),
                    employerDtoRegisterRequest.getLogin(), employerDtoRegisterRequest.getPassword());
            EmployerDao eDao = new EmployerDao(dataBase);
            eDao.save(employer);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoRegisterResponse(employer.getId()));
    }

    public String loginEmployer(String json) {
        DtoLoginRequest dtoLoginRequest;
        Employer employer;
        EmployerDao eDao = new EmployerDao(dataBase);
        dtoLoginRequest = gson.fromJson(json, DtoLoginRequest.class);
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
        DtoAddVacancyRequest dtoAddVacancyRequest = gson.fromJson(vacancyJson, DtoAddVacancyRequest.class);
        if (dtoAddVacancyRequest.getNamePost().isEmpty() ||
                dtoAddVacancyRequest.getSalary() < 0 ||
                dtoAddVacancyRequest.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Одно из полей, кроме, требований, имееет  неверное значение"));
        }

        Vacancy vacancy = new Vacancy(dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getDemands(), dtoAddVacancyRequest.getToken());
        vacancyDao.save(vacancy);
        DemandSkillDao demandSkillDao = new DemandSkillDao(dataBase);
        demandSkillDao.saveSubList(vacancy.getNamesDemands());
        return gson.toJson(new ErrorToken());
    }

    public String getEmployeeListNotLess(String demandsJson) {
        List<Employee> allEmployee = employeeDao.getAll();
        DtoDemands dtoDemands = convertDemands(demandsJson);
        List<Employee> outList = new ArrayList<>();
        if (validateDemand(dtoDemands)) {
            return gson.toJson(new ErrorToken("Список требований пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(new DtoEmployeesResponse(outList));
    }

    public String getEmployeeObligatoryDemand(String demandsJson) {
        List<Employee> allEmployee = employeeDao.getAll();
        DtoDemands dtoDemands = convertDemands(demandsJson);
        List<Employee> outList = new ArrayList<>();
        if (validateDemand(dtoDemands)) {
            return gson.toJson(new ErrorToken("Список требований пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(new DtoEmployeesResponse(outList));
    }

    public String getEmployee(String demandsJson) {
        List<Employee> allEmployee = employeeDao.getAll();
        DtoDemands dtoDemands = convertDemands(demandsJson);
        List<Employee> outList = new ArrayList<>();
        if (validateDemand(dtoDemands)) {
            return gson.toJson(new ErrorToken("Список требований пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(new DtoEmployeesResponse(outList));
    }

    public String getEmployeeWithOneDemand(String demandsJson) {
        List<Employee> allEmployee = employeeDao.getAll();
        DtoDemands dtoDemands = convertDemands(demandsJson);
        List<Employee> outList = new ArrayList<>();
        if (validateDemand(dtoDemands)) {
            return gson.toJson(new ErrorToken("Список требований пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(new DtoEmployeesResponse(outList));
    }

    public String getAllVacancies(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        if (dtoToken.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Токен пуст"));
        }
        DtoVacanciesResponse dtoVacanciesResponse = new DtoVacanciesResponse(vacancyDao.getVacanciesListByToken(dtoToken.getToken()));
        return gson.toJson(dtoVacanciesResponse);
    }

    public String getActivityVacancies(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        if (dtoToken.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Токен пуст"));
        }
        List<Vacancy> allVacancies = vacancyDao.getVacanciesListByToken(dtoToken.getToken());
        List<Vacancy> outVacanciesList = new ArrayList<>();
        for (Vacancy vacancy : allVacancies) {
            if (vacancy.isStatus()) {
                outVacanciesList.add(vacancy);
            }
        }
        DtoVacanciesResponse dtoVacanciesResponse = new DtoVacanciesResponse(outVacanciesList);
        return gson.toJson(dtoVacanciesResponse);
    }

    public String getNotActivityVacancies(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        if (dtoToken.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Токен пуст"));
        }
        List<Vacancy> allVacancies = vacancyDao.getVacanciesListByToken(dtoToken.getToken());
        List<Vacancy> outVacanciesList = new ArrayList<>();
        for (Vacancy vacancy : allVacancies) {
            if (!vacancy.isStatus()) {
                outVacanciesList.add(vacancy);
            }
        }
        DtoVacanciesResponse dtoVacanciesResponse = new DtoVacanciesResponse(outVacanciesList);
        return gson.toJson(dtoVacanciesResponse);
    }

    private DtoDemands convertDemands(String demandsJson) {
        return gson.fromJson(demandsJson, DtoDemands.class);
    }

    private boolean validateDemand(DtoDemands dtoDemands) {
        return dtoDemands.getDemands().isEmpty() || dtoDemands.getToken().isEmpty();
    }

    public String setVacancyStatus(String statusJson) {
        DtoStatusVacancyRequest dtoStatusRequest = gson.fromJson(statusJson, DtoStatusVacancyRequest.class);
        if (dtoStatusRequest.getToken().isEmpty() || dtoStatusRequest.getNamePost().isEmpty()) {
            return gson.toJson(new ErrorToken("Невалидное значение токена или названия должности"));
        }
        Vacancy vacancy = vacancyDao.getVacancyByTokenAndName(dtoStatusRequest.getToken(), dtoStatusRequest.getNamePost());
        if (vacancy == null) {
            return gson.toJson(new ErrorToken("Такой вакансии у данного работодателя не найдено"));
        }
        Vacancy newVacancy = vacancy;
        newVacancy.setStatus(dtoStatusRequest.isStatus());
        vacancyDao.update(vacancy, newVacancy);
        return gson.toJson(new ErrorToken());
    }

    public String updateDemandsInVacancy(String oldDemandJson, String newDemandJson) {
        Vacancy vacancy;
        try {
            DtoUpdateDemandRequest dtoOldDemand = gson.fromJson(oldDemandJson, DtoUpdateDemandRequest.class);
            if (validateDemand(dtoOldDemand)) {
                return gson.toJson(new ErrorToken("Невалидный объект"));
            }
            Demand oldDemand = new Demand(dtoOldDemand.getNameDemand(), dtoOldDemand.getSkill(), dtoOldDemand.isNecessary());
            DtoUpdateDemandRequest dtoNewDemand = gson.fromJson(newDemandJson, DtoUpdateDemandRequest.class);
            if (validateDemand(dtoNewDemand)) {
                return gson.toJson(new ErrorToken("Невалидный объект"));
            }
            Demand newDemand = new Demand(dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary());
            vacancy = vacancyDao.getVacancyByTokenAndName(dtoOldDemand.getToken(), dtoOldDemand.getNameVacancy());
            vacancy.updateDemand(oldDemand, newDemand);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken("Ошибка в оценке навыка"));
        }
        return gson.toJson(new ErrorToken());
    }

    public boolean validateDemand(DtoUpdateDemandRequest dtoOldDemand) {
        return dtoOldDemand.getNameVacancy().isEmpty() || dtoOldDemand.getToken().isEmpty() ||
                dtoOldDemand.getNameDemand().isEmpty() || dtoOldDemand.getSkill() < 1 ||
                dtoOldDemand.getSkill() < 1 || dtoOldDemand.getSkill() > 5;
    }

    public String removeVacancy(String vacancyJson) {
        DtoRemoveVacancyRequest dtoRemoveVacancyRequest = gson.fromJson(vacancyJson, DtoRemoveVacancyRequest.class);
        if (dtoRemoveVacancyRequest.getDemands().size() == 0 || dtoRemoveVacancyRequest.getNamePost().isEmpty() ||
                dtoRemoveVacancyRequest.getSalary() < 0 || dtoRemoveVacancyRequest.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Невалидный объект(одно из полей имеет ошибку)"));
        }
        Vacancy vacancy = new Vacancy(dtoRemoveVacancyRequest.getNamePost(), dtoRemoveVacancyRequest.getSalary(), dtoRemoveVacancyRequest.getDemands(), dtoRemoveVacancyRequest.getToken());
        vacancyDao.delete(vacancy);
        return gson.toJson(new ErrorToken());
    }
}