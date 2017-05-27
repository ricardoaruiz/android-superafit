package superafit.rar.com.br.superafit.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import superafit.rar.com.br.superafit.service.model.request.LoginRequest;
import superafit.rar.com.br.superafit.service.model.response.LoginResponse;

/**
 * Created by ralmendro on 27/05/17.
 */

public interface LoginService {

    @POST("login")
    Call<LoginResponse> doLogin(@Body LoginRequest request);

}
