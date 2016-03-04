package com.aberezovskaya.etherapists.daos;


import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContract;

public class BodyProblem extends BaseEntity<BodyProblem> {

    private long mBodyPartId;
    private String mDescription;

    public BodyProblem() {

        super();
    }


    public BodyProblem(Long id, Long createDate, Long updateDate,
                                    Long bodyPart, String description) {

        super(id, createDate, updateDate);
        mBodyPartId = bodyPart;
        mDescription = description;
    }

    @Override
    public BodyProblem fromCursor(Cursor c) {

        super.fromCursor(c);


        Long id = getLongValue(c, DataContract.BodyProblem.COLUMN_BODY_PART);
        mBodyPartId = id != null ? id : -1;
        mDescription = getStringValue(c, DataContract.BodyProblem.COLUMN_DESCRIPTION);

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


    public BodyProblem setDescription(String description) {

        mDescription = description;
        return getThis();
    }


    @Override
    public ContentValues toContentValues(ContentValues cv) {
         super.toContentValues(cv);
            cv.put(DataContract.BodyProblem.COLUMN_BODY_PART, mBodyPartId);

        if (mDescription != null && !mDescription.isEmpty()){
            cv.put(DataContract.BodyProblem.COLUMN_DESCRIPTION, mDescription);
        }

        return cv;
    }
}
