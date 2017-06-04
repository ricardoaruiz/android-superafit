package superafit.rar.com.br.superafit.service.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 03/06/17.
 */

public class GetWodResponse implements Serializable {

    private static final long serialVersionUID = -6214545927808415742L;

    private String date;

    private String type;

    private String round;

    private List<MovementResponse> movements;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public List<MovementResponse> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementResponse> movements) {
        this.movements = movements;
    }
}
