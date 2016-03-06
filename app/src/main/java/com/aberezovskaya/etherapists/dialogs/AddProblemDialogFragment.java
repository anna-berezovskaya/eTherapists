package com.aberezovskaya.etherapists.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.adapters.BodyProblemDialogCursorAdapter;
import com.aberezovskaya.etherapists.daos.PhysicalProblem;
import com.aberezovskaya.etherapists.providers.DataContract;

import com.aberezovskaya.etherapists.model.BodyPartEnum;
import com.aberezovskaya.etherapists.utils.ObservableLoader;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;


public class AddProblemDialogFragment extends AppCompatDialogFragment {

    private static final String TAG = AddProblemDialogFragment.class.getSimpleName();

    private static final String ARG_BODY_PART = "body_part";
    private static String KEY_BODY_PROBLEMS_LOADER ="body_problems_loader_task";

    private AddProblemDialogListener mListener;
    private ObservableLoader<Cursor> mLoader;

    private BodyPartEnum mBodyPart = null;
    private ListView mProblemsList;
    private SeekBar mIntensityBar;

    public interface AddProblemDialogListener {
        void onNegativeBtnClick();
        void onPositiveBtnClick(PhysicalProblem problem);

    }

    public void setListener(AddProblemDialogListener listener){
        mListener = listener;
    }

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
                mIntensityBar = (SeekBar) view.findViewById(R.id.seek_intensity);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setView(view);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mListener != null) {
                            PhysicalProblem problem = new PhysicalProblem();

                            problem.setBodyProblemId((Integer) mProblemsList.getAdapter().getItem(mProblemsList.getCheckedItemPosition()));
                            problem.setIntensity(mIntensityBar.getProgress());
                            mListener.onPositiveBtnClick(problem);
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
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
