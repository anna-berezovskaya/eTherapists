package com.aberezovskaya.etherapists.adapters;


import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aberezovskaya.etherapists.Config;
import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.daos.Exercise;
import com.squareup.picasso.Picasso;

import rx.Subscription;

/**
 * Adapter to display exercise item
 */
public class CoachingAdapter extends BaseRecyclerCursorAdapter<CoachingAdapter.CoachingViewHolder> {

    public CoachingAdapter(Context context) {
        super(context);
    }


    @Override
    public CoachingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_rc_exercises, parent, false);
        return new CoachingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoachingViewHolder holder, int position) {

        getCursor().moveToPosition(position);
        Exercise exercise = new Exercise().fromCursor(getCursor());

        holder.mCircleView.setImageResource(position % 2 == 0 ? R.drawable.green_circle : R.drawable.red_circle);
        String uriImage = Config.ASSETS_URI_EXERCISES + exercise.getImg();
        Picasso.with(getContext())
                .load(uriImage)
                .into(holder.mExerciseView);
        if (!TextUtils.isEmpty(exercise.getName())) {
            holder.mExerciseTitle.setText(exercise.getName());
        }

        if (exercise.getDuration() != -1) {
            holder.mDurationText.setText(String.valueOf(exercise.getDuration()) + " " + getContext().getString(R.string.min));
        }
    }


    static class CoachingViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mCircleView;
        ImageView mExerciseView;
        TextView mExerciseTitle;
        TextView mDurationText;

        public CoachingViewHolder(View itemView) {
            super(itemView);

            mCircleView = (AppCompatImageView) (itemView.findViewById(R.id.img_round_circle_exercise));
            mExerciseView = (ImageView) (itemView.findViewById(R.id.img_exercise));
            mExerciseTitle = (TextView) (itemView.findViewById(R.id.tv_title_exercise));
            mDurationText = (TextView) (itemView.findViewById(R.id.tv_time_exercise));
        }
    }
}
