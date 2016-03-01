package com.aberezovskaya.etherapists.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.aberezovskaya.etherapists.R;
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
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new HomeFragment())
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onFragmentClick() {
        //start another activity
    }
}
