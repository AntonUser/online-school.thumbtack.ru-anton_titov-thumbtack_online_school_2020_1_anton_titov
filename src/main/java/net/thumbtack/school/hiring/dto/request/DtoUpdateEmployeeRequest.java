package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Skill;

import java.util.List;

public class DtoUpdateEmployeeRequest extends EmployeeDtoRegisterRequest {
    private String id;
    private boolean activity;

    public DtoUpdateEmployeeRequest(String firstName, String lastName, String patronymic, String login, String password, String email, List<Skill> attainmentsList, String id, boolean activity) {
        super(firstName, lastName, patronymic, login, password, email, attainmentsList);
        this.id = id;
        this.activity = activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    /*public void validate() throws ServerException {
        super.validate();
        if (id == null || id.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_TOKEN_EXCEPTION);
        }
    }*/

}
