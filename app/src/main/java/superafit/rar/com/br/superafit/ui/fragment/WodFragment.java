package superafit.rar.com.br.superafit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

/**
 * Created by ralmendro on 5/19/17.
 */

public class WodFragment extends Fragment {

    private View view;

    private SwipeRefreshLayout swipe;
    private SwipeRefreshLayout swipeNoData;
    private TextView textDate;
    private TextView textRounds;
    private ListView listMovements;

    private GetWodResponse wod;
    private View loading;
    private View main;
    private View noData;

    private WodFragment() {

    }

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

        textDate = (TextView) view.findViewById(R.id.fragment_wod_date);
        textRounds = (TextView) view.findViewById(R.id.fragment_wod_rounds);
        listMovements = (ListView) view.findViewById(R.id.fragment_wod_list_movement);

        loading = view.findViewById(R.id.fragment_wod_loading);
        main = view.findViewById(R.id.fragment_wod_main);
        noData = view.findViewById(R.id.fragment_wod_no_data);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.fragment_wod_swipe);
        swipe.setOnRefreshListener(getRefreshListener());

        swipeNoData = (SwipeRefreshLayout) view.findViewById(R.id.fragment_wod_swipe_no_data);
        swipeNoData.setOnRefreshListener(getRefreshListener());

        load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodResponseEvent(GetWodResponseEvent event) {
        wod = event.getWod();
        fillView();
        stopSwipe();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodFailureEvent(GetWodFailureEvent event) {
        Snackbar.make(textDate,
                R.string.msg_load_information_error,
                Snackbar.LENGTH_LONG).show();
        stopSwipe();
        showMain();
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
            showNoData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showMain() {
        loading.setVisibility(View.GONE);
        noData.setVisibility(View.GONE);
        main.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        noData.setVisibility(View.GONE);
        main.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    private void showNoData() {
        loading.setVisibility(View.GONE);
        main.setVisibility(View.GONE);
        noData.setVisibility(View.VISIBLE);
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
        if(swipeNoData.isRefreshing()) {
            swipeNoData.setRefreshing(false);
        }
    }
}
