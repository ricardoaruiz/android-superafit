package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;

/**
 * Created by ralmendro on 03/06/17.
 */

public class GetWodResponseEvent {

    private GetWodResponse data;

    private String message;

    private boolean unavailable;

    public GetWodResponseEvent() { /*Nothing*/ }

    public GetWodResponseEvent(GetWodResponse wod) {
        this.data = wod;
    }

    public GetWodResponseEvent(String message, boolean unavailable) {
        this.message = message;
        this.unavailable = unavailable;
    }

    public GetWodResponseEvent(String message) {
        this.message = message;
    }

    public GetWodResponse getData() {
        return data;
    }

    public boolean hasData() {
        return data != null;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUnavailable() {
        return unavailable;
    }
}
