package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.dto.request.DtoRequirement;
import net.thumbtack.school.hiring.model.Requirement;

import java.util.List;
import java.util.Objects;

public class DtoVacancyResponse {
    private String id;
    private String name;
    private int salary;
    private List<DtoRequirement> requirements;
    private String vacancyStatus;

    public DtoVacancyResponse(String id, String name, int salary, List<DtoRequirement> requirements, String vacancyStatus) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.requirements = requirements;
        this.vacancyStatus = vacancyStatus;
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

    public List<DtoRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<DtoRequirement> requirements) {
        this.requirements = requirements;
    }

    public String getVacancyStatus() {
        return vacancyStatus;
    }

    public void setVacancyStatus(java.lang.String vacancyStatus) {
        this.vacancyStatus = vacancyStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoVacancyResponse that = (DtoVacancyResponse) o;
        return salary == that.salary &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(requirements, that.requirements) &&
                Objects.equals(vacancyStatus, that.vacancyStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, requirements, vacancyStatus);
    }
}
