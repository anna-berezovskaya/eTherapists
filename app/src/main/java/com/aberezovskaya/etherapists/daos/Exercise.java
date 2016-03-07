package com.aberezovskaya.etherapists.daos;

import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContract;

/**
 * daos object for the exercise field
 */
public class Exercise extends BaseEntity<Exercise> {

    private String mName = null;
    private String mImg = null;
    private int mDuration = -1;

    public Exercise() {
        super();
    }

    public Exercise(long id, long createDate, long modifyDate, String name, String pic, int duration) {
        super(id, createDate, modifyDate);
        mName = name;
        mImg = pic;
        mDuration = duration;
    }

    public String getName() {
        return mName;
    }

    public String getImg() {
        return mImg;
    }

    public int getDuration() {
        return mDuration;
    }

    @Override
    public Exercise fromCursor(Cursor c) {
        super.fromCursor(c);
        mName = getStringValue(c, DataContract.Exercise.COLUMN_TITLE);
        mImg = getStringValue(c, DataContract.Exercise.COLUMN_IMAGE);
        Integer duration = getIntValue(c, DataContract.Exercise.COLUMN_DURATION);
        mDuration = duration != null ? duration : -1;

        return getThis();
    }

    @Override
    public ContentValues toContentValues(ContentValues cv) {
        super.toContentValues(cv);
        cv.put(DataContract.Exercise.COLUMN_TITLE, mName);
        cv.put(DataContract.Exercise.COLUMN_IMAGE, mImg);
        cv.put(DataContract.Exercise.COLUMN_DURATION, mDuration);

        return cv;
    }
}
