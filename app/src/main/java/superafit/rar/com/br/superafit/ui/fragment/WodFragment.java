package superafit.rar.com.br.superafit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.WodMovementListItemAdapter;
import superafit.rar.com.br.superafit.controller.WodController;
import superafit.rar.com.br.superafit.event.GetWodFailureEvent;
import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;
import superafit.rar.com.br.superafit.ui.layout.GenericMessageLayout;

/**
 * Created by ralmendro on 5/19/17.
 */

public class WodFragment extends Fragment {

    private GenericMessageLayout genericMessage;

    private View view;
    private View main;

    private SwipeRefreshLayout swipe;
    private TextView textDate;
    private TextView textRounds;
    private ListView listMovements;

    private GetWodResponse wod;

    public static WodFragment newInstance() {
        return new WodFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wod, container, false);
        genericMessage = new GenericMessageLayout(view, R.id.framgent_wod_generic_message);

        textDate = (TextView) view.findViewById(R.id.fragment_wod_date);
        textRounds = (TextView) view.findViewById(R.id.fragment_wod_rounds);
        listMovements = (ListView) view.findViewById(R.id.fragment_wod_list_movement);

        main = view.findViewById(R.id.fragment_wod_main);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.fragment_wod_swipe);
        swipe.setOnRefreshListener(getRefreshListener());

        load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodResponseEvent(GetWodResponseEvent event) {
        if(event.hasData()) {
            wod = event.getData();
            fillView();
            stopSwipe();

        } else {
            showMessageWithRetry(event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodFailureEvent(GetWodFailureEvent event) {
        stopSwipe();
        showMessageWithRetry(getString(R.string.msg_remote_error));
    }

    private void load() {
        showLoading();
        WodController.getInstance(getContext()).load();
    }

    private void fillView() {
        if(wod != null) {
            textDate.setText(wod.getDate());
            textRounds.setText(wod.getRound() + " rounds");
            if(wod.getMovements() != null && !wod.getMovements().isEmpty()) {
                listMovements.setAdapter(new WodMovementListItemAdapter(getContext(), wod.getMovements()));
            }
            showMain();
        } else {
            showMessageWithRetry(getString(R.string.msg_wod_not_found));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        };
    }

    private void stopSwipe() {
        if(swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
    }
}
