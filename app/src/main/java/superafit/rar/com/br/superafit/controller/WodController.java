package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.converter.WodConverter;
import superafit.rar.com.br.superafit.event.GetWodFailureEvent;
import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.repository.WodRepository;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;
import superafit.rar.com.br.superafit.uitls.DateUtil;

/**
 * Created by ralmendro on 03/06/17.
 */

public class WodController {

    private static WodController instance;

    private Context context;

    private WodRepository wodRepository;

    private WodController(Context context) {
        this.context = context;
        this.wodRepository = new WodRepository(context);
    }

    public static WodController getInstance(Context context) {
        if(instance == null) {
            instance = new WodController(context);
        }
        return instance;
    }

    public void load() {
        Date today = DateUtil.toDate(DateUtil.fromDate(new Date(), DateUtil.Format.DIA_MES_ANO), DateUtil.Format.DIA_MES_ANO);
        Date lastUpdate = wodRepository.getLastUpdate();

//        if(lastUpdate != null && lastUpdate.equals(today)){
//            getLocalWod();
//        } else {
            getRemoteWod();
//        }
    }

    private void getLocalWod() {
        final GetWodResponse wodResponse = WodConverter.getInstance().fromModel(wodRepository.getWod());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new GetWodResponseEvent(wodResponse));
            }
        },1000);
    }

    private void getRemoteWod() {
        String data = DateUtil.fromDate(new Date(), DateUtil.Format.DIA_MES_ANO);
        Call<GetWodResponse> getWodCall = ServiceFactory.getInstance().getWodService().getWod(data);
        getWodCall.enqueue(new Callback<GetWodResponse>() {
            @Override
            public void onResponse(Call<GetWodResponse> call, Response<GetWodResponse> response) {

                switch (response.code()) {
                    case HttpURLConnection.HTTP_NO_CONTENT:
                        Log.i("getRemoteWod", "onResponse: Não foram encontrados dados.");
                        EventBus.getDefault().post(new GetWodResponseEvent(context.getString(R.string.msg_wod_not_found)));
                        break;
                    case HttpURLConnection.HTTP_UNAVAILABLE:
                        Log.e("getRemoteWod", "onResponse: " + context.getString(R.string.msg_service_unavailable));
                        EventBus.getDefault().post(new GetWodResponseEvent(context.getString(R.string.msg_service_unavailable)));
                        break;
                    default:
                        wodRepository.save(WodConverter.getInstance().toModel(response.body()));
                        EventBus.getDefault().post(new GetWodResponseEvent(response.body()));
                        break;
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
