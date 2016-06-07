package com.sourav.loktraandroidtest.helpers.beans;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourav.loktraandroidtest.helpers.beans.response.CommitResponseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sourav on 7/6/16.
 */
public class Ajaxhelper {

    private static final String TAG = "Ajaxhelper";

    public static ArrayList<CommitResponseBean> ajax() {

        String response = null;
        String url = "https://api.github.com/repos/rails/rails/commits";

        try {
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response responsehttp = okHttpClient.newCall(request).execute();
            response = responsehttp.body().string();
            Log.d(TAG,"response== "+response);
        } catch (Exception e) {
            if(e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }

        ArrayList<CommitResponseBean> finalObject = null;
        Gson gson = new Gson();
        Type finalType = new TypeToken<ArrayList<CommitResponseBean>>() {}.getType();
        try {
            finalObject = gson.fromJson(response, finalType);
        } catch (Exception e) {
            Log.e(TAG, "exception " + e.getMessage());
        }
        if (response != null && !response.equals("")) {
            return finalObject;
        } else {
            return null;
        }
    }
}
