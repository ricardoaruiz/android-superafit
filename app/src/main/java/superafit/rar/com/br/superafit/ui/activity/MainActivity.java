package superafit.rar.com.br.superafit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.adapter.FragmetAdapter;
import superafit.rar.com.br.superafit.controller.LoginController;
import superafit.rar.com.br.superafit.controller.MainController;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.ui.fragment.MessagesFragment;
import superafit.rar.com.br.superafit.ui.fragment.SchedulesFragment;
import superafit.rar.com.br.superafit.ui.fragment.WodFragment;

public class MainActivity extends AppCompatActivity {

    private MainController mainController;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainController = new MainController(this);
        this.loginController = new LoginController(this);

        TabLayout tabs = (TabLayout) findViewById(R.id.view_pager_tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmetAdapter(getSupportFragmentManager(), getFragments(), getFragmentNames()));
        tabs.setupWithViewPager(viewPager);

        this.mainController.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.common_menu_sair :
                loginController.logoff();
                final Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                finish();
        }

        return false;
    }

    @NonNull
    private List<String> getFragmentNames() {
        List<String> fragmentNames;
        fragmentNames = Arrays.asList(getString(R.string.wod), getString(R.string.schedules), getString(R.string.messages));
        return fragmentNames;
    }

    @NonNull
    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WodFragment.getInstance());
        fragments.add(SchedulesFragment.getInstance());
        fragments.add(MessagesFragment.getInstance());
        return fragments;
    }
}
