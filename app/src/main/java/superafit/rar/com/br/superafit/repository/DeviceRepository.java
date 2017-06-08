package superafit.rar.com.br.superafit.repository;

import android.content.Context;

import superafit.rar.com.br.superafit.model.Device;
import superafit.rar.com.br.superafit.uitls.JsonUtil;

/**
 * Created by ralmendro on 07/06/17.
 */

public class DeviceRepository extends BaseSharedPreferenceRepository {

    private static final String DEVICE = "DEVICE";

    public DeviceRepository(Context context) {
        super(context);
    }

    @Override
    String getPreferenceKey() {
        return "superafit.rar.com.br.superafit.repository.DeviceRepository";
    }

    public void save(Device device) {
        if(device != null) {
            save(JsonUtil.toJson(device), DEVICE);
        }
    }

    public Device getDevice() {
        String jsonDevice = getValue(DEVICE, null);
        if(jsonDevice != null) {
            return (Device) JsonUtil.fromJson(jsonDevice, Device.class);
        }
        return null;
    }

}
