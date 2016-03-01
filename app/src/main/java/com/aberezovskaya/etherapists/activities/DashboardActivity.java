package com.aberezovskaya.etherapists.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aberezovskaya.etherapists.R;

/**
 * The Main Activity of the application, containing
 * tabs and controls fragments
 */
public class DashboardActivity extends AppCompatActivity{

    private ViewPager mPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mPager = (ViewPager)findViewById(R.id.viewpager);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mPager);
    }
}
