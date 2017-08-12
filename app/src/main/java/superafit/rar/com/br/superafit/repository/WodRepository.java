package superafit.rar.com.br.superafit.repository;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Date;

import superafit.rar.com.br.superafit.model.Wod;
import superafit.rar.com.br.superafit.uitls.DateUtil;
import superafit.rar.com.br.superafit.uitls.JsonUtil;

/**
 * Created by ralmendro on 06/06/17.
 */

public class WodRepository extends BaseSharedPreferenceRepository {

    private static final String LAST_UPDATE = "LAST_UPDATE";
    private static final String WOD = "WOD";
    private static final String NOTIFICATION_RECEIVED = "NOTIFICATION_RECEIVED";

    private Context context;

    public WodRepository(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    String getPreferenceKey() {
        return "superafit.rar.com.br.superafit.repository.WodRepository";
    }

    public Date getLastUpdate() {
        String lastUpdate = getValue(LAST_UPDATE, null);
        if(lastUpdate != null) {
            return DateUtil.toDate(lastUpdate, DateUtil.Format.DIA_MES_ANO);
        }
        return null;
    }

    public Wod getWod() {
        String wod = getValue(WOD, null);
        if(wod != null) {
            return (Wod) JsonUtil.fromJson(wod, Wod.class);
        }
        return null;
    }

    public void save(Wod wod) {
        if(wod != null) {
            String json = JsonUtil.toJson(wod);
            if(json != null) {
                save(DateUtil.fromDate(new Date(), DateUtil.Format.DIA_MES_ANO), LAST_UPDATE);
                save(json, WOD);
            }
        }
    }

    public void remove() {
        super.remove(WOD);
        super.remove(LAST_UPDATE);
    }

    public void setNotificationReceived(Boolean notificationReceived) {
        save(notificationReceived.toString(), NOTIFICATION_RECEIVED);
    }

    public boolean isNotificationReceived() {
        return Boolean.valueOf(getValue(NOTIFICATION_RECEIVED, "false"));
    }
}
