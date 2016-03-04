package com.aberezovskaya.etherapists.daos;


import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContract;

public class PhysicalProblem extends BaseEntity<PhysicalProblem> {

    private long mBodyProblemId = -1;
    private int mIntensity = -1;

    public PhysicalProblem(){super();};

    public PhysicalProblem(long id, long createDate, long modifyDate, long problemId, int intensity){
        super(id, createDate, modifyDate);
        mBodyProblemId = problemId;
        mIntensity = intensity;
    }

    @Override
    public PhysicalProblem fromCursor(Cursor c) {
        super.fromCursor(c);
        Long problemId = getLongValue(c, DataContract.PhysicalProblem.COLUMN_BODY_PROBLEM);
        Integer intensity = getIntValue(c, DataContract.PhysicalProblem.COLUMN_INTENSITY);
        mBodyProblemId = problemId != null ? problemId : -1;
        mIntensity = intensity != null ? intensity : -1;

        return getThis();
    }

    @Override
    public ContentValues toContentValues(ContentValues cv) {
         super.toContentValues(cv);
        cv.put(DataContract.PhysicalProblem.COLUMN_BODY_PROBLEM, mBodyProblemId);
        cv.put(DataContract.PhysicalProblem.COLUMN_INTENSITY, mIntensity);
        return cv;
    }

    public int getIntensity(){
        return mIntensity;
    }
}
