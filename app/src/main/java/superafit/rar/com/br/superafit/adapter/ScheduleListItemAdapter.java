package superafit.rar.com.br.superafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.service.model.response.DayScheduleResponse;
import superafit.rar.com.br.superafit.service.model.response.ListScheduleResponse;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;

/**
 * Created by ralmendro on 31/05/17.
 */

public class ScheduleListItemAdapter extends BaseAdapter {

    private Context context;

    private List<ScheduleResponse> schedules;

    public ScheduleListItemAdapter(Context context, List<ScheduleResponse> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public ScheduleResponse getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView != null ?
                convertView :
                LayoutInflater.from(context).inflate(R.layout.fragment_schedule_list_item, parent, false);

        ScheduleResponse schedule = getItem(position);

        TextView weekDay = (TextView) view.findViewById(R.id.fragment_schedules_week_day);
        TextView daySchedule = (TextView) view.findViewById(R.id.fragment_schedules_week_day_schedule);

        weekDay.setText(schedule.getWeekDay());

        StringBuilder daySchedules = new StringBuilder();
        for (DayScheduleResponse dayScheduleResponse : schedule.getDaySchedules()) {
            daySchedules.append("das ")
                    .append(dayScheduleResponse.getStart())
                    .append(" às ")
                    .append(dayScheduleResponse.getEnd())
                    .append("\n");
        }
        daySchedule.setText(daySchedules.toString());

        return view;
    }
}
