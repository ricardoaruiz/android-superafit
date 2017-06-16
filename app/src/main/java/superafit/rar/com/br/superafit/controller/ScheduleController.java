package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.event.ListScheduleFailureEvent;
import superafit.rar.com.br.superafit.event.ListScheduleResponseEvent;
import superafit.rar.com.br.superafit.repository.ScheduleRepository;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;

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
        load(false);
    }

    public void load(boolean forceRemote) {
        if(forceRemote) {
            getRemoteSchedule();
        } else {
            final ListScheduleResponse schedules = scheduleRepository.getSchedules();

            if (schedules != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new ListScheduleResponseEvent(schedules));
                    }
                }, 1000);
            } else {
                getRemoteSchedule();
            }
        }
    }

    private void getRemoteSchedule() {
        Call<ListScheduleResponse> callSchedules = ServiceFactory.getInstance().getScheduleService().list();
        callSchedules.enqueue(new Callback<ListScheduleResponse>() {
            @Override
            public void onResponse(Call<ListScheduleResponse> call, Response<ListScheduleResponse> response) {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_NO_CONTENT:
                        Log.i("getRemoteSchedule", "onResponse: Não foram encontrados dados.");
                        EventBus.getDefault().post(new ListScheduleResponseEvent(context.getString(R.string.msg_schedule_not_found)));
                        break;
                    case HttpURLConnection.HTTP_UNAVAILABLE:
                        Log.e("getRemoteSchedule", "onResponse: " + context.getString(R.string.msg_service_unavailable));
                        EventBus.getDefault().post(new ListScheduleResponseEvent(context.getString(R.string.msg_service_unavailable), true));
                        break;
                    default:
                        ListScheduleResponse data = response.body();
                        scheduleRepository.save(data);
                        EventBus.getDefault().post(new ListScheduleResponseEvent(data));
                        break;
                }
            }

            @Override
            public void onFailure(Call<ListScheduleResponse> call, Throwable t) {
                Log.e("load", "onFailure: " + t.getMessage());
                EventBus.getDefault().post(new ListScheduleFailureEvent());
            }
        });
    }

}
