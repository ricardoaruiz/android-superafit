package superafit.rar.com.br.superafit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 06/06/17.
 */

public class Wod implements Serializable {

    private static final long serialVersionUID = 1166556574939386096L;

    private List<WodItem> data;

    public List<WodItem> getData() {
        return data;
    }

    public void setData(List<WodItem> data) {
        this.data = data;
    }
}
