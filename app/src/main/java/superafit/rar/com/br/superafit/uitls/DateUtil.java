package superafit.rar.com.br.superafit.uitls;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ralmendro on 06/06/17.
 */

public class DateUtil {

    private static final String TAG = "DateUtil";

    public static Date toDate(String value, DateUtil.Format format) {
        try {
            return format.getFormater().parse(value);
        } catch (ParseException e) {
            Log.e(TAG, "toDate: " + e.getMessage());
            return null;
        }
    }

    public static String fromDate(Date date, DateUtil.Format format) {
        return format.getFormater().format(date);
    }

    public enum Format {

        DIA_MES_ANO(new SimpleDateFormat("dd/MM/yyyy"));

        private final SimpleDateFormat formater;

        Format(SimpleDateFormat formater) {
            this.formater = formater;
        }

        public SimpleDateFormat getFormater() {
            return formater;
        }
    }

}
