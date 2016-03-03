package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class BodyProblemDialogCursorAdapter extends BaseRecyclerCursorAdapter<BodyProblemDialogCursorAdapter.BodyProblemDialogViewHolder> {

    public BodyProblemDialogCursorAdapter(Context context) {
        super(context);
    }

    @Override
    public BodyProblemDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BodyProblemDialogViewHolder holder, int position) {

    }

    static class BodyProblemDialogViewHolder extends RecyclerView.ViewHolder{

        public BodyProblemDialogViewHolder(View itemView) {
            super(itemView);
        }
    }
}
