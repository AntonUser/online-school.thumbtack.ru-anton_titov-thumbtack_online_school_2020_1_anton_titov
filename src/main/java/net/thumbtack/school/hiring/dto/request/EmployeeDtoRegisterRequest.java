package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.model.Attainments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeDtoRegisterRequest extends DtoRegisterRequest {

    private boolean status;
    private List<Attainments> attainmentsList;

    public EmployeeDtoRegisterRequest(String firstName, String lastName, String patronymic, String login, String password, String email, ArrayList<Attainments> attainments, boolean status) {
        super(firstName, lastName, patronymic, login, password, email);
        setStatus(status);
        setAttainmentsList(attainments);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Attainments> getAttainmentsList() {
        return attainmentsList;
    }

    public void setAttainmentsList(List<Attainments> attainmentsList) {
        this.attainmentsList = attainmentsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployeeDtoRegisterRequest that = (EmployeeDtoRegisterRequest) o;
        return status == that.status &&
                Objects.equals(attainmentsList, that.attainmentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, attainmentsList);
    }
}
