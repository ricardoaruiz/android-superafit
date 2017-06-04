package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.event.ListScheduleSuccessEvent;
import superafit.rar.com.br.superafit.repository.ScheduleRepository;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ScheduleController {

    private static ScheduleController instance;

    private Context context;
    private ScheduleRepository scheduleRepository;

    private ScheduleController(Context context) {
        this.context = context;
        this.scheduleRepository = new ScheduleRepository(context);
    }

    public static ScheduleController getInstance(Context context) {
        if(instance == null) {
            instance = new ScheduleController(context);
        }
        return instance;
    }

    public void load() {

        final ListScheduleResponse schedules = scheduleRepository.getSchedules();

        if(schedules != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new ListScheduleSuccessEvent(schedules));
                }
            },1000);
        } else {
            Call<ListScheduleResponse> callSchedules = ServiceFactory.getInstance().getScheduleService().list();
            callSchedules.enqueue(new Callback<ListScheduleResponse>() {
                @Override
                public void onResponse(Call<ListScheduleResponse> call, Response<ListScheduleResponse> response) {
                    Log.i("load", "onResponse: sucesso");
                    ListScheduleResponse data = response.body();
                    scheduleRepository.save(data);
                    EventBus.getDefault().post(new ListScheduleSuccessEvent(data));
                }

                @Override
                public void onFailure(Call<ListScheduleResponse> call, Throwable t) {
                    Log.e("load", "onFailure: " + t.getMessage());
                }
            });
        }
    }

}
