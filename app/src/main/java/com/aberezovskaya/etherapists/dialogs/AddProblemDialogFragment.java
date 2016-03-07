package com.aberezovskaya.etherapists.dialogs;


import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.adapters.BodyProblemDialogCursorAdapter;
import com.aberezovskaya.etherapists.daos.PhysicalProblem;
import com.aberezovskaya.etherapists.providers.DataContract;

import com.aberezovskaya.etherapists.model.BodyParts;
import com.aberezovskaya.etherapists.utils.ObservableLoader;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * dialog fragment class to add the physical problem
 * The custom view used, cause the UI layout on Mockup
 * can be achieved by the custom view only
 */
public class AddProblemDialogFragment extends AppCompatDialogFragment {

    /**
     * consts
     */

    // class name for logging
    private static final String TAG = AddProblemDialogFragment.class.getSimpleName();

    // fragment argumen (the part of body)
    private static final String ARG_BODY_PART = "body_part";
    // the key for loader observer to stor in Observable Cache
    private static String KEY_BODY_PROBLEMS_LOADER = "body_problems_loader_task";

    /**
     * variables
     */
    private AddProblemDialogListener mListener;
    private ObservableLoader<Cursor> mLoader;

    private BodyParts mBodyPart = null;
    private ListView mProblemsList;
    private SeekBar mIntensityBar;

    /**
     * callback interface
     */
    public interface AddProblemDialogListener {
        void onNegativeBtnClick();

        void onPositiveBtnClick(PhysicalProblem problem);

    }


    public void setListener(AddProblemDialogListener listener) {
        mListener = listener;
    }

    /**
     * instance class to pass the arguments
     *
     * @param bodyPart - the part of body selected by user
     * @return dialog fragment instance
     */
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
            mBodyPart = BodyParts.getBPByTag(bodyPart);
            if (mBodyPart != null && mBodyPart != BodyParts.UNKNOWN) {

                ViewGroup view = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.dlg_add_problem, null, false);
                mProblemsList = (ListView) view.findViewById(R.id.problem_list);
                ((TextView) view.findViewById(R.id.tv_body_part)).setText(mBodyPart.getTag());
                mProblemsList.setClickable(true);
                mProblemsList.setChoiceMode(ListViewCompat.CHOICE_MODE_SINGLE);
                mIntensityBar = (SeekBar) view.findViewById(R.id.seek_intensity);

                AppCompatButton btnOk = (AppCompatButton) view.findViewById(R.id.btn_ok);
                AppCompatButton btnCancel = (AppCompatButton) view.findViewById(R.id.btn_cancel);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            PhysicalProblem problem = new PhysicalProblem();

                            problem.setBodyProblemId((Integer) mProblemsList.getAdapter().getItem(mProblemsList.getCheckedItemPosition()));
                            problem.setIntensity(mIntensityBar.getProgress());
                            mListener.onPositiveBtnClick(problem);
                        }
                        dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setView(view);

                // initiate data loading
                mLoader = new ObservableLoader<Cursor>(getLoadObservable(), mProblemsObserver);
                mLoader.getSubscription(KEY_BODY_PROBLEMS_LOADER);

                return builder.create();
            }
        }

        Log.d(TAG, "Something goes wrong. The part of body is unknown!");
        return super.onCreateDialog(savedInstanceState);
    }

    protected Observable<Cursor> getLoadObservable() {
        return Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                String selection = DataContract.BodyPart.COLUMN_NAME + " = ?";
                Cursor cursor = getContext().getContentResolver().query(DataContract.BodyProblem.JOIN_CONTENT_URI, null, selection, new String[]{mBodyPart.getTag()}, null);
                if (cursor != null) {
                    subscriber.onNext(cursor);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new SQLiteException());
                }

            }
        });
    }

    private Observer<Cursor> mProblemsObserver = new Observer<Cursor>() {

        @Override
        public void onCompleted() {
            mLoader.unsubscribeLoaderTask(true);
        }

        @Override
        public void onError(Throwable e) {
            mLoader.unsubscribeLoaderTask(true);

        }

        @Override
        public void onNext(Cursor aCursor) {

            BodyProblemDialogCursorAdapter adapter = new BodyProblemDialogCursorAdapter(getContext(), aCursor, false);
            mProblemsList.setAdapter(adapter);
            mProblemsList.setItemChecked(0, true);
        }
    };


}
