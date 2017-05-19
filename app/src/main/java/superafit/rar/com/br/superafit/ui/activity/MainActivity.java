package superafit.rar.com.br.superafit.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.FragmetAdapter;
import superafit.rar.com.br.superafit.ui.fragment.ComunicadosFragment;
import superafit.rar.com.br.superafit.ui.fragment.HorariosFragment;
import superafit.rar.com.br.superafit.ui.fragment.WodFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabs = (TabLayout) findViewById(R.id.view_pager_tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmetAdapter(getSupportFragmentManager(), getFragments(), getFragmentNames()));
        tabs.setupWithViewPager(viewPager);
    }

    @NonNull
    private List<String> getFragmentNames() {
        List<String> fragmentNames;
        fragmentNames = Arrays.asList(getString(R.string.wod), getString(R.string.horarios), getString(R.string.comunicados));
        return fragmentNames;
    }

    @NonNull
    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WodFragment.getInstance());
        fragments.add(HorariosFragment.getInstance());
        fragments.add(ComunicadosFragment.getInstance());
        return fragments;
    }
}
