package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 6/12/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWodDataResponse implements Serializable {

    private String date;

    private String type;

    private String round;

    private String sequence;

    private String description;

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

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MovementResponse> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementResponse> movements) {
        this.movements = movements;
    }

}
