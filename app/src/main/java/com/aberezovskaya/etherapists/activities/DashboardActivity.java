package com.aberezovskaya.etherapists.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.adapters.BaseRecyclerCursorAdapter;
import com.aberezovskaya.etherapists.fragments.BaseFragment;
import com.aberezovskaya.etherapists.fragments.CoachingFragment;
import com.aberezovskaya.etherapists.fragments.PhysicalProblemsFragment;
import com.aberezovskaya.etherapists.fragments.StubFragment;

/**
 * The Main Activity of the application, containing
 * tabs and controls fragments
 */
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * enum used to
     * uniquely define tab-to-fragment
     * relation.
     * Tabs distinguish by
     * position is not safe,
     * cause changes in tab order (add new tab somewhere)
     * will produce potential issues with the inappropriate fragment
     * shown in the tab.
     * The way with enum, overriding instance method (to get the fragment's instance)
     * makes tab selection and UI showing process clear and
     * easy to support in future
     */
    private enum Fragments {
        TAB_COACHING("tab_coaching") {
            @Override
            BaseFragment instance() {
                return new CoachingFragment();
            }
        },
        TAB_BODY("tab_body") {
            @Override
            BaseFragment instance() {
                return new PhysicalProblemsFragment();
            }
        },
        TAB_MENTAL("tab_mental") {
            @Override
            BaseFragment instance() {
                return new StubFragment();
            }
        },
        TAB_SCORES("tab_scores") {
            @Override
            BaseFragment instance() {
                return new StubFragment();
            }
        };


        private String mTag;

        Fragments(String tag) {
            mTag = tag;
        }

        abstract BaseFragment instance();

        public String getTag() {

            return mTag;
        }

    }

    private TabLayout mTabLayout;
    private FloatingActionButton mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mAddButton = (FloatingActionButton) findViewById(R.id.btn_add);
        mAddButton.setOnClickListener(this);
        setupTabs();
    }

    private void setupTabs() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getTag() != null &&
                        tab.getTag() instanceof Fragments &&
                        ((Fragments) tab.getTag()).instance() != null) {
                    replaceFragment(((Fragments) tab.getTag()).instance());
                    setupAddButton((Fragments) tab.getTag());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.play).setTag(Fragments.TAB_COACHING), true);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.body).setTag(Fragments.TAB_BODY));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.mental).setTag(Fragments.TAB_MENTAL));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.scores).setTag(Fragments.TAB_SCORES));

    }

    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setupAddButton(Fragments fragments){
        if (fragments.equals(Fragments.TAB_BODY)){
            mAddButton.setVisibility(View.VISIBLE);
        } else {
            mAddButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(this, BodyViewActivity.class));
    }
}
