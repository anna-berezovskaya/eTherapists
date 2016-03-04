package com.aberezovskaya.etherapists.daos;


import android.content.ContentValues;
import android.database.Cursor;

import com.aberezovskaya.etherapists.providers.DataContract;

public class Training extends BaseEntity<Training> {

    private long mProblemId = -1;
    private long mExerciseId = -1;

    public Training(){super();}

    public Training(long id, long createDate, long modifyDate, long problemId, long exerciseid){
        super(id, createDate, modifyDate);
        mProblemId = problemId;
        mExerciseId = exerciseid;
    }

    @Override
    public Training fromCursor(Cursor c) {
         super.fromCursor(c);
        Long problemId = getLongValue(c, DataContract.Training.COLUMN_PROBLEM_ID);
        Long exerciseId = getLongValue(c, DataContract.Training.COLUMN_EXERCISE_ID);

        mProblemId = problemId != null ? problemId : -1;
        mExerciseId = exerciseId != null ? exerciseId : -1;

        return getThis();
    }

    @Override
    public ContentValues toContentValues(ContentValues cv) {
         super.toContentValues(cv);
        cv.put(DataContract.Training.COLUMN_PROBLEM_ID, mProblemId);
        cv.put(DataContract.Training.COLUMN_EXERCISE_ID, mExerciseId);
        return cv;
    }
}
