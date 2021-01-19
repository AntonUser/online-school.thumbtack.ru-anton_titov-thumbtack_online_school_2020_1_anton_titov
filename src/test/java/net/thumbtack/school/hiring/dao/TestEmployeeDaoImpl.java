package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployeeDaoImpl {
    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    EmployerDaoImpl employerDao = new EmployerDaoImpl();
    DataBase dataBase = DataBase.getInstance();

    @Test
    public void testGetVacanciesListNotLess() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("java", 5));
        skills.add(new Skill("python", 5));
        skills.add(new Skill("kotlin", 4));
        skills.add(new Skill("php", 3));
        try {
            Employer employer1 = new Employer("CodeMaker", "g.Omsk, ul.Mira 188", "codemaker@cm.ru", "Ivan", "Ivanovich", "Petrov", "logocoDeMaKer", "12345678+");
            Employer employer2 = new Employer("ItStar", "g.Omsk, ul.Gagarina 18", "itstar@it.ru", "Boris", "Petrovich", "Petrov", "itStaR__", "12s4f67s8g+");
            List<Requirement> requirements1 = new ArrayList<>();
            List<Requirement> requirements2 = new ArrayList<>();
            List<Requirement> requirements3 = new ArrayList<>();
            List<Requirement> requirements4 = new ArrayList<>();

            requirements1.add(new Requirement("java", 3, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("html", 5, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("javascript", 4, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("css", 4, ConditionsRequirements.NOT_NECESSARY));
            employer1.addVacancy(new Vacancy("developer", 50000, requirements1, UUID.randomUUID().toString()));//

            requirements2.add(new Requirement("java", 4, ConditionsRequirements.NECESSARY));
            requirements2.add(new Requirement("kotlin", 4, ConditionsRequirements.NECESSARY));
            employer1.addVacancy(new Vacancy("tester", 40000, requirements2, UUID.randomUUID().toString()));
            employerDao.registerEmployer(employer1);

            requirements3.add(new Requirement("php", 5, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("angular", 4, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("css", 4, ConditionsRequirements.NOT_NECESSARY));
            employer2.addVacancy(new Vacancy("web-developer", 80000, requirements3, UUID.randomUUID().toString()));

            requirements4.add(new Requirement("python", 4, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("python-tester", 61000, requirements4, UUID.randomUUID().toString()));
            employerDao.registerEmployer(employer2);
            List<Vacancy> result = employeeDao.getVacanciesListNotLess(skills);

            assertEquals(2, result.size());

            if (!result.contains(employer2.getVacancyByName("python-tester"))) {
                assert false;
            }
            if (!result.contains(employer1.getVacancyByName("tester"))) {
                assert false;
            }
            dataBase.cleanDataBase();
        } catch (ServerException es) {
            es.getStackTrace();
        }

    }

    @Test
    public void testGetVacanciesListObligatoryDemand() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("java", 5));
        skills.add(new Skill("python", 5));
        skills.add(new Skill("kotlin", 3));
        skills.add(new Skill("css", 5));
        skills.add(new Skill("angular", 4));
        try {
            Employer employer1 = new Employer("ITKing", "g.Omsk, ul.Mira 188", "kingit@cm.ru", "Ivan", "Ivanovich", "Petrov", "iTKing", "12345678+");
            Employer employer2 = new Employer("ItStar", "g.Omsk, ul.Gagarina 18", "itstar@it.ru", "Boris", "Petrovich", "Petrov", "itSrr", "12s4f67s8g+");
            List<Requirement> requirements1 = new ArrayList<>();
            List<Requirement> requirements2 = new ArrayList<>();
            List<Requirement> requirements3 = new ArrayList<>();
            List<Requirement> requirements4 = new ArrayList<>();
            List<Requirement> requirements5 = new ArrayList<>();

            requirements1.add(new Requirement("java", 3, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("html", 5, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("javascript", 4, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("css", 2, ConditionsRequirements.NOT_NECESSARY));
            employer1.addVacancy(new Vacancy("developer", 50000, requirements1, UUID.randomUUID().toString()));//

            requirements2.add(new Requirement("java", 4, ConditionsRequirements.NECESSARY));
            requirements2.add(new Requirement("kotlin", 4, ConditionsRequirements.NECESSARY));
            employer1.addVacancy(new Vacancy("tester", 40000, requirements2, UUID.randomUUID().toString()));
            employerDao.registerEmployer(employer1);

            requirements3.add(new Requirement("angular", 3, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("css", 3, ConditionsRequirements.NOT_NECESSARY));
            employer2.addVacancy(new Vacancy("front-end developer", 80000, requirements3, UUID.randomUUID().toString()));

            requirements4.add(new Requirement("python", 4, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("python-tester", 61000, requirements4, UUID.randomUUID().toString()));

            requirements5.add(new Requirement("css", 3, ConditionsRequirements.NECESSARY));
            requirements5.add(new Requirement("angular", 3, ConditionsRequirements.NECESSARY));
            requirements5.add(new Requirement("java", 5, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("full-stack developer", 100000, requirements5, UUID.randomUUID().toString()));
            employerDao.registerEmployer(employer2);

            List<Vacancy> result = employeeDao.getVacanciesListObligatoryDemand(skills);
            assertEquals(2, result.size());
            if (!result.contains(employer2.getVacancyByName("python-tester"))) {
                assert false;
            }
            if (!result.contains(employer2.getVacancyByName("full-stack developer"))) {
                assert false;
            }
            dataBase.cleanDataBase();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetVacanciesListOnlyName() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("java", 1));
        skills.add(new Skill("python", 1));
        skills.add(new Skill("kotlin", 1));
        skills.add(new Skill("php", 1));
        skills.add(new Skill("css", 1));
        skills.add(new Skill("angular", 1));
        try {
            Employer employer1 = new Employer("Super technology", "g.Omsk, ul.Mira 188", "codemaker@cm.ru", "Ivan", "Ivanovich", "Petrov", "supTech", "12345678+");
            Employer employer2 = new Employer("OmskWeb", "g.Omsk, ul.Gagarina 18", "itstar@it.ru", "Boris", "Petrovich", "Petrov", "OmskWeb", "12s4f67s8g+");
            List<Requirement> requirements1 = new ArrayList<>();
            List<Requirement> requirements2 = new ArrayList<>();
            List<Requirement> requirements3 = new ArrayList<>();
            List<Requirement> requirements4 = new ArrayList<>();
            List<Requirement> requirements5 = new ArrayList<>();

            requirements1.add(new Requirement("java", 3, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("html", 5, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("javascript", 4, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("css", 4, ConditionsRequirements.NOT_NECESSARY));
            employer1.addVacancy(new Vacancy("developer", 50000, requirements1, UUID.randomUUID().toString()));//

            requirements2.add(new Requirement("java", 4, ConditionsRequirements.NECESSARY));
            requirements2.add(new Requirement("kotlin", 4, ConditionsRequirements.NECESSARY));
            employer1.addVacancy(new Vacancy("tester", 40000, requirements2, UUID.randomUUID().toString()));
            employerDao.registerEmployer(employer1);

            requirements3.add(new Requirement("php", 5, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("angular", 4, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("css", 4, ConditionsRequirements.NOT_NECESSARY));
            employer2.addVacancy(new Vacancy("web-developer", 80000, requirements3, UUID.randomUUID().toString()));

            requirements4.add(new Requirement("python", 4, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("python-tester", 61000, requirements4, UUID.randomUUID().toString()));

            requirements5.add(new Requirement("css", 3, ConditionsRequirements.NECESSARY));
            requirements5.add(new Requirement("angular", 3, ConditionsRequirements.NECESSARY));
            requirements5.add(new Requirement("java", 5, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("full-stack developer", 100000, requirements5, UUID.randomUUID().toString()));

            employerDao.registerEmployer(employer2);
            List<Vacancy> result = employeeDao.getVacanciesListOnlyName(skills);

            assertEquals(4, result.size());

            if (!result.contains(employer2.getVacancyByName("python-tester"))) {
                assert false;
            }
            if (!result.contains(employer2.getVacancyByName("web-developer"))) {
                assert false;
            }
            if (!result.contains(employer2.getVacancyByName("full-stack developer"))) {
                assert false;
            }
            if (!result.contains(employer1.getVacancyByName("tester"))) {
                assert false;
            }
            dataBase.cleanDataBase();
        } catch (ServerException es) {
            es.getStackTrace();
        }

    }

    @Test
    public void testGetVacanciesListWithOneDemand() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("java", 4));
        skills.add(new Skill("python", 5));
        skills.add(new Skill("kotlin", 1));
        skills.add(new Skill("php", 5));
        skills.add(new Skill("css", 5));
        skills.add(new Skill("angular", 5));
        try {
            Employer employer1 = new Employer("Omsk_It", "g.Omsk, ul.Mira 188", "codemaker@cm.ru", "Ivan", "Ivanovich", "Petrov", "supTech", "12345678+");
            Employer employer2 = new Employer("Itishka", "g.Omsk, ul.Gagarina 18", "itstar@it.ru", "Boris", "Petrovich", "Petrov", "Itis", "12s4f67s8g+");
            List<Requirement> requirements1 = new ArrayList<>();
            List<Requirement> requirements2 = new ArrayList<>();
            List<Requirement> requirements3 = new ArrayList<>();
            List<Requirement> requirements4 = new ArrayList<>();
            List<Requirement> requirements5 = new ArrayList<>();

            requirements1.add(new Requirement("java", 3, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("html", 5, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("javascript", 4, ConditionsRequirements.NECESSARY));
            requirements1.add(new Requirement("css", 4, ConditionsRequirements.NOT_NECESSARY));
            employer1.addVacancy(new Vacancy("developer", 50000, requirements1, UUID.randomUUID().toString()));//

            requirements2.add(new Requirement("ruby", 4, ConditionsRequirements.NECESSARY));
            requirements2.add(new Requirement("c++", 4, ConditionsRequirements.NECESSARY));
            employer1.addVacancy(new Vacancy("tester", 40000, requirements2, UUID.randomUUID().toString()));
            employerDao.registerEmployer(employer1);

            requirements3.add(new Requirement("php", 5, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("angular", 4, ConditionsRequirements.NECESSARY));
            requirements3.add(new Requirement("css", 4, ConditionsRequirements.NOT_NECESSARY));
            employer2.addVacancy(new Vacancy("web-developer", 80000, requirements3, UUID.randomUUID().toString()));

            requirements4.add(new Requirement("python", 4, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("python-tester", 61000, requirements4, UUID.randomUUID().toString()));

            requirements5.add(new Requirement("css", 3, ConditionsRequirements.NECESSARY));
            requirements5.add(new Requirement("angular", 3, ConditionsRequirements.NECESSARY));
            requirements5.add(new Requirement("java", 5, ConditionsRequirements.NECESSARY));
            employer2.addVacancy(new Vacancy("full-stack developer", 100000, requirements5, UUID.randomUUID().toString()));

            employerDao.registerEmployer(employer2);
            List<Vacancy> result = employeeDao.getVacanciesListWithOneDemand(skills);

            assertEquals(4, result.size());

            if (!result.contains(employer2.getVacancyByName("python-tester"))) {
                assert false;
            }
            if (!result.contains(employer2.getVacancyByName("web-developer"))) {
                assert false;
            }
            if (!result.contains(employer2.getVacancyByName("full-stack developer"))) {
                assert false;
            }
            if (!result.contains(employer1.getVacancyByName("developer"))) {
                assert false;
            }
            dataBase.cleanDataBase();
        } catch (ServerException es) {
            es.getStackTrace();
        }

    }
  /*  @Test
    public void testGetAll() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        employeeDaoImpl.save(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true));
        employeeDaoImpl.save(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>(), true));
        assertEquals(2, employeeDaoImpl.getAll().size());
    }

    @Test
    public void testGet() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        Employee employee1 = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true);
        Employee employee2 = new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>(), true);
        employeeDaoImpl.save(employee1);
        employeeDaoImpl.save(employee2);
        assertEquals(employee1, employeeDaoImpl.getById(employee1.getId()));
        employeeDaoImpl.delete(employee1.getId());
        employeeDaoImpl.delete(employee2.getId());
    }

    @Test
    public void testSave() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true);
        employeeDaoImpl.save(employee);
        try {
            employeeDaoImpl.save(employee);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYEE, se.getErrorCode());
        } finally {
            employeeDaoImpl.delete(employee.getId());
        }
    }

    @Test
    public void testUpdate1() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        String id = UUID.randomUUID().toString();
        List<Skill> attainments = new ArrayList<>();
        attainments.add(new Skill("ss", 4));
        Employee employee = new Employee(id, "Egor", "Ivanovich", "Ivanov", "Egorka", "eeeeee", "egorkinmail@mail.ru", true, attainments, true);
        Employee newEmployee = new Employee(id, "Egor", "Nicolaevich", "Ivanov", "Egorka", "ee444", "egorkinmail@mail.ru", true, attainments, true);
        employeeDaoImpl.save(employee);
        employeeDaoImpl.update(employee.getId(), newEmployee);
        assertEquals(newEmployee, employeeDaoImpl.getById(id));
        employeeDaoImpl.delete(newEmployee.getId());
    }*/
}
