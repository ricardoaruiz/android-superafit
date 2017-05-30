package superafit.rar.com.br.superafit.service.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralmendro on 29/05/17.
 */

public class ErrorsResponse implements Serializable {

    private static final long serialVersionUID = -6302356639265722435L;

    private List<ErrorResponse> errors;

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public String getFormatedErrors() {
        StringBuilder strErrors = new StringBuilder();

        boolean hasMore = errors.size() > 1;
        for (ErrorResponse error : errors) {
            strErrors.append(error.getError());
            if(hasMore) {
                strErrors.append("\n");
            }
        }
        return strErrors.toString();
    }
}
