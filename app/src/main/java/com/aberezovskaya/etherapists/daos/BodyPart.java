package com.aberezovskaya.etherapists.daos;


import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContract;

import com.aberezovskaya.etherapists.model.BodyPartEnum;

public class BodyPart extends BaseEntity<BodyPart> {

    private BodyPartEnum mBodyPart = null;

    public BodyPart() {
        super();
    }

    public BodyPart(Long id, Long createDate, Long modifyDate, BodyPartEnum bp) {
        super(id, createDate, modifyDate);
        this.mBodyPart = bp;
    }

    public BodyPart(Long id, Long createDate, Long modifyDate, String bp) {
        super(id, createDate, modifyDate);
        this.mBodyPart = BodyPartEnum.getBPByTag(bp);

    }

    @Override
    public BodyPart fromCursor(Cursor c) {
        super.fromCursor(c);
        mBodyPart = BodyPartEnum.getBPByTag(getStringValue(c, DataContract.BodyPart.COLUMN_NAME));

        return getThis();
    }

    @Override
    public ContentValues toContentValues(ContentValues cv) {
         super.toContentValues(cv);
        cv.put(DataContract.BodyPart.COLUMN_NAME, mBodyPart.getTag());
        return cv;
    }
}
