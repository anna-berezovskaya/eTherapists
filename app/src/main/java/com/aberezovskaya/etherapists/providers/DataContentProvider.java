package com.aberezovskaya.etherapists.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aberezovskaya.etherapists.App;


/**
 * Content Provider to manage the database
 */
public class DataContentProvider extends ContentProvider{

    /** class name for logging */
    private static final String TAG = DataContentProvider.class.getSimpleName();

    /** uri matcher */
    private static final UriMatcher URI_MATCHER;

    /** uri matcher codes */
    private static final int BODY_PROBLEM_CODE = 1;
    private static final int BODY_PROBLEM_BY_ID_CODE = 2;
    private static final int EXERCISE_CODE = 3;
    private static final int EXERCISE_BY_ID_CODE = 4;
    private static final int TRAINING_CODE = 5;
    private static final int TRAINING_BY_ID_CODE = 6;
    private static final int PHYSICAL_PROBLEM_CODE = 7;

    /** uri matcher initialization */
    static {

        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.BodyProblem.CONTENT_PATH, BODY_PROBLEM_CODE);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.BodyProblem.CONTENT_PATH + "/#", BODY_PROBLEM_BY_ID_CODE);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.Exercise.CONTENT_PATH, EXERCISE_CODE);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.Exercise.CONTENT_PATH + "/#", EXERCISE_BY_ID_CODE);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.Training.CONTENT_PATH, TRAINING_CODE);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.Training.CONTENT_PATH + "/#", TRAINING_BY_ID_CODE);
        URI_MATCHER.addURI(DataContract.AUTHORITY,
                DataContract.PhysicalProblem.CONTENT_PATH, PHYSICAL_PROBLEM_CODE);
    }

    private static final String SQL_TRAININGS = "(SELECT " +
            DataContract.Tables.TRAINING + "." + DataContract.Training.COLUMN_ID + ", " +
            DataContract.Tables.TRAINING + "." + DataContract.Training.COLUMN_CREATE_DATE + ", " +
            DataContract.Tables.TRAINING + "." + DataContract.Training.COLUMN_MODIFY_DATE + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_BODY_PART + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_DESCRIPTION + ", " +
            DataContract.Tables.EXERCISE + "." + "." + DataContract.Exercise.COLUMN_TITLE + ", " +
            DataContract.Tables.EXERCISE + "." + "." + DataContract.Exercise.COLUMN_IMAGE + ", " +
            DataContract.Tables.EXERCISE + "." + "." + DataContract.Exercise.COLUMN_DURATION + ", " +
            "FROM " +DataContract.Tables.TRAINING + " " +
            "LEFT JOIN " +DataContract.Tables.BODY_PROBLEM + " ON " +DataContract.Tables.BODY_PROBLEM + "."
            + DataContract.BodyProblem.COLUMN_ID + " = " + DataContract.Tables.TRAINING + "." + DataContract.Training.COLUMN_PROBLEM_ID + " = " + DataContract.Tables.TRAINING +
            "." + DataContract.Training.COLUMN_PROBLEM_ID +" " +
            "LEFT JOIN " + DataContract.Tables.EXERCISE + " ON " + DataContract.Tables.EXERCISE + "." + DataContract.Exercise.COLUMN_ID + " = " +
            DataContract.Tables.TRAINING + "." + DataContract.Training.COLUMN_EXERCISE_ID +
            ")";

    private static final String SQL_BODY_PROBLEMS = "SELECT " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_ID + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_CREATE_DATE + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_MODIFY_DATE + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_BODY_PART + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_DESCRIPTION + ", " +
            DataContract.Tables.BODY_PART + "." + DataContract.BodyPart.COLUMN_NAME + " " +
            "FROM " +DataContract.Tables.BODY_PROBLEM  + " " +
            "LEFT JOIN " +DataContract.Tables.BODY_PART + " ON " +DataContract.Tables.BODY_PART + "."
            + DataContract.BodyPart.COLUMN_ID + " = " + DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_BODY_PART;

    private static final String SQL_PHYSICAL_PROBLEM = "SELECT " +
            DataContract.Tables.PHYSICAL_PROBLEM + "." + DataContract.PhysicalProblem.COLUMN_ID + ", " +
            DataContract.Tables.PHYSICAL_PROBLEM + "." + DataContract.PhysicalProblem.COLUMN_CREATE_DATE + ", " +
            DataContract.Tables.PHYSICAL_PROBLEM + "." + DataContract.PhysicalProblem.COLUMN_MODIFY_DATE + ", " +
            DataContract.Tables.PHYSICAL_PROBLEM + "." + DataContract.PhysicalProblem.COLUMN_BODY_PROBLEM + ", " +
            DataContract.Tables.PHYSICAL_PROBLEM + "." + DataContract.PhysicalProblem.COLUMN_INTENSITY + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_DESCRIPTION + ", " +
            DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_BODY_PART + ", " +
            DataContract.Tables.BODY_PART + "." + DataContract.BodyPart.COLUMN_NAME + " " +
            "FROM " + DataContract.Tables.PHYSICAL_PROBLEM  + " " +
            "LEFT JOIN " + DataContract.Tables.BODY_PROBLEM + " ON " + DataContract.Tables.BODY_PROBLEM + "."
            + DataContract.BodyProblem.COLUMN_ID + " = " + DataContract.Tables.PHYSICAL_PROBLEM + "." + DataContract.PhysicalProblem.COLUMN_BODY_PROBLEM + " " +
            "LEFT JOIN " + DataContract.Tables.BODY_PART + " ON " + DataContract.Tables.BODY_PART + "." + DataContract.BodyPart.COLUMN_ID +
            " = " + DataContract.Tables.BODY_PROBLEM + "." + DataContract.BodyProblem.COLUMN_BODY_PART;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = DataSQLiteOpenHelper.instance().getReadableDatabase();
        final int code = URI_MATCHER.match(uri);
        Cursor cursor;
        switch (code) {

            case BODY_PROBLEM_CODE: {

              //  cursor = db.query(SQL_BODY_PROBLEMS, projection, selection, selectionArgs, null, null,
              //          sortOrder);
                cursor = db.rawQuery(SQL_BODY_PROBLEMS, null);
            }
            break;

            case BODY_PROBLEM_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                cursor = db.query(DataContract.Tables.BODY_PROBLEM, projection, DataContract.BodyProblem.COLUMN_ID + " = ?",
                        new String[] {id}, null, null, sortOrder);
            }
            break;

            case EXERCISE_CODE: {

                cursor = db.query(DataContract.Tables.EXERCISE, projection,selection, selectionArgs, null, null, sortOrder);
            }
            break;
            case EXERCISE_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                cursor = db.query(DataContract.Tables.EXERCISE, projection, DataContract.Exercise.COLUMN_ID + " = ?",
                        new String[] {id}, null, null, sortOrder);
            }
            break;
            case TRAINING_CODE: {

                //cursor = db.query(SQL_TRAININGS, projection, selection, selectionArgs, null, null, sortOrder);
                cursor = db.rawQuery(SQL_TRAININGS, null);
            }
            break;
            case TRAINING_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                cursor = db.query(DataContract.Tables.TRAINING, projection, DataContract.Training.COLUMN_ID + " = ?",
                        new String[] {id}, null, null, sortOrder);
            }
            break;

            case PHYSICAL_PROBLEM_CODE:{
                cursor = db.rawQuery(SQL_PHYSICAL_PROBLEM, null);
            }
            break;
            default: {

                Log.d(TAG, "query(): unsupported uri " + uri);
                return null;
            }
        }

        if (null != cursor) {

            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = DataSQLiteOpenHelper.instance().getWritableDatabase();
        final ContentResolver resolver = getContext().getContentResolver();
        final int code = URI_MATCHER.match(uri);
        long rowId;

        switch (code) {

            case BODY_PROBLEM_CODE: {

                rowId = db.insert(DataContract.Tables.BODY_PROBLEM, null, values);

                if (-1 != rowId) {

                    Uri insertedUri = ContentUris.withAppendedId(DataContract.BodyProblem.CONTENT_URI, rowId);
                    resolver.notifyChange(insertedUri, null);
                    return insertedUri;
                }

                throw new SQLException("Failed to insert row into " + uri);
            }

            case EXERCISE_CODE: {
                rowId = db.insert(DataContract.Tables.EXERCISE, null, values);

                if (-1 != rowId) {

                    Uri insertedUri = ContentUris.withAppendedId(DataContract.Exercise.CONTENT_URI, rowId);
                    resolver.notifyChange(insertedUri, null);
                    return insertedUri;
                }

                throw new SQLException("Failed to insert row into " + uri);
            }

            case TRAINING_CODE: {
                rowId = db.insert(DataContract.Tables.TRAINING, null, values);

                if (-1 != rowId) {

                    Uri insertedUri = ContentUris.withAppendedId(DataContract.Training.CONTENT_URI, rowId);
                    resolver.notifyChange(insertedUri, null);
                    return insertedUri;
                }

                throw new SQLException("Failed to insert row into " + uri);
            }

            case PHYSICAL_PROBLEM_CODE: {
                rowId = db.insert(DataContract.Tables.PHYSICAL_PROBLEM, null, values);

                if (-1 != rowId) {

                    Uri insertedUri = ContentUris.withAppendedId(DataContract.PhysicalProblem.CONTENT_URI, rowId);
                    resolver.notifyChange(insertedUri, null);
                    return insertedUri;
                }

                throw new SQLException("Failed to insert row into " + uri);
            }

            default: {

                Log.d(TAG, "insert(): unsupported uri " + uri);
            }
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = DataSQLiteOpenHelper.instance().getWritableDatabase();
        final ContentResolver resolver = getContext().getContentResolver();
        final int code = URI_MATCHER.match(uri);
        int count;

        switch(code) {

            case BODY_PROBLEM_CODE: {

                count = db.delete(DataContract.Tables.BODY_PROBLEM, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.BodyProblem.CONTENT_URI, null);
                }
            }
            break;

            case BODY_PROBLEM_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                count = db.delete(DataContract.Tables.BODY_PROBLEM, DataContract.BodyProblem.COLUMN_ID + " = ?", new String[] {id});

                if(count > 0) {

                    resolver.notifyChange(DataContract.BodyProblem.CONTENT_URI, null);
                    resolver.notifyChange(uri, null);
                }
            }
            break;
            case EXERCISE_CODE: {

                count = db.delete(DataContract.Tables.EXERCISE, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.Exercise.CONTENT_URI, null);
                }
            }
            break;

            case EXERCISE_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                count = db.delete(DataContract.Tables.EXERCISE, DataContract.Exercise.COLUMN_ID + " = ?", new String[] {id});

                if(count > 0) {

                    resolver.notifyChange(DataContract.Exercise.CONTENT_URI, null);
                    resolver.notifyChange(uri, null);
                }
            }
            break;
            case TRAINING_CODE: {

                count = db.delete(DataContract.Tables.TRAINING, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.Training.CONTENT_URI, null);
                }
            }
            break;

            case TRAINING_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                count = db.delete(DataContract.Tables.TRAINING, DataContract.Training.COLUMN_ID + " = ?", new String[] {id});

                if(count > 0) {

                    resolver.notifyChange(DataContract.Training.CONTENT_URI, null);
                    resolver.notifyChange(uri, null);
                }
            }
            break;
            case PHYSICAL_PROBLEM_CODE: {

                count = db.delete(DataContract.Tables.PHYSICAL_PROBLEM, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.PhysicalProblem.CONTENT_URI, null);
                }
            }
            break;

            default: {

                throw new IllegalArgumentException("delete(): unsupported uri " + uri);
            }
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = DataSQLiteOpenHelper.instance().getWritableDatabase();
        final ContentResolver resolver = getContext().getContentResolver();
        final int code = URI_MATCHER.match(uri);
        int count;

        switch(code) {

            case BODY_PROBLEM_CODE: {

                count = db.update(DataContract.Tables.BODY_PROBLEM, values, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.BodyProblem.CONTENT_URI, null);
                }
            }
            break;

            case BODY_PROBLEM_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                count = db.update(DataContract.Tables.BODY_PROBLEM, values, DataContract.BodyProblem.COLUMN_ID + " = ?", new String[] {id});

                if(count > 0) {

                    resolver.notifyChange(DataContract.BodyProblem.CONTENT_URI, null);
                    resolver.notifyChange(uri, null);
                }
            }
            break;

            case EXERCISE_CODE: {

                count = db.update(DataContract.Tables.EXERCISE, values, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.Exercise.CONTENT_URI, null);
                }
            }
            break;

            case EXERCISE_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                count = db.update(DataContract.Tables.EXERCISE, values, DataContract.Exercise.COLUMN_ID + " = ?", new String[] {id});

                if(count > 0) {

                    resolver.notifyChange(DataContract.Exercise.CONTENT_URI, null);
                    resolver.notifyChange(uri, null);
                }
            }
            break;

            case TRAINING_CODE: {

                count = db.update(DataContract.Tables.TRAINING, values, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.Training.CONTENT_URI, null);
                }
            }
            break;

            case TRAINING_BY_ID_CODE: {

                String id = uri.getLastPathSegment();
                count = db.update(DataContract.Tables.TRAINING, values, DataContract.Training.COLUMN_ID + " = ?", new String[] {id});

                if(count > 0) {

                    resolver.notifyChange(DataContract.Training.CONTENT_URI, null);
                    resolver.notifyChange(uri, null);
                }
            }
            break;

            case PHYSICAL_PROBLEM_CODE: {

                count = db.update(DataContract.Tables.PHYSICAL_PROBLEM, values, selection, selectionArgs);

                if(count > 0) {

                    resolver.notifyChange(DataContract.PhysicalProblem.CONTENT_URI, null);
                }
            }
            break;

            default: {

                throw new IllegalArgumentException("update(): unsupported uri " + uri);
            }
        }

        return count;
    }
}
