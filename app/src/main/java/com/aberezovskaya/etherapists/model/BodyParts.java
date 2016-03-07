package com.aberezovskaya.etherapists.model;


import com.aberezovskaya.etherapists.App;
import com.aberezovskaya.etherapists.R;

/**
 * the enum
 * to declare body parts
 * available to click
 * and store in database
 */
public enum BodyParts {
    HEAD(App.instance().getString(R.string.head)),
    NECK(App.instance().getString(R.string.neck)),
    SHOULDER_RIGHT(App.instance().getString(R.string.right_shoulder)),
    SHOULDER_LEFT(App.instance().getString(R.string.left_shoulder)),
    BACK(App.instance().getString(R.string.back)),
    UNKNOWN(App.instance().getString(R.string.unknown));

    private String mTag;

    BodyParts(String tag) {

        mTag = tag;
    }

    public String getTag() {
        return mTag;
    }


    public static BodyParts getBPByTag(String tag) {
        for (BodyParts part : BodyParts.values()) {
            if (part.mTag.equals(tag)) {
                return part;
            }
        }

        return UNKNOWN;
    }

}
