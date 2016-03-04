package com.aberezovskaya.etherapists.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.adapters.PhysicalProblemsAdapter;
import com.aberezovskaya.etherapists.providers.DataContract;
import com.aberezovskaya.etherapists.utils.VerticalSpacingItemDecorator;


/**
 * Fragment to manage currently selected physical problems
 */
public class PhysicalProblemsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{


    /**
     * loader id
     */
    private static final int ID_LOADER_PROBLEMS = 0;

    private RecyclerView mRecycler;
    private PhysicalProblemsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_physical_problems, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = (RecyclerView)view.findViewById(R.id.rc_problems);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        mRecycler.setLayoutManager(manager);
        mRecycler.addItemDecoration(new VerticalSpacingItemDecorator(getActivity(), (int)getResources().getDimension(R.dimen.rc_item_spacing)));
        mAdapter = new PhysicalProblemsAdapter(getActivity());
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ID_LOADER_PROBLEMS, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(), DataContract.PhysicalProblem.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        if (data != null) {
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
