package superafit.rar.com.br.superafit.service.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by ralmendro on 07/06/17.
 */

public class CreateDeviceRequest implements Serializable {

    private static final long serialVersionUID = 8806273965597859910L;

    @JsonProperty("user_id")
    private String userId;

    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
