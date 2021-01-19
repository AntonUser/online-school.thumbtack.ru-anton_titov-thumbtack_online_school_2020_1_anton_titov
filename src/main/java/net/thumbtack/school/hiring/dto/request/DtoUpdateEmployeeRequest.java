package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.model.Skill;

import java.util.List;

public class DtoUpdateEmployeeRequest extends EmployeeDtoRegisterRequest {
    private String id;
    private String status;

    public DtoUpdateEmployeeRequest(String firstName, String lastName, String patronymic, String login, String password, String email, List<DtoSkill> attainmentsList, String id, String status) {
        super(firstName, lastName, patronymic, login, password, email, attainmentsList);
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
