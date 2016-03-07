package com.aberezovskaya.etherapists.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aberezovskaya.etherapists.App;
import com.aberezovskaya.etherapists.Config;
import com.aberezovskaya.etherapists.daos.BaseEntity;
import com.aberezovskaya.etherapists.daos.BodyPart;
import com.aberezovskaya.etherapists.daos.BodyProblem;
import com.aberezovskaya.etherapists.daos.Exercise;
import com.aberezovskaya.etherapists.daos.PhysicalProblem;
import com.aberezovskaya.etherapists.daos.Training;

/**
 * SQLiteOpenHelper class to manage
 * database creation
 * and opening.
 * <p/>
 * For the test project, the database is not
 * very necessary, cause we can use
 * a small amount of static test data
 * <p/>
 * But for the real project such kind of database,
 * containing exercises for different physical problems,
 * and, possibly, some another info in future,
 * would be useful
 */
public class DataSQLiteOpenHelper extends SQLiteOpenHelper {

    /*
     * consts
	 */
    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "eTherapists_data.db";

    private DataSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /*
     * Holder class, for implement
       * lazy-loaded singleton design pattern
     */
    private static class LazyHolder {
        private static final DataSQLiteOpenHelper sInstance = new DataSQLiteOpenHelper(App.instance().getApplicationContext());
    }


    /**
     * automatically thread-safe
     *
     * @return helper instance
     */
    public static DataSQLiteOpenHelper instance() {
        return LazyHolder.sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DataContract.Tables.BODY_PROBLEM + " ( " +
                DataContract.BodyProblem.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.BodyProblem.COLUMN_CREATE_DATE + " INTEGER NOT NULL, " +
                DataContract.BodyProblem.COLUMN_MODIFY_DATE + " INTEGER NOT NULL, " +
                DataContract.BodyProblem.COLUMN_BODY_PART + " INTEGER NOT NULL, " +
                DataContract.BodyProblem.COLUMN_DESCRIPTION + " TEXT" +
                ");");

        db.execSQL("CREATE TABLE " + DataContract.Tables.BODY_PART + " ( " +
                DataContract.BodyPart.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.BodyPart.COLUMN_CREATE_DATE + " INTEGER NOT NULL, " +
                DataContract.BodyPart.COLUMN_MODIFY_DATE + " INTEGER NOT NULL, " +
                DataContract.BodyPart.COLUMN_NAME + " TEXT NOT NULL" +
                ");");

        db.execSQL("CREATE TABLE " + DataContract.Tables.PHYSICAL_PROBLEM + " ( " +
                DataContract.PhysicalProblem.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.PhysicalProblem.COLUMN_CREATE_DATE + " INTEGER NOT NULL, " +
                DataContract.PhysicalProblem.COLUMN_MODIFY_DATE + " INTEGER NOT NULL, " +
                DataContract.PhysicalProblem.COLUMN_BODY_PROBLEM + " INTEGER NOT NULL, " +
                DataContract.PhysicalProblem.COLUMN_INTENSITY + " INTEGER " +
                ");");

        db.execSQL("CREATE TABLE " + DataContract.Tables.EXERCISE + " ( " +
                DataContract.Exercise.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.Exercise.COLUMN_CREATE_DATE + " INTEGER NOT NULL, " +
                DataContract.Exercise.COLUMN_MODIFY_DATE + " INTEGER NOT NULL, " +
                DataContract.Exercise.COLUMN_TITLE + " TEXT NOT NULL, " +
                DataContract.Exercise.COLUMN_IMAGE + " TEXT NOT NULL, " +
                DataContract.Exercise.COLUMN_DURATION + " INTEGER" +
                ");");

        db.execSQL("CREATE TABLE " + DataContract.Tables.TRAINING + " ( " +
                DataContract.Training.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.Training.COLUMN_CREATE_DATE + " INTEGER NOT NULL, " +
                DataContract.Training.COLUMN_MODIFY_DATE + " INTEGER NOT NULL, " +
                DataContract.Training.COLUMN_EXERCISE_ID + " INTEGER NOT NULL, " +
                DataContract.Training.COLUMN_PROBLEM_ID + " INTEGER NOT NULL" +
                ");");


        ContentValues cv = new ContentValues();
        for (BodyProblem problem : Config.PREDEFINED_BODY_PROBLEM) {

            cv.clear();
            BaseEntity.prepareForInsert(problem.toContentValues(cv));
            db.insert(DataContract.Tables.BODY_PROBLEM, null, cv);
        }

        for (BodyPart part : Config.PARTS_OF_BODY_LIST) {
            cv.clear();
            BaseEntity.prepareForInsert(part.toContentValues(cv));
            db.insert(DataContract.Tables.BODY_PART, null, cv);
        }

        for (Training training : Config.PREDEFINED_TRAININGS) {
            cv.clear();
            BaseEntity.prepareForInsert(training.toContentValues(cv));
            db.insert(DataContract.Tables.TRAINING, null, cv);
        }

        for (Exercise exercise : Config.PREDEFINED_EXERCISES) {
            cv.clear();
            BaseEntity.prepareForInsert(exercise.toContentValues(cv));
            db.insert(DataContract.Tables.EXERCISE, null, cv);
        }
        for (PhysicalProblem problem : Config.PREDEFINED_PHYSICAL_PROBLEMS) {
            cv.clear();
            BaseEntity.prepareForInsert(problem.toContentValues(cv));
            db.insert(DataContract.Tables.PHYSICAL_PROBLEM, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // the lazy way of upgrade
        // though, suitable for our
        // situation
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.Tables.BODY_PROBLEM);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.Tables.EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.Tables.TRAINING);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.Tables.BODY_PART);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.Tables.PHYSICAL_PROBLEM);

        onCreate(db);
    }
}
