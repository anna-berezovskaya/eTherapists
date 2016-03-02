package com.aberezovskaya.etherapists.daos;


import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContentProvider;
import com.aberezovskaya.etherapists.providers.DataContract;

public abstract class BaseEntity<T extends BaseEntity<T>> {

    private Long mId;
    private Long mCreateDate;
    private Long mModifyDate;

    public BaseEntity() {

        this(null, null, null);
    }

    public BaseEntity(Long id, Long createDate, Long updateDate) {

        setId(id);
    }

    public T fromCursor(Cursor c) {

        mId = getLongValue(c, DataContract.BaseEntityColumns.COLUMN_ID);
        mCreateDate = getLongValue(c, DataContract.BaseEntityColumns.COLUMN_CREATE_DATE);
        mModifyDate = getLongValue(c, DataContract.BaseEntityColumns.COLUMN_MODIFY_DATE);

        return getThis();
    }

    public Long getId() {

        return mId;
    }

    public T setId(Long id) {

        mId = id;
        return getThis();
    }

    public Long getCreateDate() {

        return mCreateDate;
    }

    public T setCreateDate(Long createDate) {

       mCreateDate = createDate;
        return getThis();
    }

    public Long getModifyDate() {

        return mModifyDate;
    }

    public T setModifyDate(Long modifyDate) {

        mModifyDate = modifyDate;
        return getThis();
    }

    /**
     *
     * @param cv
     * @return
     */
    public static ContentValues prepareForUpdate(ContentValues cv, boolean forceSettUpdateDate) {

        cv.remove(DataContract.BaseEntityColumns.COLUMN_CREATE_DATE);

        if((true == forceSettUpdateDate) || (!cv.containsKey(DataContract.BaseEntityColumns.COLUMN_MODIFY_DATE)) ){

            cv.put(DataContract.BaseEntityColumns.COLUMN_MODIFY_DATE, System.currentTimeMillis());
        }

        return cv;
    }

    /**
     *
     * @param cv
     * @return
     */
    public static ContentValues prepareForInsert(ContentValues cv) {

        return prepareForInsert(cv, true);
    }

    /**
     *
     * @param cv
     * @return
     */
    public static ContentValues prepareForInsert(ContentValues cv, boolean forceSetCreateDate) {

        long currTime = System.currentTimeMillis();
        long createDate = (true == forceSetCreateDate) ? currTime :
                cv.containsKey(DataContract.BaseEntityColumns.COLUMN_CREATE_DATE) ? cv.getAsLong(DataContract.BaseEntityColumns.COLUMN_CREATE_DATE) : currTime;

        cv.put(DataContract.BaseEntityColumns.COLUMN_CREATE_DATE, createDate);
        cv.put(DataContract.BaseEntityColumns.COLUMN_MODIFY_DATE, createDate);

        return cv;
    }

    /**
     *
     * @param cv
     */
    public ContentValues toContentValues(ContentValues cv) {

        cv.put(DataContract.BaseEntityColumns.COLUMN_ID, mId);
        cv.put(DataContract.BaseEntityColumns.COLUMN_CREATE_DATE, mCreateDate);
        cv.put(DataContract.BaseEntityColumns.COLUMN_MODIFY_DATE, mModifyDate);

        return cv;
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected T getThis() {

        return (T) this;
    }

    protected static Long getLongValue( Cursor c, String columnName) {

        int columnIndex = c.getColumnIndex(columnName);

        if(columnIndex >= 0) {

            return c.getLong(columnIndex);
        }
        else {

            return null;
        }
    }

    protected static Integer getIntValue( Cursor c, String columnName) {

        int columnIndex = c.getColumnIndex(columnName);

        if(columnIndex >= 0) {

            return c.getInt(columnIndex);
        }
        else {

            return null;
        }
    }

    protected static String getStringValue( Cursor c, String columnName){
        int columnIndex = c.getColumnIndex(columnName);
        if (columnIndex >= 0){
            return c.getString(columnIndex);
        } else {
            return null;
        }
    }
}