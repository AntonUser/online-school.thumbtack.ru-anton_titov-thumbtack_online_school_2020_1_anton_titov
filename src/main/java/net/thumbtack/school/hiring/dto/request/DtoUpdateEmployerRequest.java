package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoUpdateEmployerRequest extends EmployerDtoRegisterRequest {
    private String id;
    private boolean activity;

    public DtoUpdateEmployerRequest(String firstName, String lastName, String patronymic, String login, String password, String name, String address, String email, String id, boolean activity) {
        super(firstName, lastName, patronymic, login, password, name, address, email);
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

    public void validate() throws ServerException {
        super.validate();
        if (id == null || id.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }
}
