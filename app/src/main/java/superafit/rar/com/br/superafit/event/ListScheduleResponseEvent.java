package superafit.rar.com.br.superafit.event;

import android.util.Log;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ListScheduleResponseEvent {

    private ListScheduleResponse data;

    private String message;

    private boolean unavailable;

    public ListScheduleResponseEvent() {
    }

    public ListScheduleResponseEvent(ListScheduleResponse schedules) {
        this.data = schedules;
    }

    public ListScheduleResponseEvent(String message, boolean unavailable) {
        this.message = message;
        this.unavailable = unavailable;
    }

    public ListScheduleResponseEvent(String message) {
        this.message = message;
    }

    public ListScheduleResponse getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasData() {
        return this.data != null && this.data.getSchedules() != null && !this.data.getSchedules().isEmpty();
    }

    public boolean isUnavailable() {
        return unavailable;
    }
}
