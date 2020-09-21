package net.thumbtack.school.hiring.model;

import java.util.*;


public class Vacancy {
    private String id;
    private String name;
    private int salary;
    // REVU эти 2 map мне непонятны
    // что такое String и Integer ?
    // давайте в скайпе обсудим, можно голосом
    private Map<String, Integer> obligatoryDemands;
    private Map<String, Integer> notObligatoryDemands;
    private boolean status;//сделать enum

    public Vacancy(String id,String namePost, int salary, Map<String, Integer> obligatoryDemands, Map<String, Integer> notObligatoryDemands, String token) {
        this.id = id;
        this.name = namePost;
        this.salary = salary;
        this.status = true;
        this.obligatoryDemands = obligatoryDemands;
        this.notObligatoryDemands = notObligatoryDemands;
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

    public Map<String, Integer> getObligatoryDemands() {
        return obligatoryDemands;
    }

    public Map<String, Integer> getNotObligatoryDemands() {
        return notObligatoryDemands;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void updateDemand(String nameDemand, Requirement newDemand) {
        if (newDemand.isNecessary()) {
            obligatoryDemands.remove(nameDemand, obligatoryDemands.get(nameDemand));
            obligatoryDemands.put(newDemand.getNameDemand(), newDemand.getSkill());
        } else {
            notObligatoryDemands.remove(nameDemand, notObligatoryDemands.get(nameDemand));
            notObligatoryDemands.put(newDemand.getNameDemand(), newDemand.getSkill());
        }
    }

    public Set<String> getNamesDemands() {
        Set<String> outSet = new HashSet<>();
        outSet.addAll(obligatoryDemands.keySet());
        outSet.addAll(notObligatoryDemands.keySet());
        return outSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return salary == vacancy.salary &&
                status == vacancy.status &&
                Objects.equals(id, vacancy.id) &&
                Objects.equals(name, vacancy.name) &&
                Objects.equals(obligatoryDemands, vacancy.obligatoryDemands) &&
                Objects.equals(notObligatoryDemands, vacancy.notObligatoryDemands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, obligatoryDemands, notObligatoryDemands, status);
    }
}
