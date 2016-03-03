package com.aberezovskaya.etherapists.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aberezovskaya.etherapists.R;

/**
 * Fragment to display the body view and
 * add new physical problems
 */
public class BodyViewFragment extends BaseFragment {

  //  private View mBodyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View bodyView = view.findViewById(R.id.body_view);
        bodyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProblemDialog();
            }
        });

    }

    private void showAddProblemDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("AppCompatDialog");
        builder.setMessage("Lorem ipsum dolor...");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
