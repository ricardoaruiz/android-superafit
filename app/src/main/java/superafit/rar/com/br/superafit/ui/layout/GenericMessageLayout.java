package superafit.rar.com.br.superafit.ui.layout;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import superafit.rar.com.br.superafit.R;

/**
 * Created by ralmendro on 09/06/17.
 */

public class GenericMessageLayout {

    private final View view;

    private TextView textMessage;

    private View layout;

    public GenericMessageLayout(View view, int layoutId) {
        this.view = view;
        layout = this.view.findViewById(layoutId);
    }

    public void showMessage(String msg) {
        layout.setVisibility(View.VISIBLE);
        TextView textMessage = (TextView) layout.findViewById(R.id.layout_generic_message_text);
        textMessage.setText(msg);

        layout.findViewById(R.id.layout_generic_message_btn_try_again).setVisibility(View.GONE);
    }

    public void showMessage(String msg, final OnClickTryAgainEvent event) {
        showMessage(msg);
        Button btnTryAgain = (Button) layout.findViewById(R.id.layout_generic_message_btn_try_again);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.onClick();
            }
        });
        btnTryAgain.setVisibility(View.VISIBLE);
    }

    public void hideMessage() {
        layout.setVisibility(View.GONE);
    }

    public interface OnClickTryAgainEvent {
        void onClick();
    }

}
