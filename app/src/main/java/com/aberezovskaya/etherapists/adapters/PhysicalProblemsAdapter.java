package com.aberezovskaya.etherapists.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class PhysicalProblemsAdapter extends BaseRecyclerCursorAdapter<PhysicalProblemsAdapter.PhysicalProblemsViewHolder> {

    public PhysicalProblemsAdapter(Context context) {
        super(context);
    }

    @Override
    public PhysicalProblemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PhysicalProblemsViewHolder holder, int position) {

    }

    static class PhysicalProblemsViewHolder extends RecyclerView.ViewHolder{

        public PhysicalProblemsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
