package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.*;

public class Employee extends User {
    private String lastName;
    private String firstName;
    private String patronymic;
    private List<Attainments> attainmentsList;
    private boolean status;


    public Employee(String id, String firstName, String patronymic, String lastName, String login, String password, String email, boolean status, List<Attainments> attainments, boolean activity) throws ServerException {
        super(id, login, password, email, activity);
        setLastName(lastName);
        setFirstName(firstName);
        setPatronymic(patronymic);
        attainmentsList = attainments;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws ServerException {
        if (firstName == null || firstName.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_FIRST_NAME_EXCEPTION);
        }
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws ServerException {
        if (lastName == null || lastName.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LAST_NAME_EXCEPTION);
        }
        this.lastName = lastName;
    }

    public void addAttainments(Attainments attainments) {
        attainmentsList.add(attainments);
    }

    public void updateAttainments(String oldAttainments, Attainments newAttainments) {
        for (Attainments attainments : attainmentsList) {
            if (oldAttainments.equals(attainments.getNameSkill())) {
                attainmentsList.set(attainmentsList.indexOf(attainments), newAttainments);
            }
        }
    }

    public void removeAttainments(Attainments attainments) {
        attainmentsList.remove(attainments);
    }

    public List<Attainments> getAttainmentsList() {
        return new ArrayList<>(attainmentsList);
    }

    public Set<String> getNamesAttainments() {
        Set<String> outSet = new HashSet<>();
        for (Attainments attainments : attainmentsList) {
            outSet.add(attainments.getNameSkill());
        }
        return outSet;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return status == employee.status &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(patronymic, employee.patronymic) &&
                Objects.equals(attainmentsList, employee.attainmentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lastName, firstName, patronymic, attainmentsList, status);
    }

}
