package superafit.rar.com.br.superafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.ui.model.WodItemList;

/**
 * Created by ralmendro on 03/06/17.
 */

public class WodMovementListItemAdapter extends BaseAdapter {

    private Context context;

    private List<WodItemList> items;

    public WodMovementListItemAdapter(Context context, List<WodItemList> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public WodItemList getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WodItemList item = getItem(position);

        View view = null;

        if(item.isHeader()) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.fragment_wod_movement_header_list_item, parent, false);

            TextView textType = (TextView) view.findViewById(R.id.fragment_wod_list_movement_header_type);
            TextView textDescription = (TextView) view.findViewById(R.id.fragment_wod_list_movement_header_description);

            textType.setText(item.getTrainingType() + " - " + item.getTrainingRound() + " rounds");
            if(item.getTrainingDescription() != null) {
                textDescription.setText(item.getTrainingDescription());
            } else {
                textDescription.setVisibility(View.GONE);
            }

        } else {
            view = LayoutInflater.from(context).inflate(
                    R.layout.fragment_wod_movement_list_item, parent, false);

            TextView textRepetition = (TextView) view.findViewById(R.id.fragment_wod_list_movement_rep);
            TextView textName = (TextView) view.findViewById(R.id.fragment_wod_list_movement_name);

            textRepetition.setText(item.getMovementQtRep() + " -");
            textName.setText(item.getMovementName());
        }

        return view;
    }
}
