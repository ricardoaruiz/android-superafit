package superafit.rar.com.br.superafit.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ScheduleRepository {

    private static final String SCHEDULE_PREFERENCE = "superafit.rar.com.br.superafit.repository.ScheduleRepository";
    private static final String SCHEDULES = "SCHEDULES";
    private Context context;

    public ScheduleRepository(Context context) {
        this.context = context;
    }

    public void save(ListScheduleResponse data) {
        if(data.getSchedules() != null && !data.getSchedules().isEmpty()) {
            String jsonSchedules = getJsonSchedulesFromList(data);
            SharedPreferences preference = getPreference();
            SharedPreferences.Editor edit = preference.edit();
            edit.putString(SCHEDULES, jsonSchedules);
            edit.commit();
        }
    }

    public ListScheduleResponse getSchedules() {

        ListScheduleResponse listSchedule = null;

        SharedPreferences preference = getPreference();
        String jsonSchedules = preference.getString(SCHEDULES, null);

        if(jsonSchedules != null) {
            listSchedule = getListScheduleFromJson(jsonSchedules);
        }

        return listSchedule;
    }

    private String getJsonSchedulesFromList(ListScheduleResponse schedules) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(schedules);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private ListScheduleResponse getListScheduleFromJson(String jsonSchedules) {
        ListScheduleResponse listSchedule = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            listSchedule = mapper.readValue(jsonSchedules,ListScheduleResponse.class);
        } catch (IOException e) {
            Log.e("leitura", "getListScheduleFromJson: " + e.getMessage() );
        }
        return listSchedule;
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(SCHEDULE_PREFERENCE, Context.MODE_PRIVATE);
    }
}
