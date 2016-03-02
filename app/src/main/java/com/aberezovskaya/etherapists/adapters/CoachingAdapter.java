package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;

public class CoachingAdapter extends BaseRecyclerCursorAdapter<CoachingAdapter.CoachingViewHolder> {

    CoachingAdapter(Context context){
        super(context);
    }



    @Override
    public CoachingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_rc_exercises, parent, false);
        return new CoachingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoachingViewHolder holder, int position) {

    }


     static class CoachingViewHolder extends RecyclerView.ViewHolder{

         AppCompatImageView mCircleView;
         ImageView mExerciseView;
         TextView mExerciseTitle;
         TextView mExerciseTime;

        public CoachingViewHolder(View itemView) {
            super(itemView);

            mCircleView = (AppCompatImageView)(itemView.findViewById(R.id.img_round_circle_exercise));
            mExerciseView = (ImageView)(itemView.findViewById(R.id.img_exercise));
            mExerciseTitle = (TextView)(itemView.findViewById(R.id.tv_title_exercise));
            mExerciseTime = (TextView)(itemView.findViewById(R.id.tv_time_exercise));
        }
    }
}
