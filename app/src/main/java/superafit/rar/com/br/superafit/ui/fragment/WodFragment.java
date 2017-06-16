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

import java.util.ArrayList;
import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.WodMovementListItemAdapter;
import superafit.rar.com.br.superafit.controller.WodController;
import superafit.rar.com.br.superafit.event.GetWodFailureEvent;
import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.service.model.response.GetWodDataResponse;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;
import superafit.rar.com.br.superafit.service.model.response.MovementResponse;
import superafit.rar.com.br.superafit.ui.layout.GenericMessageLayout;
import superafit.rar.com.br.superafit.ui.model.WodItemList;

/**
 * Created by ralmendro on 5/19/17.
 */

public class WodFragment extends Fragment {

    private GenericMessageLayout genericMessage;

    private View view;
    private View main;

    private TextView textDate;
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
        listMovements = (ListView) view.findViewById(R.id.fragment_wod_list_movement);
        main = view.findViewById(R.id.fragment_wod_main);

        load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodResponseEvent(GetWodResponseEvent event) {
        if(event.hasData()) {
            wod = event.getData();
            fillView();
        } else {
            showMessageWithRetry(event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWodFailureEvent(GetWodFailureEvent event) {
        showMessageWithRetry(getString(R.string.msg_remote_error));
    }

    private void load() {
        showLoading();
        WodController.getInstance(getContext()).load();
    }

    private void fillView() {
        final List<WodItemList> trainingList = getWodList();

        if(trainingList != null && !trainingList.isEmpty()) {
            textDate.setText(trainingList.get(0).getTrainingDate());
            listMovements.setAdapter(new WodMovementListItemAdapter(getContext(), trainingList));
            showMain();
        } else {
            showMessageWithRetry(getString(R.string.msg_wod_not_found));
        }
    }

    private List<WodItemList> getWodList() {
        List<WodItemList> toReturn = null;
        if(wod != null && wod.getData() != null) {
            toReturn = new ArrayList<>();
            for(GetWodDataResponse data : wod.getData()) {

                WodItemList header = new WodItemList();
                header.setHeader(true);
                header.setTrainingDate(data.getDate());
                header.setTrainingDescription(data.getDescription());
                header.setTrainingRound(data.getRound());
                header.setTrainingType(data.getType());
                toReturn.add(header);

                for(MovementResponse m : data.getMovements()) {
                    WodItemList item = new WodItemList();
                    item.setHeader(false);
                    item.setMovementName(m.getName());
                    item.setMovementDescription(m.getDescription());
                    item.setMovementTranslate(m.getTranslate());
                    item.setMovementQtRep(m.getQtRep());
                    toReturn.add(item);
                }

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

}
