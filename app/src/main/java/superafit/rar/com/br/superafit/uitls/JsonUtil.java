package superafit.rar.com.br.superafit.uitls;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by ralmendro on 06/06/17.
 */

public class JsonUtil {

    private static final String TAG = "JsonUtil";

    public static String toJson(Object o) {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "toJson: " + e.getMessage());
            return null;
        }
    }

    public static Object fromJson(String json, Class clazz) {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(json, clazz);
        } catch (IOException e) {
            Log.e(TAG, "fromJson: " + e.getMessage());
            return null;
        }
    }

}
