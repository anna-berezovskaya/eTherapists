package com.aberezovskaya.etherapists.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.adapters.CoachingAdapter;
import com.aberezovskaya.etherapists.providers.DataContract;
import com.aberezovskaya.etherapists.utils.ObservableLoader;
import com.aberezovskaya.etherapists.utils.VerticalSpacingItemDecorator;

import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Fragment to display daily exercises
 */
public class CoachingFragment extends BaseFragment {

    /**
     * consts
     */

    // constant to define exercises per day amount
    private static final int EXERCISES_PER_DAY = 5;

    // constant for caching the load task subscription
    private static final String KEY_LOAD_TASK = "load_task";

    /**
     * variables
     */
    private ObservableLoader<Cursor> mLoader;
    private CoachingAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coaching, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rc = (RecyclerView) view.findViewById(R.id.rc_exercises);
        mAdapter = new CoachingAdapter(getActivity());
        rc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rc.setAdapter(mAdapter);
        rc.addItemDecoration(new VerticalSpacingItemDecorator(getActivity(), (int) getResources().getDimension(R.dimen.rc_item_spacing)));
        mLoader = new ObservableLoader<>(getLoadObservable(), mExercisesLoadingObesriver);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoader.getSubscription(KEY_LOAD_TASK);
    }


    /*
    ORDER BY RAND() - has really bad performance on
    a big tables.
    That's why another approach was used. The table of
    exercises is potentially big
     */

    protected Observable<Cursor> getLoadObservable() {
        return Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                // retirieve the trainings suitable for
                // show in accordance with the user's body problems
                // see  DataContentProbvider SQL_DAILY_TRAININGS
                Cursor trainingsCursor = getContext().getContentResolver().query(DataContract.Training.DAILY_TRAININGS_CONTENT_URI,
                        null, null, null, null);

                if (trainingsCursor != null) {
                    int count = trainingsCursor.getCount();

                    if (count > 0) {

                        // retrieve max 5 exercises
                        // from the exercise table
                        // which id
                        // contains in the
                        // trainings returned by the previous query

                        int exLimit = Math.min(count, EXERCISES_PER_DAY);
                        StringBuilder selection = new StringBuilder();
                        selection.append(DataContract.Exercise.COLUMN_ID);
                        selection.append(" IN (");
                        String[] selectionArgs = new String[exLimit];

                        // The seed of the random may be used
                        // to show the same exercises all the day
                        // the number of day in a year should be used
                        Random rand = new Random(System.currentTimeMillis());

                        for (int i = 0; i < exLimit; i++) {
                            int position = rand.nextInt(count);
                            trainingsCursor.moveToPosition(position);
                            selectionArgs[i] = String.valueOf(trainingsCursor.getInt(trainingsCursor.getColumnIndex(DataContract.Training.COLUMN_EXERCISE_ID)));
                            selection.append("?");
                            if ((i + 1) != exLimit) {
                                selection.append(",");
                            }
                        }
                        selection.append(")");


                        Cursor exercises = getContext().getContentResolver().query(DataContract.Exercise.CONTENT_URI, null, selection.toString(), selectionArgs, null);
                        if (exercises != null) {
                            subscriber.onNext(exercises);
                            subscriber.onCompleted();
                        }
                        {
                            subscriber.onError(new SQLiteException());
                        }
                    }
                    trainingsCursor.close();
                }
            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoader.unsubscribeLoaderTask(false);
    }


    private Observer<Cursor> mExercisesLoadingObesriver = new Observer<Cursor>() {

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

            mAdapter.swapCursor(aCursor);
        }
    };

}
