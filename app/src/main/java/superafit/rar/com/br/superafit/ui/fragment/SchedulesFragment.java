package superafit.rar.com.br.superafit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.ScheduleListItemAdapter;
import superafit.rar.com.br.superafit.controller.ScheduleController;
import superafit.rar.com.br.superafit.event.ListScheduleFailureEvent;
import superafit.rar.com.br.superafit.event.ListScheduleResponseEvent;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;
import superafit.rar.com.br.superafit.ui.layout.GenericMessageLayout;

/**
 * Created by ralmendro on 5/19/17.
 */

public class SchedulesFragment extends Fragment {

    private ScheduleController scheduleController;

    private GenericMessageLayout genericMessage;

    private ListView list;
    private List<ScheduleResponse> listSchedule;

    private SwipeRefreshLayout swipe;
    private View main;

    public static SchedulesFragment newInstance() {
        return new SchedulesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedules, container, false);
        genericMessage = new GenericMessageLayout(view, R.id.framgent_schedule_generic_message);

        list = (ListView) view.findViewById(R.id.fragment_schedules_list);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.fragment_schedules_swipe);

        main = view.findViewById(R.id.fragment_schedule_main);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });

        load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListScheduleResponseEvent(ListScheduleResponseEvent event) {
        if(event.hasData()) {
            listSchedule = event.hasData() ? event.getData().getSchedules() : null;
            fillList();
            stopSwipe();
        } else {
            showMessageWithRetry(event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListScheduleFailureEvent(ListScheduleFailureEvent event) {
        stopSwipe();
        showMessageWithRetry(getString(R.string.msg_remote_error));
    }

    private void load() {
        showLoading();
        scheduleController = ScheduleController.getInstance(getContext());
        scheduleController.load();
    }

    private void fillList() {
        if(listSchedule != null && !listSchedule.isEmpty()) {
            list.setAdapter(new ScheduleListItemAdapter(this.getContext(), listSchedule));
            showMain();
        } else {
            showMessageWithRetry(getString(R.string.msg_schedule_not_found));
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


    private void stopSwipe() {
        if(swipe != null && swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
    }
}
