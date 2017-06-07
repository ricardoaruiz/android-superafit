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

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.ScheduleListItemAdapter;
import superafit.rar.com.br.superafit.controller.ScheduleController;
import superafit.rar.com.br.superafit.event.ListScheduleSuccessEvent;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;

/**
 * Created by ralmendro on 5/19/17.
 */

public class SchedulesFragment extends Fragment {

    private ScheduleController scheduleController;

    private ListView list;
    private List<ScheduleResponse> listSchedule;

    private View main;
    private View noData;

    private SchedulesFragment() {

    }

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
        list = (ListView) view.findViewById(R.id.fragment_schedules_list);

        main = view.findViewById(R.id.fragment_schedule_main);
        noData = view.findViewById(R.id.fragment_schedules_no_data);

        //initProgress();
        scheduleController = ScheduleController.getInstance(getContext());
        scheduleController.load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(ListScheduleSuccessEvent event) {
        listSchedule = event.hasData() ? event.getData().getSchedules() : null;
        fillList();
    }

    private void fillList() {
        if(listSchedule != null && !listSchedule.isEmpty()) {
            list.setAdapter(new ScheduleListItemAdapter(this.getContext(), listSchedule));
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
        main.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
    }

    private void showNoData() {
        main.setVisibility(View.GONE);
        noData.setVisibility(View.VISIBLE);
    }
}
