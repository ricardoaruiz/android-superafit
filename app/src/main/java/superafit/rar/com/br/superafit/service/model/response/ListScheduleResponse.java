package superafit.rar.com.br.superafit.service.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ListScheduleResponse implements Serializable {

    private static final long serialVersionUID = 2083933464670803990L;

    private List<ScheduleResponse> schedules;

    public List<ScheduleResponse> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleResponse> schedules) {
        this.schedules = schedules;
    }
}
