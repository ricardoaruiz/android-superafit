package superafit.rar.com.br.superafit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.service.model.response.MovementResponse;

/**
 * Created by ralmendro on 03/06/17.
 */

public class WodMovementListItemAdapter extends BaseAdapter {

    private Context context;

    private List<MovementResponse> movements;

    public WodMovementListItemAdapter(Context context, List<MovementResponse> movements) {
        this.context = context;
        this.movements = movements;
    }

    @Override
    public int getCount() {
        return movements.size();
    }

    @Override
    public MovementResponse getItem(int position) {
        return movements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_wod_movement_list_item, parent, false);

        MovementResponse movement = getItem(position);

        TextView textRepetition = (TextView) view.findViewById(R.id.fragment_wod_list_movement_rep);
        TextView textName = (TextView) view.findViewById(R.id.fragment_wod_list_movement_name);

        textRepetition.setText(movement.getQtRep() + " - ");
        textName.setText(movement.getName());

        return view;
    }
}
