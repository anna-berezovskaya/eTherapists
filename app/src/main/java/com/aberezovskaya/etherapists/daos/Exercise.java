package com.aberezovskaya.etherapists.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.aberezovskaya.etherapists.providers.DataContract;


public class Exercise extends BaseEntity<Exercise> {

    private String mName = null;
    private String mPic = null;
    private int mDuration = -1;

    Exercise(){super();}
    Exercise(long id, long createDate, long modifyDate, String name, String pic, int duration){
        super(id, createDate, modifyDate);
        mName = name;
        mPic = pic;
    }

    @Override
    public Exercise fromCursor(Cursor c) {
         super.fromCursor(c);
        mName = getStringValue(c, DataContract.Exercise.COLUMN_TITLE);
        mPic = getStringValue(c, DataContract.Exercise.COLUMN_IMAGE);
        Integer duration = getIntValue(c, DataContract.Exercise.COLUMN_DURATION);
        mDuration = duration != null ? duration : -1;

        return getThis();
    }

    @Override
    public ContentValues toContentValues(ContentValues cv) {
         super.toContentValues(cv);
        cv.put(DataContract.Exercise.COLUMN_TITLE, mName);
        cv.put(DataContract.Exercise.COLUMN_IMAGE, mPic);
        cv.put(DataContract.Exercise.COLUMN_DURATION, mDuration);

        return cv;
    }
}
