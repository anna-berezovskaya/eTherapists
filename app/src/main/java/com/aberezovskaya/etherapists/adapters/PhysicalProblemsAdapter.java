package com.aberezovskaya.etherapists.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.daos.BodyProblem;


public class PhysicalProblemsAdapter extends BaseRecyclerCursorAdapter<PhysicalProblemsAdapter.PhysicalProblemsViewHolder> {

    public PhysicalProblemsAdapter(Context context) {
        super(context);
    }

    @Override
    public PhysicalProblemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_rc_problem, parent, false);
        return new PhysicalProblemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhysicalProblemsViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        BodyProblem problem = new BodyProblem().fromCursor(mCursor);
        if (!TextUtils.isEmpty(problem.getBodyPart())){
            holder.mProblemTitle.setText(problem.getBodyPart());
        }

        if (!TextUtils.isEmpty(problem.getDescription())){
            holder.mProblemDescription.setText(problem.getDescription());
        }

        holder.mProblemIntensity.setProgress(problem.getIntesity());
        switch(position % 3){
            case 0:
                holder.mProblemIntensity.setBackgroundColor(ContextCompat.getColor(mContext, R.color.seek_pane_color_green));
                break;
            case 1:
                holder.mProblemIntensity.setBackgroundColor(ContextCompat.getColor(mContext, R.color.seek_pane_color_red));
                break;
            case 2:
                holder.mProblemIntensity.setBackgroundColor(ContextCompat.getColor(mContext, R.color.seek_pane_color_blue));
                break;
        }
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
