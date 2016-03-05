package com.aberezovskaya.etherapists.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.adapters.BodyProblemDialogCursorAdapter;
import com.aberezovskaya.etherapists.daos.BodyPart;
import com.aberezovskaya.etherapists.providers.DataContract;

import model.BodyPartEnum;


public class AddProblemDialogFragment extends AppCompatDialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = AddProblemDialogFragment.class.getSimpleName();

    private static final String ARG_BODY_PART = "body_part";
    private static final int PROBLEM_LOADER_ID = 0;


    private BodyPartEnum mBodyPart = null;
    private ListView mProblemsList;


    public static AddProblemDialogFragment getInstance(String bodyPart) {
        AddProblemDialogFragment fragment = new AddProblemDialogFragment();
        Bundle bundle = new Bundle();
        if (bodyPart != null) {
            bundle.putString(ARG_BODY_PART, bodyPart);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String bodyPart = null;
        if (getArguments() != null && (bodyPart = getArguments().getString(ARG_BODY_PART)) != null) {
            mBodyPart = BodyPartEnum.getBPByTag(bodyPart);
            if (mBodyPart != null && mBodyPart != BodyPartEnum.UNKNOWN) {
                ViewGroup view = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.dlg_add_problem, null, false);
                mProblemsList = (ListView) view.findViewById(R.id.problem_list);
                ((TextView) view.findViewById(R.id.tv_body_part)).setText(mBodyPart.getTag());
                mProblemsList.setClickable(true);
                mProblemsList.setChoiceMode(ListViewCompat.CHOICE_MODE_SINGLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setView(view);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                getLoaderManager().restartLoader(PROBLEM_LOADER_ID, null, this);
                return builder.create();
            }
        }

        Log.d(TAG, "Something goes wrong. The part of body is unknown!");
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = DataContract.BodyPart.COLUMN_NAME + " = ?";
        return new CursorLoader(getActivity().getApplicationContext(), DataContract.BodyProblem.JOIN_CONTENT_URI, null, selection, new String[]{mBodyPart.getTag()}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            BodyProblemDialogCursorAdapter adapter = new BodyProblemDialogCursorAdapter(getContext(), data, false);
            mProblemsList.setAdapter(adapter);
            mProblemsList.setItemChecked(0, true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
