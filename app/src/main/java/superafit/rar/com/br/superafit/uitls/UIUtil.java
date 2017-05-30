package superafit.rar.com.br.superafit.uitls;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import superafit.rar.com.br.superafit.ui.activity.LoginActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by ralmendro on 21/05/17.
 */

public class UIUtil {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static TextWatcher getWatcher(final Activity activity, final int length) {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Nothing */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == length) {
                    UIUtil.hideKeyboard(activity);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { /* Nothing */ }
        };
    }
}
