package com.aberezovskaya.etherapists;

import com.aberezovskaya.etherapists.daos.BodyPart;
import com.aberezovskaya.etherapists.daos.BodyProblem;

import model.BodyPartEnum;


public class Config {

    public static final BodyPart[] PARTS_OF_BODY_LIST = new BodyPart[]{
            new BodyPart(1L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.HEAD),
            new BodyPart(2L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.NECK),
            new BodyPart(3L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.SHOULDER_LEFT),
            new BodyPart(4L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.SHOULDER_RIGHT),
            new BodyPart(5L, System.currentTimeMillis(), System.currentTimeMillis(), BodyPartEnum.KNEE),
    };

    public static final BodyProblem[] PREDEFINED_BODY_PROBLEM = new BodyProblem[]{
            new BodyProblem(1L, System.currentTimeMillis(), System.currentTimeMillis(), 1L, "Arm Pain", 50),
            new BodyProblem(2L, System.currentTimeMillis(), System.currentTimeMillis(), 2L, "Leg Pain", 10),
            new BodyProblem(3L, System.currentTimeMillis(), System.currentTimeMillis(), 3L, "Foot Pain", 100),
            new BodyProblem(4L, System.currentTimeMillis(), System.currentTimeMillis(), 4L, "Headache", 30)

};
}
