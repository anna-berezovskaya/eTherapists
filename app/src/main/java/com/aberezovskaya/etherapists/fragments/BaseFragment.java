package com.aberezovskaya.etherapists.fragments;


import android.database.Cursor;
import android.support.v4.app.Fragment;

import com.aberezovskaya.etherapists.App;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func0;

/**
 * Base Fragment
 */
public class BaseFragment extends Fragment {


    /**
     * Return the "target" for this fragment of specified type. By default target is activity that owns
     * current fragment but also could be any fragment.
     * @param clazz requested callback interface
     * @return requested callback or null if no callback of requested type is found
     */
    protected <T> T getTarget(Class<T> clazz) {

        Object[] targets = new Object[]{getTargetFragment(), getParentFragment(), getActivity()};

        for(Object target : targets) {

            if( (null != target) && (clazz.isInstance(target)) ) {

                return clazz.cast(target);
            }
        }

        return null;
    }
}
