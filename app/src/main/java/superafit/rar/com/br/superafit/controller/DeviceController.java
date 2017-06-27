package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.model.Device;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.repository.DeviceRepository;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.request.CreateDeviceRequest;

/**
 * Created by ralmendro on 07/06/17.
 */

public class DeviceController {

    private static final String TAG = "DeviceController";

    private Context context;

    private DeviceRepository deviceRepository;

    private LoginRepository loginRepository;

    public DeviceController(Context context) {
        this.context = context;
        this.deviceRepository = new DeviceRepository(context);
        this.loginRepository = new LoginRepository(context);
    }

    public Device getDevice() {
        return deviceRepository.getDevice();
    }

    public void syncronize(final Device device) {
        deviceRepository.save(device);
        callSync(device);
    }

    public void syncronize() {
        final Device device = deviceRepository.getDevice();

        if(device != null && !device.isSyncronized()) {
            callSync(device);
        }
    }

    private void callSync(final Device device) {
        Call<Void> deviceSyncCall = ServiceFactory.getInstance().getDeviceService().create(getCreateDeviceRequest(device));

        deviceSyncCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == HttpURLConnection.HTTP_CREATED) {
                    device.syncronized();
                    deviceRepository.save(device);
                } else {
                    Log.e(TAG, "onFailure: Erro ao sincronizar o token do device - HTTP_Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "onFailure: Erro ao sincronizar o token do device: " + t.getMessage());
            }
        });
    }

    private CreateDeviceRequest getCreateDeviceRequest(Device device) {
        CreateDeviceRequest request = new CreateDeviceRequest();
        request.setToken(device.getToken());
        return request;
    }
}
