package com.aberezovskaya.etherapists.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.daos.BodyPart;
import com.aberezovskaya.etherapists.daos.BodyProblem;
import com.aberezovskaya.etherapists.daos.PhysicalProblem;
import com.aberezovskaya.etherapists.providers.DataContract;


public class PhysicalProblemsAdapter extends BaseRecyclerCursorAdapter<PhysicalProblemsAdapter.PhysicalProblemsViewHolder> {

    public PhysicalProblemsAdapter(Context context) {
        super(context);
    }

    @Override
    public PhysicalProblemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_rc_problem, parent, false);
        return new PhysicalProblemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhysicalProblemsViewHolder holder, int position) {
        getCursor().moveToPosition(position);
        PhysicalProblem problem = new PhysicalProblem().fromCursor(getCursor());
        String bpName = getCursor().getString(getCursor().getColumnIndex(DataContract.BodyPart.COLUMN_NAME));
        String bodyProblemName = getCursor().getString(getCursor().getColumnIndex(DataContract.BodyProblem.COLUMN_DESCRIPTION));
        if (!TextUtils.isEmpty(bpName)) {
            holder.mProblemTitle.setText(bpName);
        }

        if (!TextUtils.isEmpty(bodyProblemName)){
            holder.mProblemDescription.setText(bodyProblemName);
        }

        holder.mProblemIntensity.setProgress(problem.getIntensity() != -1 ? problem.getIntensity() : 0);

        switch(position % 3){
            case 0:
                holder.mProblemIntensity.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seek_pane_color_green));
                break;
            case 1:
                holder.mProblemIntensity.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seek_pane_color_red));
                break;
            case 2:
                holder.mProblemIntensity.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seek_pane_color_blue));
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

            // make it not respond to touch events without change the stle to not active
            mProblemIntensity.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }
}
