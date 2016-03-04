package com.aberezovskaya.etherapists.daos;


import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContract;

public class BodyProblem extends BaseEntity<BodyProblem> {

    private long mBodyPartId;
    private String mDescription;
    private int mIntensity = 0;

    public BodyProblem() {

        super();
    }


    public BodyProblem(Long id, Long createDate, Long updateDate,
                                    Long bodyPart, String description, int intensity) {

        super(id, createDate, updateDate);
        mBodyPartId = bodyPart;
        mDescription = description;
        mIntensity = intensity;
    }

    @Override
    public BodyProblem fromCursor(Cursor c) {

        super.fromCursor(c);


        Long id = getLongValue(c, DataContract.BodyProblem.COLUMN_BODY_PART);
        mBodyPartId = id != null ? id : -1;
        mDescription = getStringValue(c, DataContract.BodyProblem.COLUMN_DESCRIPTION);
        Integer intensity = getIntValue(c, DataContract.BodyProblem.COLUMN_INTENSITY) ;
        mIntensity = intensity != null ? intensity : 0;

        return getThis();
    }

    public BodyProblem setBodyPart(Long bodyPart) {
        mBodyPartId = bodyPart;
        return getThis();
    }

    public Long getBodyPart(){
        return mBodyPartId;
    }

    public String getDescription(){
        return mDescription;
    }

    public int getIntesity(){
        return mIntensity;
    }

    public BodyProblem setDescription(String description) {

        mDescription = description;
        return getThis();
    }

    public BodyProblem setIntensity(int intensity) {

        mIntensity = intensity;

        return getThis();
    }


    @Override
    public ContentValues toContentValues(ContentValues cv) {
         super.toContentValues(cv);
            cv.put(DataContract.BodyProblem.COLUMN_BODY_PART, mBodyPartId);

        if (mDescription != null && !mDescription.isEmpty()){
            cv.put(DataContract.BodyProblem.COLUMN_DESCRIPTION, mDescription);
        }

        cv.put(DataContract.BodyProblem.COLUMN_INTENSITY, mIntensity);

        return cv;
    }
}
