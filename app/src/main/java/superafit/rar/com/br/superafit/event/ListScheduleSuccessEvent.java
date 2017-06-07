package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ListScheduleSuccessEvent {

    private ListScheduleResponse data;

    public ListScheduleSuccessEvent() {
    }

    public ListScheduleSuccessEvent(ListScheduleResponse schedules) {
        this.data = schedules;
    }

    public ListScheduleResponse getData() {
        return data;
    }

    public boolean hasData() {
        return this.data != null && !this.data.getSchedules().isEmpty();
    }
}
