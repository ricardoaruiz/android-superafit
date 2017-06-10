package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.CreateUserResponse;
import superafit.rar.com.br.superafit.service.model.response.ErrorsResponse;

/**
 * Created by ralmendro on 29/05/17.
 */

public class CreateUserResponseEvent {

    private CreateUserResponse data;

    private ErrorsResponse validations;

    private String message;

    public CreateUserResponseEvent(CreateUserResponse body) {
        this.data = body;
    }

    public CreateUserResponseEvent(ErrorsResponse validations) {
        this.validations = validations;
    }

    public CreateUserResponseEvent(String message) {
        this.message = message;
    }

    public CreateUserResponse getData() {
        return data;
    }

    public ErrorsResponse getValidations() {
        return validations;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasData() {
        return data != null;
    }

    public boolean hasValidations() {
        return validations != null;
    }
}
