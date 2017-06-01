package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public class MainController {

    private Context context;

    private ScheduleController scheduleController;

    public MainController(Context context) {
        this.context = context;
        this.scheduleController = new ScheduleController(context);
    }

    public void loadData() {
        this.scheduleController.list();
    }

}
