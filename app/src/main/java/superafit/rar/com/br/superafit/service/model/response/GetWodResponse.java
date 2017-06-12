package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 03/06/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWodResponse implements Serializable {

    private static final long serialVersionUID = -6214545927808415742L;

    private List<GetWodDataResponse> data;

    public List<GetWodDataResponse> getData() {
        return data;
    }

    public void setData(List<GetWodDataResponse> data) {
        this.data = data;
    }
}
