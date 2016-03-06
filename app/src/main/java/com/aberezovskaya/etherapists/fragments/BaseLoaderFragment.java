package com.aberezovskaya.etherapists.fragments;


import com.aberezovskaya.etherapists.App;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

public abstract class BaseLoaderFragment<T> extends BaseFragment{

    private String mTaskKey;
    protected Subscription mLoaderSubscription = null;

    @Override
    public void onResume() {
        super.onResume();
    }

    protected Subscription getSubscription(String key){
        mTaskKey = key;
        return App.instance().getObservablesCache().subscribeTo(mTaskKey,
                new Func0<Observable<T>>() {

                    @Override
                    public Observable<T> call() {
                        return getLoadObservable();
                    }
                }, getObserver(), true);
    }

    protected abstract Observable<T> getLoadObservable();

    protected abstract Observer<T> getObserver();

    protected void unsubscribeLoaderTask(boolean removeObservable) {

        if (removeObservable) {
            App.instance().getObservablesCache().removeObservable(mTaskKey);
        }

        if (null != mLoaderSubscription) {
            mLoaderSubscription.unsubscribe();
            mLoaderSubscription = null;
        }
    }

}
