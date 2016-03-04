package com.aberezovskaya.etherapists.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.fragments.BodyViewFragment;
import com.aberezovskaya.etherapists.fragments.HomeFragment;

/**
 * Activity simply displays the home screen
 * May provide some additional app initialisation
 * or the slide show in future releases
 */
public class HomeActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public void onFragmentClick() {
        startActivity(new Intent(this, DashboardActivity.class));
    }
}
