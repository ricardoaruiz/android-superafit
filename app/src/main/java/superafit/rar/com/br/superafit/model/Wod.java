package superafit.rar.com.br.superafit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 06/06/17.
 */

public class Wod implements Serializable {

    private static final long serialVersionUID = -814749243002123569L;

    private String date;

    private String type;

    private String round;

    private List<WodMovement> movements;

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

    public List<WodMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<WodMovement> movements) {
        this.movements = movements;
    }

}
