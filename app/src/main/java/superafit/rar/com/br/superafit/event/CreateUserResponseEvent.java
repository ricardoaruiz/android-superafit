package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.service.model.response.CreateUserResponse;
import superafit.rar.com.br.superafit.service.model.response.ErrorsResponse;

/**
 * Created by ralmendro on 29/05/17.
 */

public class CreateUserResponseEvent {

    private final CreateUserResponse body;

    public CreateUserResponseEvent(CreateUserResponse body) {
        this.body = body;
    }

    public CreateUserResponse getBody() {
        return body;
    }
}
