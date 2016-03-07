package com.aberezovskaya.etherapists.fragments;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.aberezovskaya.etherapists.model.BodyParts;

/**
 * Fragment to display the body view and
 * add new physical problems
 * <p/>
 * the  "map" view approach used
 * to acchieve the clickable image.
 * The approache works good on the small images,
 * which we don't have to change the color on click
 * <p/>
 * the custom view may be developed for high quality big
 * images with scaling and touch response, what will
 * require more time.
 * <p/>
 * current approach is not suitable for large images
 */
public class BodyViewFragment extends BaseFragment implements AddProblemDialogFragment.AddProblemDialogListener {

    /**
     * consts
     */
    // class name for logging
    private static final String CREATE_PHYSICAL_PROBLEM_DIALOG = "create_physical_problem_dialog";

    // tolerance level to match the colors
    private static final int COLOR_TOLERANCE = 25;

    /**
     * variables
     */
    private ImageView mColorMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mColorMap = (ImageView) view.findViewById(R.id.image_map);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int evX = (int) event.getX();
                final int evY = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        BodyParts bodyPartClicked = BodyParts.UNKNOWN;
                        int color = getClickPixelColor(evX, evY);
                        if (color != -1) {
                            // match the color of the pixel
                            if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_head), color, COLOR_TOLERANCE)) {
                                bodyPartClicked = BodyParts.HEAD;
                            } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_neck), color, COLOR_TOLERANCE)) {
                                bodyPartClicked = BodyParts.NECK;
                            } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_back), color, COLOR_TOLERANCE)) {
                                bodyPartClicked = BodyParts.BACK;
                            } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_shoulder_left), color, COLOR_TOLERANCE)) {
                                bodyPartClicked = BodyParts.SHOULDER_LEFT;
                            } else if (ColorUtils.closeMatch(ContextCompat.getColor(getContext(), R.color.color_shoulder_right), color, COLOR_TOLERANCE)) {
                                bodyPartClicked = BodyParts.SHOULDER_RIGHT;
                            }

                            if (bodyPartClicked != BodyParts.UNKNOWN) {
                                showAddProblemDialog(bodyPartClicked);
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    // get the color of the invisible "map" image region
    // uses "inverting" the touch coordinates into
    // the bitmap coordinates
    public int getClickPixelColor(int x, int y) {
        Drawable imgDrawable = mColorMap.getDrawable();

        Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();
        Matrix invertMatrix = new Matrix();
        mColorMap.getImageMatrix().invert(invertMatrix);
        float[] inverted = new float[]{x, y};
        invertMatrix.mapPoints(inverted);

        int xInv = (int) inverted[0];
        int yInv = (int) inverted[1];

        if (xInv > bitmap.getWidth() - 1 || xInv < 0 || yInv < 0 || yInv > bitmap.getHeight() - 1) {
            return -1;
        }
        return bitmap.getPixel(xInv, yInv);
    }

    private void showAddProblemDialog(BodyParts bodyPart) {
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
