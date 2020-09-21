package net.thumbtack.school.hiring.model;

import java.io.Serializable;
import java.util.*;

public class Employee extends User implements Serializable {
    private List<Skill> attainmentsList;

    public Employee(String firstName, String patronymic, String lastName, String login, String password, String email, List<Skill> attainments) {
        super(login, password, email, lastName, firstName, patronymic);
        attainmentsList = attainments;
    }

    public void addAttainments(Skill attainments) {
        attainmentsList.add(attainments);
    }

    public void updateAttainments(String oldAttainments, Skill newAttainments) {
        for (Skill attainments : attainmentsList) {
            if (oldAttainments.equals(attainments.getName())) {
                attainmentsList.set(attainmentsList.indexOf(attainments), newAttainments);
            }
        }
    }

    public void removeAttainments(Skill attainments) {
        attainmentsList.remove(attainments);
    }

    public List<Skill> getAttainmentsList() {
        return new ArrayList<>(attainmentsList);
    }

    public Set<String> getNamesAttainments() {
        Set<String> outSet = new HashSet<>();
        for (Skill attainments : attainmentsList) {
            outSet.add(attainments.getName());
        }
        return outSet;
    }

    public void setAttainmentsList(List<Skill> attainmentsList) {
        this.attainmentsList = attainmentsList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(attainmentsList, employee.attainmentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attainmentsList);
    }
}
