package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;

/**
 * Created by ralmendro on 03/06/17.
 */

public class GetWodResponseEvent {

    private GetWodResponse wod;

    public GetWodResponseEvent() {
    }

    public GetWodResponseEvent(GetWodResponse wod) {
        this.wod = wod;
    }

    public GetWodResponse getWod() {
        return wod;
    }
}
