package model;


import com.aberezovskaya.etherapists.App;
import com.aberezovskaya.etherapists.R;

public enum BodyPartEnum {
    HEAD(App.instance().getString(R.string.head)),
    NECK(App.instance().getString(R.string.neck)),
    SHOULDER_RIGHT(App.instance().getString(R.string.right_shoulder)),
    SHOULDER_LEFT(App.instance().getString(R.string.left_shoulder)),
    BACK(App.instance().getString(R.string.back)),
    UNKNOWN(App.instance().getString(R.string.unknown));

    private String mTag;

    BodyPartEnum(String tag){

        mTag = tag;
    }

    public String getTag(){
        return mTag;
    }


    public static BodyPartEnum getBPByTag(String tag){
       for (BodyPartEnum part :BodyPartEnum.values()){
           if (part.mTag.equals(tag)){
               return part;
           }
       }

        return UNKNOWN;
    }

}
