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
import com.aberezovskaya.etherapists.adapters.PhysicalProblemsAdapter;
import com.aberezovskaya.etherapists.providers.DataContract;
import com.aberezovskaya.etherapists.utils.ObservableLoader;
import com.aberezovskaya.etherapists.utils.VerticalSpacingItemDecorator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * Fragment to manage currently selected physical problems
 */
public class PhysicalProblemsFragment extends BaseFragment {


    /**
     * consts
     */
    private static final String KEY_PH_PROBLEMS_LOADING_TASK = "physical_problem_task";

    /**
     * variables
     */
    private PhysicalProblemsAdapter mAdapter;
    private ObservableLoader<Cursor> mLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_physical_problems, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.rc_problems);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new VerticalSpacingItemDecorator(getActivity(), (int) getResources().getDimension(R.dimen.rc_item_spacing)));
        mAdapter = new PhysicalProblemsAdapter(getActivity());
        recycler.setAdapter(mAdapter);
        mLoader = new ObservableLoader<>(getLoadObservable(), mProblemsObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoader.getSubscription(KEY_PH_PROBLEMS_LOADING_TASK);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoader.unsubscribeLoaderTask(false);
    }

    private Observable<Cursor> getLoadObservable() {
        return Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                Cursor cursor = getContext().getContentResolver().query(DataContract.PhysicalProblem.JOIN_CONTENT_URI, null, null, null, null);
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
            mAdapter.swapCursor(aCursor);
        }
    };
}
