package com.example.ztpmobilprojekt;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DictionaryDataService {

    public static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    Context context;

    private JSONObject  requestedObj;
    private JSONArray meanings;

    public DictionaryDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(JSONObject response);
    }
    public void makeRequest(String word, VolleyResponseListener volleyResponseListener){


        String url = API_URL + word;  //TODO moze dodac wybor jezyka

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    requestedObj = response.getJSONObject(0);
                    meanings = requestedObj.getJSONArray("meanings");
                    Toast.makeText(context,R.string.request_success,Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onResponse(requestedObj);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,R.string.request_error,Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something went wrong");
            }
        });

        DictionarySingleton.getInstance(context).addToRequestQueue(request);


    }

   public String getWord() throws JSONException {

        return this.requestedObj.getString("word");
   }
    public String getPhonetic() throws JSONException {

        if(!this.requestedObj.has("phonetic")){
            return this.requestedObj.getJSONArray("phonetics").getJSONObject(1).getString("text");
        }

        return this.requestedObj.getString("phonetic");
    }
    public String getPartOfSpeech() throws JSONException {   //TODO moze dodac list

        return this.meanings.getJSONObject(0).getString("partOfSpeech");
    }
    public String getDefinition() throws JSONException {  //TODO moze dodac liste

        return this.meanings.getJSONObject(0).getJSONArray("definitions").getJSONObject(0).getString("definition");
   }
}
