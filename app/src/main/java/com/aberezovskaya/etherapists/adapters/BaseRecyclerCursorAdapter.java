package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;


public abstract class BaseRecyclerCursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter< VH>{

    private Cursor mCursor;
    private Context mContext;

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
        if (mCursor != null && !mCursor.isClosed()) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    protected Context getContext(){
        return mContext;
    }

    protected Cursor getCursor(){
        return mCursor;
    }
}
