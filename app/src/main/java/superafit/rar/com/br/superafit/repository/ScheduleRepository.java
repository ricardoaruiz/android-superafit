package superafit.rar.com.br.superafit.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import superafit.rar.com.br.superafit.service.ServiceConstants;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;
import superafit.rar.com.br.superafit.uitls.JsonUtil;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ScheduleRepository extends BaseSharedPreferenceRepository {

    private static final String SCHEDULES = "SCHEDULES";
    private static final String NOTIFICATION_RECEIVED = "NOTIFICATION_RECEIVED";

    private Context context;

    public ScheduleRepository(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    String getPreferenceKey() {
        return "superafit.rar.com.br.superafit.repository.ScheduleRepository";
    }

    public void save(ListScheduleResponse data) {
        if(data.getSchedules() != null && !data.getSchedules().isEmpty()) {
            String jsonSchedules = JsonUtil.toJson(data);
            if(jsonSchedules != null) {
                save(jsonSchedules, SCHEDULES);
            }
        }
    }

    public ListScheduleResponse getSchedules() {
        String jsonSchedules = getValue(SCHEDULES, null);
        if(jsonSchedules != null) {
            return (ListScheduleResponse) JsonUtil.fromJson(jsonSchedules, ListScheduleResponse.class);
        }
        return null;
    }

    public void setNotificationReceived(Boolean notificationReceived) {
        save(notificationReceived.toString(), NOTIFICATION_RECEIVED);
    }

    public boolean isNotificationReceived() {
        return Boolean.valueOf(getValue(NOTIFICATION_RECEIVED, "false"));
    }    
}
