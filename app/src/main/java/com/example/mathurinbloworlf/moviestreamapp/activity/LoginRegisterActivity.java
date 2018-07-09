package com.example.mathurinbloworlf.moviestreamapp.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.adapter.ViewPagerAdapter;
import com.example.mathurinbloworlf.moviestreamapp.fragment.login_register.Login;
import com.example.mathurinbloworlf.moviestreamapp.fragment.login_register.Register;
import com.example.mathurinbloworlf.moviestreamapp.manager.SharedPrefManager;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            //startActivity(new Intent(LoginRegisterActivity.this, Profile.class));
            finish();
            return;
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.feed_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Login(), "LOG IN");
        adapter.addFragment(new Register(), "REGISTER");
        viewPager.setAdapter(adapter);

    }
}
