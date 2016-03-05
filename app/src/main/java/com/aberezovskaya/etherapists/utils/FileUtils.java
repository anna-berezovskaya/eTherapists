package com.aberezovskaya.etherapists.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static Bitmap getBitmapFromAsset(Context context, String filePath) throws IOException{
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        istr = assetManager.open(filePath);
        bitmap = BitmapFactory.decodeStream(istr);


        return bitmap;
    }

    public static JSONObject loadJSONFromAsset(Context context, String filePath) throws IOException, JSONException {
        String json = null;
        InputStream is = context.getAssets().open(filePath);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer);
        JSONObject obj = new JSONObject(json);

        return obj;
    }
}
