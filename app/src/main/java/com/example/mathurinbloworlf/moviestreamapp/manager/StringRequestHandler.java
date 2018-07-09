package com.example.mathurinbloworlf.moviestreamapp.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mathurin Bloworlf on 21/04/2017.
 */

public class StringRequestHandler {
    private static StringRequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private StringRequestHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized StringRequestHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StringRequestHandler(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
