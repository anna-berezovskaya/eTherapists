package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.providers.DataContract;

public class BodyProblemDialogCursorAdapter extends CursorAdapter {


    public BodyProblemDialogCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dlg_problems, parent, false);
        BodyProblemDialogViewHolder holder = new BodyProblemDialogViewHolder(v);
        v.setTag(holder);
        return v;
    }

    // will return an Id of the bodyProblem selected
    @Override
    public Object getItem(int position) {

        // Local variables have better performance for Android rather than method call for
        // several times.
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        return cursor.getInt((cursor.getColumnIndex(DataContract.BodyProblem.COLUMN_ID)));
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        BodyProblemDialogViewHolder holder = (BodyProblemDialogViewHolder) view.getTag();
        holder.mCheckedTextView.setText(cursor.getString(cursor.getColumnIndex(DataContract.BodyProblem.COLUMN_DESCRIPTION)));

    }

    static class BodyProblemDialogViewHolder {

        private CheckedTextView mCheckedTextView;

        public BodyProblemDialogViewHolder(View itemView) {
            mCheckedTextView = (CheckedTextView) itemView.findViewById(R.id.problem);
        }
    }
}
