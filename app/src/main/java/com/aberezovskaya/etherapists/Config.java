package com.aberezovskaya.etherapists;

import com.aberezovskaya.etherapists.daos.BodyPart;
import com.aberezovskaya.etherapists.daos.BodyProblem;
import com.aberezovskaya.etherapists.daos.Exercise;
import com.aberezovskaya.etherapists.daos.PhysicalProblem;
import com.aberezovskaya.etherapists.daos.Training;

import com.aberezovskaya.etherapists.model.BodyPartEnum;


public class Config {

    public static final String ASSETS_URI_EXERCISES = "file:///android_asset/exercises/";

    /**
     * predefined data arrays
     * needed to pre-fill data for the test purposes (to give a fill of the app UI)!
     * In delivery this may be left in app, (if this is suitable), or removed,
     * to let the users create everything by themselve.
     * bodyParts array can be left "as-is", or another way of DB preparing can be used.
     */

    public static final BodyPart[] PARTS_OF_BODY_LIST = new BodyPart[]{
            new BodyPart(1L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.HEAD),
            new BodyPart(2L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.NECK),
            new BodyPart(3L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.SHOULDER_LEFT),
            new BodyPart(4L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.SHOULDER_RIGHT),
            new BodyPart(5L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.BACK),
    };

    public static final BodyProblem[] PREDEFINED_BODY_PROBLEM = new BodyProblem[]{
            new BodyProblem(1L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, "Head problem"),
            new BodyProblem(2L, System.currentTimeMillis(), System.currentTimeMillis(), 2L, "Neck problem"),
            new BodyProblem(3L, System.currentTimeMillis(), System.currentTimeMillis(), 3L, "Left shoulder problem"),
            new BodyProblem(4L, System.currentTimeMillis(), System.currentTimeMillis(), 4L, "Right shoulder problem"),
            new BodyProblem(5L, System.currentTimeMillis(), System.currentTimeMillis(), 5L, "Back problem"),
            new BodyProblem(6L, System.currentTimeMillis(), System.currentTimeMillis(), 5L, "Back problem_1"),
            new BodyProblem(7L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, "Head problem_1"),

    };

    public static final PhysicalProblem[] PREDEFINED_PHYSICAL_PROBLEMS = new PhysicalProblem[]{
            new PhysicalProblem(1L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, 30),
            new PhysicalProblem(2L, System.currentTimeMillis(), System.currentTimeMillis(), 3L, 10),
            new PhysicalProblem(3L, System.currentTimeMillis(), System.currentTimeMillis(), 2L, 60)
    };

    public static final Exercise[] PREDEFINED_EXERCISES = new Exercise[]{
            new Exercise(1L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex1", "coaching.png", 3 ),
            new Exercise(2L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex2", "coaching.png", 20 ),
            new Exercise(3L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex3", "coaching.png", 5 ),
            new Exercise(4L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex4", "coaching.png", 10 ),
            new Exercise(5L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex5", "coaching.png", 15 ),
            new Exercise(6L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex6", "coaching.png", 100 ),
            new Exercise(7L, System.currentTimeMillis(), System.currentTimeMillis(), "Ex7", "coaching.png", -1 ),

    };

    public static final Training[] PREDEFINED_TRAININGS = new Training[]{
            new Training(1L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, 1L),
            new Training(2L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, 2L),
            new Training(3L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, 3L),
            new Training(4L, System.currentTimeMillis(), System.currentTimeMillis(), 2L, 1L),
            new Training(5L, System.currentTimeMillis(), System.currentTimeMillis(), 2L, 6L),
            new Training(6L, System.currentTimeMillis(), System.currentTimeMillis(), 2L, 7L),
            new Training(7L, System.currentTimeMillis(), System.currentTimeMillis(), 3L, 3L),
            new Training(8L, System.currentTimeMillis(), System.currentTimeMillis(), 3L, 4L),
            new Training(9L, System.currentTimeMillis(), System.currentTimeMillis(), 3L, 5L),
            new Training(10L, System.currentTimeMillis(), System.currentTimeMillis(), 4L, 5L),
            new Training(11L, System.currentTimeMillis(), System.currentTimeMillis(), 4L, 6L),
            new Training(12L, System.currentTimeMillis(), System.currentTimeMillis(), 4L, 7L),
    };
}
