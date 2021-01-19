package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.ArrayList;
import java.util.List;

public class EmployerService {
    private final EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
    private static final Gson gson = new Gson();
    private final EmployerDaoImpl employerDaoImpl = new EmployerDaoImpl();
    private final DemandSkillDaoImpl demandSkillDaoImpl = new DemandSkillDaoImpl();

    public String registerEmployer(String json) {
        try {
            List<Requirement> requirements = new ArrayList<>();
            EmployerDtoRegisterRequest employerDtoRegisterRequest = getClassInstanceFromJson(EmployerDtoRegisterRequest.class, json);
            validateEmployerDtoRegisterRequest(employerDtoRegisterRequest);
            Employer employer = new Employer(employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(),
                    employerDtoRegisterRequest.getEmail(), employerDtoRegisterRequest.getFirstName(),
                    employerDtoRegisterRequest.getPatronymic(), employerDtoRegisterRequest.getLastName(),
                    employerDtoRegisterRequest.getLogin(), employerDtoRegisterRequest.getPassword());
            for (DtoAddVacancyRequest vacancy : employerDtoRegisterRequest.getVacancies()) {
                for (DtoRequirement dtoRequirement : vacancy.getRequirements()) {
                    requirements.add(new Requirement(dtoRequirement.getName(), dtoRequirement.getLevel(), ConditionsRequirements.valueOf(dtoRequirement.getNecessary())));
                }
                employer.addVacancy(new Vacancy(vacancy.getNamePost(), vacancy.getSalary(), new ArrayList<>(requirements), vacancy.getId()));
                requirements.clear();
            }
            return gson.toJson(new DtoRegisterResponse(employerDaoImpl.registerEmployer(employer)));
        } catch (ServerException se) {
            return gson.toJson(new ErrorDtoResponse(se));
        }
    }

