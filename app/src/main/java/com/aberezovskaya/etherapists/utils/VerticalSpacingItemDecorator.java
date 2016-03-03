package com.aberezovskaya.etherapists.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private Context mContext;
    private final int mVerticalSpaceHeight;

        public VerticalSpacingItemDecorator(Context context, int mVerticalSpaceHeight) {
            mContext = context;
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int px = ScreenUtils.dipToPix(mContext, mVerticalSpaceHeight);
            outRect.bottom = px;
        }
    }

