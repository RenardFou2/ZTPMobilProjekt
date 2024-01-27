package com.example.ztpmobilprojekt;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DictionarySingleton {

    private static DictionarySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private DictionarySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized DictionarySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new DictionarySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
