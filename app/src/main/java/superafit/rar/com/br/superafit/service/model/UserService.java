package superafit.rar.com.br.superafit.service.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import superafit.rar.com.br.superafit.service.model.request.CreateUserRequest;
import superafit.rar.com.br.superafit.service.model.response.CreateUserResponse;

/**
 * Created by ralmendro on 29/05/17.
 */

public interface UserService {

    @POST("user")
    Call<CreateUserResponse> create(@Body CreateUserRequest request);

}
