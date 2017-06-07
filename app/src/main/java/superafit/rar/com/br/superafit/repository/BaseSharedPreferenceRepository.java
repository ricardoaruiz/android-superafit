package superafit.rar.com.br.superafit.repository;

import android.content.Context;
import android.content.SharedPreferences;

import superafit.rar.com.br.superafit.uitls.JsonUtil;

/**
 * Created by ralmendro on 06/06/17.
 */

public abstract class BaseSharedPreferenceRepository {

    private Context context;

    public BaseSharedPreferenceRepository(Context context) {
        this.context = context;
    }

    abstract String getPreferenceKey();

    protected void save(String value, String key) {
        SharedPreferences preference = getPreference();
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    protected void remove(String key) {
        SharedPreferences preference = getPreference();
        SharedPreferences.Editor edit = preference.edit();
        edit.remove(key);
        edit.apply();
    }

    protected String getValue(String key, String defaultValue) {
        return getPreference().getString(key, defaultValue);
    }

    private SharedPreferences getPreference() {
        return this.context.getSharedPreferences(getPreferenceKey(), Context.MODE_PRIVATE);
    }
}
