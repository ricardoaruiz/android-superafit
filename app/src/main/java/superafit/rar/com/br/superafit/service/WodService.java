package superafit.rar.com.br.superafit.service;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;

/**
 * Created by ralmendro on 03/06/17.
 */

public interface WodService {

    @GET("day-training")
    Call<GetWodResponse> getWod(@Query("date") String data);

}
