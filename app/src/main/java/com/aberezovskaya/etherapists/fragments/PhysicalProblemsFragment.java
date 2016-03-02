package com.aberezovskaya.etherapists.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aberezovskaya.etherapists.R;


/**
 * Fragment to manage currently selected physical problems
 */
public class PhysicalProblemsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_physical_problems, container, false);
    }
}
