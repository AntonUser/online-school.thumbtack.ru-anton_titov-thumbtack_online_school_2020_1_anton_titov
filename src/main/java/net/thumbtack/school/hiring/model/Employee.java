package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ServerException;

import java.io.Serializable;
import java.util.*;

public class Employee  extends User implements Serializable{
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

    public void setAttainmentsList(List<Attainments> attainmentsList) {
        this.attainmentsList = attainmentsList;
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
                Objects.equals(attainmentsList, employee.attainmentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attainmentsList, status);
    }
}
