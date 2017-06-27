package superafit.rar.com.br.superafit.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by ralmendro on 07/06/17.
 */

public class CreateDeviceRequest implements Serializable {

    private static final long serialVersionUID = 6358530212444211191L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
