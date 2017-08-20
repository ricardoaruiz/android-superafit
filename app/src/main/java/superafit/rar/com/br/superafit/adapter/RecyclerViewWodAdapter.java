package superafit.rar.com.br.superafit.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.service.model.response.DayScheduleResponse;
import superafit.rar.com.br.superafit.ui.model.WodItem;
import superafit.rar.com.br.superafit.ui.model.WodItemView;

/**
 * Created by ralmendro on 19/08/17.
 */

public class RecyclerViewWodAdapter extends RecyclerView.Adapter<RecyclerViewWodAdapter.WodViewHolder> {

    private Context context;

    List<WodItemView> wodItemList;

    public RecyclerViewWodAdapter(Context context, List<WodItemView> wodItemList) {
        this.context = context;
        this.wodItemList = wodItemList;
    }

    @Override
    public WodViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_wod,
                viewGroup, false);
        WodViewHolder wvh = new WodViewHolder(view);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WodViewHolder holder, int position) {
        fillHeader(holder, position);
        fillMovements(holder, position);
    }

    @Override
    public int getItemCount() {
        return wodItemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class WodViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView trainningType;
        TextView description;
        TextView movements;

        public WodViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.relative_layout_wod_item_header);
            trainningType = (TextView) itemView.findViewById(R.id.fragment_wod_list_movement_header_type);
            description = (TextView) itemView.findViewById(R.id.fragment_wod_list_movement_header_description);
            movements = (TextView) itemView.findViewById(R.id.fragment_wod_movements);
        }
    }

    private void fillHeader(WodViewHolder holder, int position) {
        WodItem header = wodItemList.get(position).getHeader();
        holder.trainningType.setText(header.getTrainingType() + " - " +
                header.getTrainingRound() + " rounds");

        String trainingDescription = header.getTrainingDescription();
        if(trainingDescription != null && !trainingDescription.isEmpty()) {
            holder.description.setText(trainingDescription);
        } else {
            holder.description.setVisibility(View.GONE);
        }
    }

    private void fillMovements(WodViewHolder holder, int position) {
        List<WodItem> movements = wodItemList.get(position).getMovements();
        StringBuilder strMovements = new StringBuilder();
        for(WodItem wodItem : movements) {

            String qtRep = wodItem.getMovementQtRep();
            if (qtRep.length() < 2) {
                qtRep = "0" + wodItem.getMovementQtRep();
            }

            strMovements.append(qtRep)
                    .append(" - ")
                    .append(wodItem.getMovementName())
                    .append("\n");
        }

        holder.movements.setText(strMovements);
    }

}
