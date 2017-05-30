package superafit.rar.com.br.superafit.service.model.response;

import java.io.Serializable;

/**
 * Created by ralmendro on 30/05/17.
 */

public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 8967355074868933753L;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}