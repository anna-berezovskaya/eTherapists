package com.aberezovskaya.etherapists.utils;

import com.aberezovskaya.etherapists.App;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

/**
 * Loader class based on rx.Observable
 * with the caching
 */
public class ObservableLoader<T> {

    private Subscription mLoaderSubscription = null;
    private String mTaskKey;
    private Observer<T> mObserver;
    private Observable<T> mObservable;


    public ObservableLoader(Observable<T> observable, Observer<T> observer) {
        mObservable = observable;
        mObserver = observer;
    }

    public void getSubscription(String key) {
        mTaskKey = key;
        mLoaderSubscription = App.instance().getObservablesCache().subscribeTo(mTaskKey,
                new Func0<Observable<T>>() {

                    @Override
                    public Observable<T> call() {
                        return mObservable;
                    }
                }, mObserver, true);
    }

    public void unsubscribeLoaderTask(boolean removeObservable) {

        if (removeObservable) {
            App.instance().getObservablesCache().removeObservable(mTaskKey);
        }

        if (null != mLoaderSubscription) {
            mLoaderSubscription.unsubscribe();
            mLoaderSubscription = null;
        }
    }
}
