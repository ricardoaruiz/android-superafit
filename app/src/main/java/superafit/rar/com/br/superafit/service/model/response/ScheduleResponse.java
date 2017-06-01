package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ScheduleResponse implements Serializable {

    private static final long serialVersionUID = 7491023362859184080L;

    @JsonProperty("week_day")
    private String weekDay;

    @JsonProperty("day_schedules")
    private List<DayScheduleResponse> daySchedules;

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public List<DayScheduleResponse> getDaySchedules() {
        return daySchedules;
    }

    public void setDaySchedules(List<DayScheduleResponse> daySchedules) {
        this.daySchedules = daySchedules;
    }
}
