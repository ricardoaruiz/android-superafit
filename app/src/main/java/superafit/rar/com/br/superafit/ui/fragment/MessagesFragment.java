package superafit.rar.com.br.superafit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.event.ListMessagesFailureEvent;
import superafit.rar.com.br.superafit.event.ListMessagesResponseEvent;
import superafit.rar.com.br.superafit.ui.layout.GenericMessageLayout;

/**
 * Created by ralmendro on 5/19/17.
 */

public class MessagesFragment extends Fragment implements LoadableFragment {

    private GenericMessageLayout genericMessage;

    private View main;
    private ListView list;

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        genericMessage = new GenericMessageLayout(view, R.id.framgent_messages_generic_message);

        list = (ListView) view.findViewById(R.id.fragment_messages_list);
        main = view.findViewById(R.id.fragment_message_main);

        load();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void load() {
        //Carregar as mensagens locais
        showMain();
    }

    @Override
    public void forceRemoteLoad() {
        //Carregar as mansagens remotas
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListMessagesResponseEvent(ListMessagesResponseEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListMessagesFailureEvent(ListMessagesFailureEvent event) {

    }

    private void showMain() {
        main.setVisibility(View.VISIBLE);
        genericMessage.hideMessage();
    }

    private void showLoading() {
        main.setVisibility(View.GONE);
        genericMessage.showMessage(getString(R.string.loading));
    }

    private void showMessageWithRetry(String message) {
        main.setVisibility(View.GONE);
        genericMessage.showMessage(message,
                new GenericMessageLayout.OnClickTryAgainEvent() {
                    @Override
                    public void onClick() {
                        load();
                    }
                });
    }
}
