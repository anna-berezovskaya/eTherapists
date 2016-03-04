package model;


public enum BodyPartEnum {
    HEAD("head"),
    NECK("neck"),
    SHOULDER_RIGHT("shoulder_right"),
    SHOULDER_LEFT("shoulder_left"),
    KNEE("knee"),
    UNKNOWN("unknown");

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
