package com.aberezovskaya.etherapists.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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


    private static final String ARG_FRAGMENT = "fragment_arg";


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


        /**
         * variables
         */
        private String mTag;

        Fragments(String tag) {
            mTag = tag;
        }

        abstract BaseFragment instance();

        public String getTag() {

            return mTag;
        }

        public static Fragments findFragmentByTag(String tag) {
            for (Fragments fragment : Fragments.values()) {
                if (fragment.getTag().equals(tag)) {
                    return fragment;
                }
            }
            return null;
        }

    }

    /**
     * variables
     */
    private TabLayout mTabLayout;
    private FloatingActionButton mAddButton;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mAddButton = (FloatingActionButton) findViewById(R.id.btn_add);
        mAddButton.setOnClickListener(this);
        String selectedFragment = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_FRAGMENT)) {
            selectedFragment = savedInstanceState.getString(ARG_FRAGMENT);
        }
        setupTabs(selectedFragment);
    }

    /**
     * seting  up the tabs layout,
     * and restores the last selected tab (if it exists),
     * since the TabLayout does not restore it automatically
     *
     * @param selectedTab - last selected tab
     *                    to reselect after screen orientation change
     */
    private void setupTabs(String selectedTab) {
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


        int tabCount = mTabLayout.getTabCount();
        if (!TextUtils.isEmpty(selectedTab)) {
            Fragments selectedFragment = Fragments.findFragmentByTag(selectedTab);
            if (selectedFragment != null) {
                for (int i = 0; i < tabCount; i++) {
                    TabLayout.Tab tab = mTabLayout.getTabAt(i);
                    if (tab != null && tab.getTag() != null) {
                        if ((tab.getTag()).equals(selectedFragment)) {
                            tab.select();
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TabLayout.Tab tab = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition());
        if (tab != null && tab.getTag() != null) {
            outState.putString(ARG_FRAGMENT, ((Fragments) tab.getTag()).getTag());
        }
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    /**
     * This method is called on tab selection
     * floating action button can
     * be shown and work correctly (on scrolling)
     * only within the main acitivty layout
     * So, we just make it invisible,
     * if it's unnecessary, when wrong fragment is
     * displayed
     *
     * @param fragments - currently displayed fragment
     */
    private void setupAddButton(Fragments fragments) {
        if (fragments.equals(Fragments.TAB_BODY)) {
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
