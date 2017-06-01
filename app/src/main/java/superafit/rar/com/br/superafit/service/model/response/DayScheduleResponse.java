package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by ralmendro on 31/05/17.
 */

public class DayScheduleResponse implements Serializable {

    @JsonProperty("schedule_start")
    private String start;

    @JsonProperty("schedule_end")
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
