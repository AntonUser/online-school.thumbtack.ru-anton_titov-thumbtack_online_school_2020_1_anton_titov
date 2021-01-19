package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.*;

public class DtoAddVacancyRequest {
    private String namePost;
    private int salary;
    private List<DtoRequirement> requirements;
    private String id;

    public DtoAddVacancyRequest(String namePost, int salary, List<DtoRequirement> requirements) {
        this.namePost = namePost;
        this.salary = salary;
        this.requirements = requirements;
        this.id = UUID.randomUUID().toString();
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public List<DtoRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<DtoRequirement> requirements) {
        this.requirements = requirements;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void validate() throws ServerException, NullPointerException {
        if (getNamePost() == null || getNamePost().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME_POST);
        } else if (getSalary() < 0) {
            throw new ServerException(ErrorCode.SALARY_EXCEPTION);
        } else if (getId() == null || getId().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoAddVacancyRequest that = (DtoAddVacancyRequest) o;
        return salary == that.salary &&
                Objects.equals(namePost, that.namePost) &&
                Objects.equals(requirements, that.requirements) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namePost, salary, requirements, id);
    }
}
