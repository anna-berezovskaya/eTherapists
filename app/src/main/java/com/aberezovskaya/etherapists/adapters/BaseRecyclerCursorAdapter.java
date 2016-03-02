package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;


public abstract class BaseRecyclerCursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter< VH>{

    protected Cursor mCursor;
    protected Context mContext;

    public BaseRecyclerCursorAdapter(Context context){
        mContext = context;
    }

    public Cursor swapCursor(Cursor cursor){
        if (mCursor != null && !mCursor.isClosed()){
            notifyItemRangeRemoved(0, mCursor.getCount());
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}