package superafit.rar.com.br.superafit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import superafit.rar.com.br.superafit.R;

/**
 * Created by ralmendro on 5/19/17.
 */

public class WodFragment extends Fragment {

    public static WodFragment getInstance() {
        return new WodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wod, container, false);
    }
}
