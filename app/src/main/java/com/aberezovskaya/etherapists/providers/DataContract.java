package com.aberezovskaya.etherapists.providers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for use with the Content Provider
 */
public class DataContract {

    /**
     * authority
     */
    public static final String AUTHORITY =  "com.aberezovskaya.etherapists.eTherapistsDataProvider";

    /**
     * content uri
     */
    public final static Uri CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY).build();

    /**
     * body problem
     */
    public interface BodyProblem extends BaseEntityColumns {

        public static final String CONTENT_PATH = "body_problem";
        public static final String JOIN_CONTENT_PATH = "body_problem_join_part";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI,
                CONTENT_PATH);
        public static final Uri JOIN_CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI, JOIN_CONTENT_PATH);

        public final static String COLUMN_BODY_PART = "body_part";
        public final static String COLUMN_DESCRIPTION = "column_description";
    }

    /**
     * physical problem
     * concrete user problem
     */
    public interface PhysicalProblem extends BaseEntityColumns{
        public static final String CONTENT_PATH = "physical_problem";
        public static final String JOIN_CONTENT_PATH = "physical_problem_join_bp";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI, CONTENT_PATH);
        public static final Uri JOIN_CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI, JOIN_CONTENT_PATH);
        public final static String COLUMN_BODY_PROBLEM= "body_problem";
        public final static String COLUMN_INTENSITY = "intensity";
    }

    /**
     * exercise
     */
    public interface Exercise extends BaseEntityColumns {

        public static final String CONTENT_PATH = "exercise";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI,
                CONTENT_PATH);

        public final static String COLUMN_IMAGE = "image";
        public final static String COLUMN_DURATION = "duration";
        public final static String COLUMN_TITLE = "title";
    }

    /**
     * training
     */
    public interface Training extends BaseEntityColumns {

        public static final String CONTENT_PATH = "training";
        public static final String JOIN_CONTENT_PATH = "training_joing_exercises_problem";
        public static final String DAILY_TRAININGS_CONTENT_PATH = "training_daily";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI,
                CONTENT_PATH);
        public static final Uri JOIN_CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI, JOIN_CONTENT_PATH);
        public static final Uri DAILY_TRAININGS_CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI, DAILY_TRAININGS_CONTENT_PATH);

        public final static String COLUMN_PROBLEM_ID = "problem_id";
        public final static String COLUMN_EXERCISE_ID = "exercise_id";
    }

    /**
     * body part
     */
    public interface BodyPart extends BaseEntityColumns {

        public static final String CONTENT_PATH = "body_part";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(DataContract.CONTENT_URI,
                CONTENT_PATH);

        public final static String COLUMN_NAME = "name";
    }


    public interface Tables {

        String BODY_PROBLEM = "body_problem";
        String EXERCISE = "exercise";
        String TRAINING = "training";
        String BODY_PART = "body_part";
        String PHYSICAL_PROBLEM = "physical_problem";
    }

    /**
     * base entity columns
     */
    public interface BaseEntityColumns {

        /**
         * The unique ID for a row.
         * <P>Type: TEXT</P>
         */
        String COLUMN_ID = BaseColumns._ID;

        /**
         * Creation date
         * <p>Type: LONG
         */
        String COLUMN_CREATE_DATE = "create_date";

        /**
         * Modification date
         * <p>Type: LONG
         */
        String COLUMN_MODIFY_DATE = "modify_date";
    }

}