    public String loginEmployer(String json) {
        try {
            DtoLoginRequest dtoLoginRequest = getClassInstanceFromJson(DtoLoginRequest.class, json);
            dtoLoginRequest.validate();
            String token = employerDaoImpl.loginEmployer(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
            return gson.toJson(new DtoLoginResponse(token));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }

    }

    public String logOut(String json) {
        try {
            DtoToken dtoToken = getClassInstanceFromJson(DtoToken.class, json);
            dtoToken.validate();
            employerDaoImpl.logOut(dtoToken.getToken());
            return gson.toJson(dtoToken);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String addVacancy(String vacancyJson) {
        try {
            DtoAddVacancyRequestWithUserToken dtoAddVacancyRequest = gson.fromJson(vacancyJson, DtoAddVacancyRequestWithUserToken.class);
            dtoAddVacancyRequest.validate();
            List<Requirement> requirements = new ArrayList<>();
            for (DtoRequirement dtoRequirement : dtoAddVacancyRequest.getRequirements()) {
                requirements.add(new Requirement(dtoRequirement.getName(), dtoRequirement.getLevel(), ConditionsRequirements.valueOf(dtoRequirement.getNecessary())));
            }
            employerDaoImpl.addVacancy(new Vacancy(dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), requirements, dtoAddVacancyRequest.getId()), dtoAddVacancyRequest.getUserToken());
            return gson.toJson(new DtoTokenResponse(dtoAddVacancyRequest.getId()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String getEmployeesListNotLess(String requirementsJson) {
        DtoRequirements dtoRequirements = gson.fromJson(requirementsJson, DtoRequirements.class);
        List<DtoRequirement> requirements = dtoRequirements.getDemands();
        List<Requirement> requirementsList = new ArrayList<>();
        for (DtoRequirement dtoRequirement : requirements) {
            requirementsList.add(new Requirement(dtoRequirement.getName(), dtoRequirement.getLevel(), ConditionsRequirements.valueOf(dtoRequirement.getNecessary())));
        }
        List<Employee> employeeList = employerDaoImpl.getEmployeesListNotLess(requirementsList);
        return gson.toJson(new DtoEmployeesResponse(employeeList));
    }

    public String getEmployeesListObligatoryDemand(String requirementsJson) {
        DtoRequirements dtoRequirements = gson.fromJson(requirementsJson, DtoRequirements.class);
        List<DtoRequirement> requirements = dtoRequirements.getDemands();
        List<Requirement> requirementsList = new ArrayList<>();
        for (DtoRequirement dtoRequirement : requirements) {
            requirementsList.add(new Requirement(dtoRequirement.getName(), dtoRequirement.getLevel(), ConditionsRequirements.valueOf(dtoRequirement.getNecessary())));
        }
        List<Employee> employeeList = employerDaoImpl.getEmployeesListObligatoryDemand(requirementsList);
        return gson.toJson(new DtoEmployeesResponse(employeeList));
    }

    public String getEmployeesListOnlyName(String requirementsJson) {
        DtoRequirements dtoRequirements = gson.fromJson(requirementsJson, DtoRequirements.class);
        List<DtoRequirement> requirements = dtoRequirements.getDemands();
        List<Requirement> requirementsList = new ArrayList<>();
        for (DtoRequirement dtoRequirement : requirements) {
            requirementsList.add(new Requirement(dtoRequirement.getName(), dtoRequirement.getLevel(), ConditionsRequirements.valueOf(dtoRequirement.getNecessary())));
        }
        List<Employee> employeeList = employerDaoImpl.getEmployeesListOnlyName(requirementsList);
        return gson.toJson(new DtoEmployeesResponse(employeeList));
    }

    public String getEmployeesListWithOneDemand(String requirementsJson) {
        DtoRequirements dtoRequirements = gson.fromJson(requirementsJson, DtoRequirements.class);
        List<DtoRequirement> requirements = dtoRequirements.getDemands();
        List<Requirement> requirementsList = new ArrayList<>();
        for (DtoRequirement dtoRequirement : requirements) {
            requirementsList.add(new Requirement(dtoRequirement.getName(), dtoRequirement.getLevel(), ConditionsRequirements.valueOf(dtoRequirement.getNecessary())));
        }
        List<Employee> employeeList = employerDaoImpl.getEmployeesListWithOneDemand(requirementsList);
        return gson.toJson(new DtoEmployeesResponse(employeeList));
    }

    public String enableVacancy(String requestJson) {
        DtoReplaceStatusVacancyRequest dtoReplaceStatusVacancyRequest = gson.fromJson(requestJson, DtoReplaceStatusVacancyRequest.class);
        try {
            employerDaoImpl.enableVacancy(dtoReplaceStatusVacancyRequest.getIdVacancy(), dtoReplaceStatusVacancyRequest.getTokenEmployer());
            return gson.toJson(new DtoResponseMessage("SUCCESSFUL"));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    public String disableVacancy(String requestJson) {
        DtoReplaceStatusVacancyRequest dtoReplaceStatusVacancyRequest = gson.fromJson(requestJson, DtoReplaceStatusVacancyRequest.class);
        try {
            employerDaoImpl.disableVacancy(dtoReplaceStatusVacancyRequest.getIdVacancy(), dtoReplaceStatusVacancyRequest.getTokenEmployer());
            return gson.toJson(new DtoResponseMessage("SUCCESSFUL"));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    public String getAllVacanciesByToken(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            List<Vacancy> vacancies = employerDaoImpl.getAllVacanciesByToken(dtoToken.getToken());
            List<DtoVacancyResponse> outList = new ArrayList<>();
            for (Vacancy vacancy : vacancies) {
                List<DtoRequirement> dtoRequirements = new ArrayList<>();
                for (Requirement requirement : vacancy.getRequirements()) {
                    dtoRequirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().name()));
                }
                outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), dtoRequirements, vacancy.getStatus().name()));
            }
            return gson.toJson(new DtoVacanciesResponse(outList));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    public String getAllActiveVacanciesByToken(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            List<Vacancy> vacancies = employerDaoImpl.getAllActiveVacanciesByToken(dtoToken.getToken());
            List<DtoVacancyResponse> outList = new ArrayList<>();
            for (Vacancy vacancy : vacancies) {
                List<DtoRequirement> dtoRequirements = new ArrayList<>();
                for (Requirement requirement : vacancy.getRequirements()) {
                    dtoRequirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().name()));
                }
                outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), dtoRequirements, vacancy.getStatus().name()));
            }
            return gson.toJson(new DtoVacanciesResponse(outList));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    public String getAllNotActiveVacanciesByToken(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            List<Vacancy> vacancies = employerDaoImpl.getAllNotActiveVacanciesByToken(dtoToken.getToken());
            List<DtoVacancyResponse> outList = new ArrayList<>();
            for (Vacancy vacancy : vacancies) {
                List<DtoRequirement> dtoRequirements = new ArrayList<>();
                for (Requirement requirement : vacancy.getRequirements()) {
                    dtoRequirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().name()));
                }
                outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), dtoRequirements, vacancy.getStatus().name()));
            }
            return gson.toJson(new DtoVacanciesResponse(outList));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    /*
        public String getEmployeeListNotLess(String demandsJson) throws ServerException {
            DtoDemands dtoDemands = convertDemands(demandsJson);
            try {
                dtoDemands.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoDemands.getToken());
            return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListNotLessByDemand(dtoDemands.getDemands())));
        }

        public String getEmployeeListObligatoryDemand(String demandsJson) throws ServerException {
            DtoDemands dtoDemands = convertDemands(demandsJson);
            try {
                dtoDemands.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoDemands.getToken());
            return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListObligatoryDemandByDemands(dtoDemands.getDemands())));
        }

        public String getEmployee(String demandsJson) throws ServerException {
            DtoDemands dtoDemands = convertDemands(demandsJson);
            try {
                dtoDemands.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoDemands.getToken());
            return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListByDemands(dtoDemands.getDemands())));
        }

        public String getEmployeeWithOneDemand(String demandsJson) throws ServerException {
            DtoDemands dtoDemands = convertDemands(demandsJson);
            try {
                dtoDemands.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoDemands.getToken());
            return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListWithOneDemandByDemands(dtoDemands.getDemands())));
        }

        public String getAllVacancies(String tokenJson) throws ServerException {
            DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
            try {
                dtoToken.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoToken.getToken());
            return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getVacanciesListByToken(dtoToken.getToken())));
        }

        public String getActivityVacancies(String tokenJson) throws ServerException {
            DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
            try {
                dtoToken.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoToken.getToken());
            return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getActivityVacanciesListByToken(dtoToken.getToken())));
        }

        public String getNotActivityVacancies(String tokenJson) throws ServerException {
            DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
            try {
                dtoToken.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoToken.getToken());
            return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getNotActivityVacanciesListByToken(dtoToken.getToken())));
        }

        public String setVacancyStatus(String statusJson) throws ServerException {
            DtoStatusVacancyRequest dtoStatusRequest = gson.fromJson(statusJson, DtoStatusVacancyRequest.class);
            Vacancy vacancy;
            validateActivity(dtoStatusRequest.getToken());
            try {
                dtoStatusRequest.validate();
                vacancy = vacancyDaoImpl.setStatus(dtoStatusRequest.getToken(), dtoStatusRequest.getNamePost(), dtoStatusRequest.isStatus());
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            return gson.toJson(new DtoVacancyResponse(vacancy.getName(), vacancy.getSalary(), vacancy.getObligatoryDemands(), vacancy.getNotObligatoryDemands(), vacancy.getToken(), vacancy.isStatus()));
        }

        public String setAccountStatus(String jsonStatus) throws ServerException {
            DtoToken dtoToken = gson.fromJson(jsonStatus, DtoToken.class);
            try {
                dtoToken.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(dtoToken.getToken());
            employerDaoImpl.setAccountStatus(dtoToken.getToken(), false);
            return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
        }

        public String updateDemandsInVacancy(String newDemandJson) throws ServerException {
            DtoUpdateDemandRequest dtoNewDemand = gson.fromJson(newDemandJson, DtoUpdateDemandRequest.class);
            validateActivity(dtoNewDemand.getToken());
            try {
                dtoNewDemand.validate();
                vacancyDaoImpl.updateDemandInVacancy(new Requirement(dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary()), dtoNewDemand.getToken(), dtoNewDemand.getNameVacancy(), dtoNewDemand.getOldNameDemand());
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            return gson.toJson(new DtoDemandResponse(dtoNewDemand.getToken(), dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary()));
        }
    */
    public String updateEmployer(String tokenJson) throws ServerException {
        DtoUpdateEmployerRequest dtoUpdateFirstName = gson.fromJson(tokenJson, DtoUpdateEmployerRequest.class);
        try {
            validateEmployerDtoUpdateRequest(dtoUpdateFirstName);
            employerDaoImpl.update(dtoUpdateFirstName.getId(), new Employer(dtoUpdateFirstName.getName(), dtoUpdateFirstName.getAddress(),
                    dtoUpdateFirstName.getEmail(), dtoUpdateFirstName.getFirstName(), dtoUpdateFirstName.getPatronymic(),
                    dtoUpdateFirstName.getLastName(), dtoUpdateFirstName.getLogin(),
                    dtoUpdateFirstName.getPassword()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateFirstName.getId()));
    }

    /*
        public String removeVacancy(String vacancyJson) throws ServerException {
            DtoRemoveVacancyRequest dtoRemoveVacancyRequest = gson.fromJson(vacancyJson, DtoRemoveVacancyRequest.class);
            validateActivity(dtoRemoveVacancyRequest.getToken());
            try {
                dtoRemoveVacancyRequest.validate();

            } catch (ServerException e) {
                return gson.toJson(new ErrorToken(e.getMessage()));
            }

            vacancyDaoImpl.delete(dtoRemoveVacancyRequest.getNamePost(), dtoRemoveVacancyRequest.getToken());
            return gson.toJson(new DtoTokenResponse(dtoRemoveVacancyRequest.getToken()));
        }
    */
    public String removeAccountEmployer(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
            employerDaoImpl.delete(dtoToken.getToken());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    private DtoRequirements convertDemands(String demandsJson) {
        return gson.fromJson(demandsJson, DtoRequirements.class);
    }

    private static <T> T getClassInstanceFromJson(Class<T> xClass, String json) throws ServerException {
        try {
            return gson.fromJson(json, xClass);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(ErrorCode.JSON_EXCEPTION);
        }
    }

    private static void validateEmployerDtoRegisterRequest(EmployerDtoRegisterRequest employerDtoRegisterRequest) throws ServerException {
        if (employerDtoRegisterRequest.getFirstName() == null || employerDtoRegisterRequest.getFirstName().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_FIRST_NAME);
        }
        if (employerDtoRegisterRequest.getLastName() == null || employerDtoRegisterRequest.getLastName().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LAST_NAME);
        }
        if (employerDtoRegisterRequest.getEmail() == null || employerDtoRegisterRequest.getEmail().isEmpty()) {
            throw new ServerException(ErrorCode.EMAIL_EXCEPTION);
        }
        if (employerDtoRegisterRequest.getLogin() == null || employerDtoRegisterRequest.getLogin().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LOGIN);
        }
        if (employerDtoRegisterRequest.getPassword() == null || employerDtoRegisterRequest.getPassword().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_PASSWORD);
        }
        if (employerDtoRegisterRequest.getAddress() == null || employerDtoRegisterRequest.getAddress().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_ADDRESS);
        }
        if (employerDtoRegisterRequest.getName() == null || employerDtoRegisterRequest.getName().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME);
        }
    }

    private static void validateEmployerDtoUpdateRequest(DtoUpdateEmployerRequest dtoUpdateEmployerRequest) throws ServerException {
        validateEmployerDtoRegisterRequest(dtoUpdateEmployerRequest);
        if (dtoUpdateEmployerRequest.getId() == null || dtoUpdateEmployerRequest.getId().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }
}