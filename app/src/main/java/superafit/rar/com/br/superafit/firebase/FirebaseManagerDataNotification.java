package superafit.rar.com.br.superafit.firebase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.converter.WodConverter;
import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.event.ListScheduleResponseEvent;
import superafit.rar.com.br.superafit.model.Wod;
import superafit.rar.com.br.superafit.repository.ScheduleRepository;
import superafit.rar.com.br.superafit.repository.WodRepository;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;
import superafit.rar.com.br.superafit.uitls.JsonUtil;

import static superafit.rar.com.br.superafit.R.string.wod;

/**
 * Created by ralmendro on 6/13/17.
 */

public class FirebaseManagerDataNotification {

    public static final String TRAINING = "training";
    public static final String START_TRAINING_JSON = "{" + TRAINING + "=";

    public static final String SCHEDULE = "schedule";
    public static final String START_SCHEDULE_JSON = "{" + SCHEDULE + "=";

    private final Context context;

    private WodRepository wodRepository;

    private ScheduleRepository scheduleRepository;

    public FirebaseManagerDataNotification(Context context) {
        this.context = context;
        wodRepository = new WodRepository(context);
        scheduleRepository = new ScheduleRepository(context);
    }

    /**
     * Usado quando a app está em foreground e recebe uma notificação
     * @param json
     */
    public void save(String json) {
        if(json != null && !json.isEmpty()) {
            if(json.startsWith(START_TRAINING_JSON)) {
                GetWodResponse wod = manageTraining(cleanJson(json, START_TRAINING_JSON));
                EventBus.getDefault().post(new GetWodResponseEvent(wod));
            }
            if(json.startsWith(START_SCHEDULE_JSON)) {
                ListScheduleResponse schedules = manageSchedule(cleanJson(json, START_SCHEDULE_JSON));
                EventBus.getDefault().post(new ListScheduleResponseEvent(schedules));
            }
            //TODO outras notificações aqui
        }
    }

    /**
     * Usado quando a app está em background e recebe uma notificação
     * @param extras
     */
    public void save(Bundle extras) {
        if(extras != null) {
            if(extras.get(TRAINING) != null) {
                manageTraining(extras.get(TRAINING).toString());
            }
            if(extras.get(SCHEDULE) != null) {
                manageSchedule(extras.get(SCHEDULE).toString());
            }
            //TODO outras notificações aqui
        }
    }

    private GetWodResponse manageTraining(String json) {
        GetWodResponse training = (GetWodResponse) JsonUtil.fromJson(json, GetWodResponse.class);
        Wod wod = WodConverter.getInstance().toModel(training);
        wodRepository.save(wod);
        return training;
    }

    private ListScheduleResponse manageSchedule(String json) {
        ListScheduleResponse schedule = (ListScheduleResponse) JsonUtil.fromJson(json, ListScheduleResponse.class);
        scheduleRepository.save(schedule);
        return schedule;
    }

    private String cleanJson(String json, String startJson) {
        if(json.startsWith(startJson)) {
            json = json.replace(startJson, "");
            json = json.substring(0, json.length()-1);
        }
        return json;
    }
}
