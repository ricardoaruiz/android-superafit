package superafit.rar.com.br.superafit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.service.model.response.DayScheduleResponse;
import superafit.rar.com.br.superafit.service.model.response.ScheduleResponse;

/**
 * Created by ralmendro on 19/08/17.
 */

public class RecyclerViewScheduleAdapter extends
        RecyclerView.Adapter<RecyclerViewScheduleAdapter.ScheduleViewHolder> {

    private Context context;

    private List<ScheduleResponse> schedules;

    public RecyclerViewScheduleAdapter(Context context, List<ScheduleResponse> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_schedule,
                viewGroup, false);
        ScheduleViewHolder wvh = new ScheduleViewHolder(view);
        return wvh;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {

        ScheduleResponse schedule = schedules.get(position);

        holder.weekDay.setText(schedule.getWeekDay());

        StringBuilder daySchedules = new StringBuilder();
        for (DayScheduleResponse dayScheduleResponse : schedule.getDaySchedules()) {
            daySchedules.append("das ")
                    .append(dayScheduleResponse.getStart())
                    .append(" às ")
                    .append(dayScheduleResponse.getEnd())
                    .append("\n");
        }
        holder.daySchedule.setText(daySchedules.toString());

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView weekDay;
        TextView daySchedule;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            weekDay = (TextView) itemView.findViewById(R.id.fragment_schedules_week_day);
            daySchedule = (TextView) itemView.findViewById(
                    R.id.fragment_schedules_week_day_schedule);
        }
    }

}
