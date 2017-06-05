package superafit.rar.com.br.superafit.ui.fragment;

import android.app.ProgressDialog;
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

    private ProgressDialog progress;

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

        //initProgress();
        scheduleController = ScheduleController.getInstance(getContext());
        scheduleController.load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(ListScheduleSuccessEvent event) {
        listSchedule = event.getData().getSchedules();
        fillList();
    }

    private void fillList() {
        if(listSchedule != null && !listSchedule.isEmpty()) {
            list.setAdapter(new ScheduleListItemAdapter(this.getContext(), listSchedule));
            endProgress();
        }
    }

    private void initProgress() {
        progress = ProgressDialog.show(this.getContext(), getString(R.string.processing), getString(R.string.processing));
    }

    private void endProgress() {
        if(progress!= null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
