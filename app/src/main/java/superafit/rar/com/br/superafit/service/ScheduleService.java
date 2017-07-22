package superafit.rar.com.br.superafit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public interface ScheduleService {

    @GET("app/schedule")
    Call<ListScheduleResponse> list();

}
