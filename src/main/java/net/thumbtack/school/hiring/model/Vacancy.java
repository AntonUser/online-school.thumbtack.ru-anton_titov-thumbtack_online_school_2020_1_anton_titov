package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.*;


public class Vacancy {
    private final String id;
    private String name;
    private int salary;
    private List<Requirement> requirements;
    private VacancyStatus vacancyStatus;

    public Vacancy(String name, int salary, List<Requirement> requirements) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.salary = salary;
        this.requirements = requirements;
        this.vacancyStatus = VacancyStatus.ACTIVE;
    }

    public void addRequirement(Requirement demand) {
        requirements.add(demand);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public VacancyStatus getStatus() {
        return vacancyStatus;
    }

    public void setStatus(VacancyStatus vacancyStatus) {
        this.vacancyStatus = vacancyStatus;
    }

    public void updateDemand(String nameDemand, Requirement newDemand) throws ServerException {
        requirements.set(requirements.indexOf(searchRequirement(nameDemand)), newDemand);
    }


    public Set<String> getNamesRequirements() {
        Set<String> outSet = new HashSet<>();
        for (Requirement demand : requirements) {
            outSet.add(demand.getName());
        }
        return outSet;
    }

    public void removeDemand(String nameDemand) throws ServerException {
        requirements.remove(searchRequirement(nameDemand));
    }

    private Requirement searchRequirement(String nameDemand) throws ServerException {
        for (Requirement demand : requirements) {
            if (demand.getName().equals(nameDemand)) {
                return demand;
            }
        }
        throw new ServerException(ErrorCode.DEMAND_EXCEPTION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return salary == vacancy.salary &&
                Objects.equals(id, vacancy.id) &&
                Objects.equals(name, vacancy.name) &&
                Objects.equals(requirements, vacancy.requirements) &&
                vacancyStatus == vacancy.vacancyStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, requirements, vacancyStatus);
    }
}
