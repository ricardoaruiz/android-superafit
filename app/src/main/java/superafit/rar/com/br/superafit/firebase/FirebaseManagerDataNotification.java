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
import superafit.rar.com.br.superafit.model.Wod;
import superafit.rar.com.br.superafit.repository.WodRepository;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;
import superafit.rar.com.br.superafit.uitls.JsonUtil;

import static superafit.rar.com.br.superafit.R.string.wod;

/**
 * Created by ralmendro on 6/13/17.
 */

public class FirebaseManagerDataNotification {

    public static final String TRAINING = "training";

    public static final String START_TRAINING_JSON = "{" + TRAINING + "=";

    private final Context context;

    private WodRepository wodRepository;

    public FirebaseManagerDataNotification(Context context) {
        this.context = context;
        wodRepository = new WodRepository(context);
    }

    public void save(String json) {
        if(json != null && !json.isEmpty()) {
            if(json.startsWith(START_TRAINING_JSON)) {
                GetWodResponse wod = manageTraining(cleanTrainingJson(json));
                EventBus.getDefault().post(new GetWodResponseEvent(wod));
            }
            //TODO outras notificações aqui
        }
    }

    public void save(Bundle extras) {
        if(extras != null) {
            if(extras.get(TRAINING) != null) {
                manageTraining(extras.get(TRAINING).toString());
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

    @NonNull
    private String cleanTrainingJson(String json) {
        String startWith = "{" + TRAINING + "=";
        if(json.startsWith(startWith)) {
            json = json.replace(startWith, "");
            json = json.substring(0, json.length()-1);
        }
        return json;
    }
}
