package superafit.rar.com.br.superafit.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.RecyclerViewWodAdapter;
import superafit.rar.com.br.superafit.controller.WodController;
import superafit.rar.com.br.superafit.event.GetWodFailureEvent;
import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.service.model.response.GetWodDataResponse;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;
import superafit.rar.com.br.superafit.service.model.response.MovementResponse;
import superafit.rar.com.br.superafit.ui.layout.GenericMessageLayout;
import superafit.rar.com.br.superafit.ui.model.WodItem;
import superafit.rar.com.br.superafit.ui.model.WodItemView;

/**
 * Created by ralmendro on 5/19/17.
 */

public class WodFragment extends Fragment implements LoadableFragment {

    private GenericMessageLayout genericMessage;

    private View view;
    private View main;

    private TextView textDate;
    private ListView listMovements;
    private RecyclerView rcWodList;

    private GetWodResponse wod;

    private boolean retried;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wod, container, false);
        genericMessage = new GenericMessageLayout(view, R.id.framgent_wod_generic_message);

        textDate = (TextView) view.findViewById(R.id.fragment_wod_date);
        main = view.findViewById(R.id.fragment_wod_main);

        rcWodList = (RecyclerView) view.findViewById(R.id.rv_wod_list);
        rcWodList.setLayoutManager(new LinearLayoutManager(getContext()));

        load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodResponseEvent(GetWodResponseEvent event) {
        if(event.hasData()) {
            retried = false;
            wod = event.getData();
            fillView();
        } else {
            if(event.isUnavailable() && !retried) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retried = true;
                        WodController.getInstance(getContext()).load();
                    }
                }, 10000);
            } else {
                retried = false;
                showMessageWithRetry(event.getMessage());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodFailureEvent(GetWodFailureEvent event) {
        showMessageWithRetry(getString(R.string.msg_remote_error));
    }

    @Override
    public void load() {
        showLoading();
        WodController.getInstance(getContext()).load();
    }

    @Override
    public void forceRemoteLoad() {
        showLoading();
        WodController.getInstance(getContext()).load(true);
    }

    private void fillView() {
        final List<WodItemView> trainingList = getWodList();

        if(trainingList != null && !trainingList.isEmpty()) {
            RecyclerViewWodAdapter adapter = new RecyclerViewWodAdapter(getContext(), trainingList);
            rcWodList.setAdapter(adapter);
            showMain();
        } else {
            showMessageWithRetry(getString(R.string.msg_wod_not_found));
        }
    }
    private List<WodItemView> getWodList() {
        List<WodItemView> toReturn = null;
        if(wod != null && wod.getData() != null) {
            toReturn = new ArrayList<>();
            for(GetWodDataResponse data : wod.getData()) {

                WodItemView wodItemView = new WodItemView();

                WodItem header = new WodItem();
                header.setHeader(true);
                header.setTrainingDate(data.getDate());
                header.setTrainingDescription(data.getDescription());
                header.setTrainingRound(data.getRound());
                header.setTrainingType(data.getType());
                wodItemView.setHeader(header);

                for(MovementResponse m : data.getMovements()) {
                    WodItem item = new WodItem();
                    item.setHeader(false);
                    item.setMovementName(m.getName());
                    item.setMovementDescription(m.getDescription());
                    item.setMovementTranslate(m.getTranslate());
                    item.setMovementQtRep(m.getQtRep());
                    wodItemView.getMovements().add(item);
                }

                toReturn.add(wodItemView);
            }
        }
        return toReturn;
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
                        forceRemoteLoad();
                    }
                });

    }

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                forceRemoteLoad();
            }
        };
    }

}
