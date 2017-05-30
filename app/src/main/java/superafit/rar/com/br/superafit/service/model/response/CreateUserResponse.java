package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by ralmendro on 29/05/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserResponse implements Serializable {

    private static final long serialVersionUID = -6299923414940527127L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
