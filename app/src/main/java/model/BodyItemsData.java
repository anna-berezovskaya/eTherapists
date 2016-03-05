package model;


import android.graphics.Point;

import com.aberezovskaya.etherapists.providers.DataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BodyItemsData {

    private static String HEAD = "head";
    private static String NECK = "neck";
    private static String BACK = "back";
    private static String RSHOULDER = "right_shoulder";
    private static String LSHOULDER = "left_shoulder";

    private Point mHeadCoord;
    private Point mNeckCoord;
    private Point mBackCoord;
    private Point mRightShoulderCoord;
    private Point mLeftShoulderCoord;

    private boolean mIsEmpty = true;


    public void build(JSONObject json) throws JSONException{
        JSONArray array = json.getJSONArray(HEAD);
        mHeadCoord = new Point(array.getInt(0), array.getInt(1));
        array = json.getJSONArray(NECK);
        mNeckCoord = new Point(array.getInt(0), array.getInt(1));
        array = json.getJSONArray(BACK);
        mBackCoord = new Point(array.getInt(0), array.getInt(1));
        array = json.getJSONArray(RSHOULDER);
        mRightShoulderCoord = new Point(array.getInt(0), array.getInt(1));
        array = json.getJSONArray(LSHOULDER);
        mLeftShoulderCoord = new Point(array.getInt(0), array.getInt(1));
        mIsEmpty = false;
    }

    public boolean isEmpty(){
        return mIsEmpty;
    }

    public Point getNeckCoord(){
        return mNeckCoord;
    }

    public Point getBackCoord(){
        return mBackCoord;
    }

    public Point getRightShoulderCoord(){
        return mRightShoulderCoord;
    }

    public Point getmLeftShoulderCoord(){
        return mLeftShoulderCoord;
    }
}
