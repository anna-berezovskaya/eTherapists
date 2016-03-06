package com.aberezovskaya.etherapists.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

public class ObservableCache {

	/*
	 * variables
	 */
	private Map<String, Observable<?>> mCache = new ConcurrentHashMap<String, Observable<?>>();

	public ObservableCache() {

	}

	public <T> Subscription subscribeTo(String key, Observer<T> subscriber,
										boolean keepAfterCompleted) {

		return subscribeTo(key, null, subscriber, keepAfterCompleted);
	}

	public <T> Subscription subscribeTo(final String key,
										final Func0<Observable<T>> observableProvider,
										final Observer<T> observer,
										final boolean keepAfterCompleted) {

		Observable<T> observable;

		if( (null == (observable = (Observable<T>)mCache.get(key))) &&
				(null != observableProvider) ) {

			observable = observableProvider.call().cache();
			mCache.put(key, observable);

			if(!keepAfterCompleted) {

				observable.subscribe(new Observer<Object>() {

					@Override
					public void onCompleted() {
						removeObservable(key);
					}

					@Override
					public void onError(Throwable e) {
						removeObservable(key);
					}

					@Override
					public void onNext(Object o) {

					}
				});
			}

			if(null == observer) {

				Subscription s = observable.subscribe();
				s.unsubscribe();
			}
		}

		return (null != observable && null != observer ? observable.subscribe(observer) : null);
	}

	public boolean hasObservable(String key) {

		return mCache.containsKey(key);
	}

	public boolean removeObservable(String key) {

		return (null != mCache.remove(key));
	}

}