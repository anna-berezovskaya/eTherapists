package com.aberezovskaya.etherapists.dialogs;


import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aberezovskaya.etherapists.R;


public class AddProblemDialogFragment extends AppCompatDialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String ARG_BODY_PART = "body_part";
    private static final int PROBLEM_LOADER_ID = 0;


    private String mBodyPart = null;
    private RecyclerView mProblemsList;


    public static final AddProblemDialogFragment getInstance(String bodyPart) {
        AddProblemDialogFragment fragment = new AddProblemDialogFragment();
        Bundle bundle = new Bundle();
        if (bodyPart != null) {
            bundle.putString(ARG_BODY_PART, bodyPart);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null && (mBodyPart = getArguments().getString(ARG_BODY_PART)) != null) {
            ViewGroup view = (ViewGroup)LayoutInflater.from(getContext()).inflate(R.layout.dlg_add_problem, null, false);
            mProblemsList = (RecyclerView) view.findViewById(R.id.problem_list);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(view);
            return builder.create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
