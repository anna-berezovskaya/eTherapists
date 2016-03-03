package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

public class BodyProblemDialogCursorAdapter extends BaseRecyclerCursorAdapter<BodyProblemDialogCursorAdapter.BodyProblemDialogViewHolder> {


    public BodyProblemDialogCursorAdapter(Context context) {

        super(context);
    }

    @Override
    public BodyProblemDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_checked, parent, false);
        return new BodyProblemDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BodyProblemDialogViewHolder holder, int position) {

    }

    static class BodyProblemDialogViewHolder extends RecyclerView.ViewHolder{

        private CheckedTextView mCheckedTextView;

        public BodyProblemDialogViewHolder(View itemView) {
            super(itemView);
            mCheckedTextView = (CheckedTextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
