package com.aberezovskaya.etherapists;

import com.aberezovskaya.etherapists.daos.BodyProblem;

/**
 * Created by aberezovskaya on 02.03.2016.
 */
public class Config {

    public static final BodyProblem[] PREDEFINED_BODY_PROBLEM = new BodyProblem[]{
            new BodyProblem(1L, System.currentTimeMillis(), System.currentTimeMillis(), "Arm", "Arm Pain", 50),
            new BodyProblem(2L, System.currentTimeMillis(), System.currentTimeMillis(), "Leg", "Leg Pain", 10),
            new BodyProblem(3L, System.currentTimeMillis(), System.currentTimeMillis(), "Foot", "Foot Pain", 100),
            new BodyProblem(4L, System.currentTimeMillis(), System.currentTimeMillis(), "Head", "Headache", 30)

};
}
