package superafit.rar.com.br.superafit.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import superafit.rar.com.br.superafit.model.Device;
import superafit.rar.com.br.superafit.service.model.request.CreateDeviceRequest;

/**
 * Created by ralmendro on 07/06/17.
 */

public interface DeviceService {

    @POST("app/device")
    Call<Void> create(@Body CreateDeviceRequest request);

}
