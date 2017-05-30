package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.ErrorsResponse;

/**
 * Created by ralmendro on 29/05/17.
 */

public class CreateUserFailureEvent {

    private ErrorsResponse body;

    public CreateUserFailureEvent() {
    }

    public CreateUserFailureEvent(ErrorsResponse body) {
        this.body = body;
    }

    public ErrorsResponse getBody() {
        return body;
    }
}
