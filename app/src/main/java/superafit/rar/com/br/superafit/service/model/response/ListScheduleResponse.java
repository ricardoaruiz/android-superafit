package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 31/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListScheduleResponse implements Serializable {

    private static final long serialVersionUID = 2083933464670803990L;

    private List<ScheduleResponse> schedules;

    private boolean sync;

    public List<ScheduleResponse> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleResponse> schedules) {
        this.schedules = schedules;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
