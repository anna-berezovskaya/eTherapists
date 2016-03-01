package com.aberezovskaya.etherapists.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aberezovskaya.etherapists.R;

/**
 * Fragment for the home activity
 * Displaying Home (greetings) screen UI
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public interface HomeFragmentListener{
        void onFragmentClick();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.main_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HomeFragmentListener listener = getTarget(HomeFragmentListener.class);
        if (listener != null){
            listener.onFragmentClick();
        }
    }
}
