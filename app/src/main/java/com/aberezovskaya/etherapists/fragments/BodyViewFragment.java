package com.aberezovskaya.etherapists.fragments;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aberezovskaya.etherapists.R;
import com.aberezovskaya.etherapists.daos.BaseEntity;
import com.aberezovskaya.etherapists.daos.PhysicalProblem;
import com.aberezovskaya.etherapists.dialogs.AddProblemDialogFragment;
import com.aberezovskaya.etherapists.providers.DataContract;
import com.aberezovskaya.etherapists.utils.ColorUtils;

import model.BodyPartEnum;

/**
 * Fragment to display the body view and
 * add new physical problems
 */
public class BodyViewFragment extends BaseFragment implements AddProblemDialogFragment.AddProblemDialogListener{

    private static final String CREATE_PHYSICAL_PROBLEM_DIALOG = "create_physical_problem_dialog";

    private ImageView mColorMap;
    private ImageView mImage;
    private BodyPartEnum mPartClicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mColorMap = (ImageView) view.findViewById(R.id.image_map);
        mImage = (ImageView) view.findViewById(R.id.image);
        mImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int evX = (int) event.getX();
                final int evY = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int tolerance = 25;
                        BodyPartEnum bodyPartClicked = BodyPartEnum.UNKNOWN;
                        int color = getClickPixelColor(evX, evY);
                        if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_head),color, tolerance)){
                            bodyPartClicked = BodyPartEnum.HEAD;
                        } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_neck),color, tolerance)){
                            bodyPartClicked = BodyPartEnum.NECK;
                        } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_back),color, tolerance)){
                            bodyPartClicked = BodyPartEnum.BACK;
                        } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_shoulder_left),color, tolerance)){
                            bodyPartClicked = BodyPartEnum.SHOULDER_LEFT;
                        } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_shoulder_right),color, tolerance)){
                            bodyPartClicked = BodyPartEnum.SHOULDER_RIGHT;
                        }

                        if (bodyPartClicked != BodyPartEnum.UNKNOWN){
                            showAddProblemDialog(bodyPartClicked);
                        }
                        return true;
                }
                return false;
            }
        });

    }

    public int getClickPixelColor(int x, int y) {
        mColorMap.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(mColorMap.getDrawingCache());
        mColorMap.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);

    }

    private void showAddProblemDialog(BodyPartEnum bodyPart) {
        AddProblemDialogFragment fragment = AddProblemDialogFragment.getInstance(bodyPart.getTag());
        fragment.setListener(this);
        fragment.show(getFragmentManager(), CREATE_PHYSICAL_PROBLEM_DIALOG);

    }

    @Override
    public void onNegativeBtnClick() {
    }

    @Override
    public void onPositiveBtnClick(PhysicalProblem problem) {
        ContentValues cv = new ContentValues();
        BaseEntity.prepareForInsert(problem.toContentValues(cv));
        getActivity().getContentResolver().insert(DataContract.PhysicalProblem.CONTENT_URI, cv);
    }
}
