package superafit.rar.com.br.superafit.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.RecyclerViewScheduleAdapter;
import superafit.rar.com.br.superafit.controller.ScheduleController;
import superafit.rar.com.br.superafit.event.ListScheduleFailureEvent;
import superafit.rar.com.br.superafit.event.ListScheduleResponseEvent;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;
import superafit.rar.com.br.superafit.ui.layout.GenericMessageLayout;

/**
 * Created by ralmendro on 5/19/17.
 */

public class SchedulesFragment extends Fragment implements LoadableFragment {

    private GenericMessageLayout genericMessage;

    private ListView list;
    private List<ScheduleResponse> listSchedule;
    private RecyclerView rcScheduleList;

    private TextView textTelefoneFixo;
    private TextView textTelefoneCelular;

    private View main;

    private boolean retried;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedules, container, false);
        genericMessage = new GenericMessageLayout(view, R.id.framgent_schedule_generic_message);

        //list = (ListView) view.findViewById(R.id.fragment_schedules_list);
        main = view.findViewById(R.id.fragment_schedule_main);

        textTelefoneFixo = (TextView) view.findViewById(R.id.textTelefoneFixo);
        textTelefoneCelular = (TextView) view.findViewById(R.id.textTelefoneCelular);

        textTelefoneFixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + textTelefoneFixo.getText()));
                startActivity(intent);
            }
        });

        textTelefoneCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + textTelefoneCelular.getText()));
                startActivity(intent);
            }
        });

        rcScheduleList = (RecyclerView) view.findViewById(R.id.rv_schedule_list);
        rcScheduleList.setLayoutManager(new LinearLayoutManager(getContext()));

        load();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListScheduleResponseEvent(ListScheduleResponseEvent event) {
        if(event.hasData()) {
            retried = false;
            listSchedule = event.hasData() ? event.getData().getSchedules() : null;
            fillView();
        } else {
            if(event.isUnavailable() && !retried) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retried = true;
                        ScheduleController.getInstance(getContext()).load();
                    }
                },10000);
            } else {
                retried = false;
                showMessageWithRetry(event.getMessage());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListScheduleFailureEvent(ListScheduleFailureEvent event) {
        showMessageWithRetry(getString(R.string.msg_remote_error));
    }

    @Override
    public void load() {
        showLoading();
        ScheduleController.getInstance(getContext()).load();
    }

    @Override
    public void forceRemoteLoad() {
        showLoading();
        ScheduleController.getInstance(getContext()).load(true);
    }

    private void fillView() {
        if(listSchedule != null && !listSchedule.isEmpty()) {
            RecyclerViewScheduleAdapter adapter = new RecyclerViewScheduleAdapter(getContext(),
                    listSchedule);

            rcScheduleList.setAdapter(adapter);
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
                        forceRemoteLoad();
                    }
                });
    }
}
