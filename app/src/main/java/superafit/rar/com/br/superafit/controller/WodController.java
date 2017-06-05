package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.event.GetWodFailureEvent;
import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;

/**
 * Created by ralmendro on 03/06/17.
 */

public class WodController {

    private static WodController instance;

    private Context context;

    private WodController(Context context) {
        this.context = context;
    }

    public static WodController getInstance(Context context) {
        if(instance == null) {
            instance = new WodController(context);
        }
        return instance;
    }

    public void load() {

        SimpleDateFormat diaMesAno = new SimpleDateFormat("dd/MM/yyyy");
        String data = diaMesAno.format(new Date());

        Call<GetWodResponse> getWodCall = ServiceFactory.getInstance().getWodService().getWod(data);
        getWodCall.enqueue(new Callback<GetWodResponse>() {
            @Override
            public void onResponse(Call<GetWodResponse> call, Response<GetWodResponse> response) {
                Log.i("load", "onResponse: sucesso.");
                if(response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                    EventBus.getDefault().post(new GetWodResponseEvent());
                } else {
                    EventBus.getDefault().post(new GetWodResponseEvent(response.body()));
                }
            }

            @Override
            public void onFailure(Call<GetWodResponse> call, Throwable t) {
                Log.e("load", "onFailure: " + t.getMessage());
                EventBus.getDefault().post(new GetWodFailureEvent());
            }
        });

    }

}
