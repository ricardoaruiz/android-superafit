package superafit.rar.com.br.superafit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 12/06/17.
 */

public class WodItem implements Serializable{

    private static final long serialVersionUID = 6644576309161579784L;

    private String date;

    private String type;

    private String round;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WodMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<WodMovement> movements) {
        this.movements = movements;
    }


}
