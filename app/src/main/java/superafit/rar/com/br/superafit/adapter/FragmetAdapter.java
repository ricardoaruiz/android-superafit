package superafit.rar.com.br.superafit.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import superafit.rar.com.br.superafit.ui.activity.MainActivity;

/**
 * Created by ralmendro on 5/19/17.
 */

public class FragmetAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private List<String> fragmentNames;

    public FragmetAdapter(FragmentManager fm, List<Fragment> fragments, List<String> fragmentNames) {
        super(fm);
        this.fragments = fragments;
        this.fragmentNames = fragmentNames;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentNames.get(position);
    }
}
