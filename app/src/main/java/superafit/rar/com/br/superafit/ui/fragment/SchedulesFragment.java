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
import superafit.rar.com.br.superafit.event.ListScheduleSuccessEvent;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;

/**
 * Created by ralmendro on 5/19/17.
 */

public class SchedulesFragment extends Fragment {

    private ListView list;

    public static SchedulesFragment getInstance() {
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
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(ListScheduleSuccessEvent event) {
        fillList(event.getData().getSchedules());
    }

    private void fillList(List<ScheduleResponse> listSchedule) {
        if(listSchedule != null && !listSchedule.isEmpty()) {
            list.setAdapter(new ScheduleListItemAdapter(this.getContext(), listSchedule));
        }
    }
}
