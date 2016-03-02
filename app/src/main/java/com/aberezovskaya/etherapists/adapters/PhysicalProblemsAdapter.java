package com.aberezovskaya.etherapists.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;


public class PhysicalProblemsAdapter extends BaseRecyclerCursorAdapter<PhysicalProblemsAdapter.PhysicalProblemsViewHolder> {

    public PhysicalProblemsAdapter(Context context) {
        super(context);
    }

    @Override
    public PhysicalProblemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_rc_exercises, parent, false);
        return new PhysicalProblemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhysicalProblemsViewHolder holder, int position) {

    }

    static class PhysicalProblemsViewHolder extends RecyclerView.ViewHolder{

        private TextView mProblemTitle;
        private TextView mProblemDescription;
        private AppCompatSeekBar mProblemIntensity;

        public PhysicalProblemsViewHolder(View itemView) {
            super(itemView);

            mProblemTitle = (TextView) itemView.findViewById(R.id.tv_problem_title);
            mProblemDescription = (TextView) itemView.findViewById(R.id.tv_problem_description);
            mProblemIntensity = (AppCompatSeekBar) itemView.findViewById(R.id.seek_intensity);
        }
    }
}
