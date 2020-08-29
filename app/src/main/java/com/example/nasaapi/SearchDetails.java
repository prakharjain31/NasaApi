package com.example.nasaapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchDetails extends AppCompatActivity {

    public String mNasaId;
    public ImageView imageView;
    public RequestQueue mRequestQueue;
    AllianceLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        imageView = findViewById(R.id.imageView2);



        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mNasaId = getIntent().getStringExtra("id");
        loader = findViewById(R.id.loader1);
        loader.setVisibility(View.VISIBLE);

        parseJSON();
    }

    private void parseJSON() {
        if (mNasaId.length() == 0) {
            mNasaId = "NHQ_2016_0120_Jim%20Green%20Planet%20X";
        }
        String url = "https://images-api.nasa.gov/asset/" + mNasaId ;
        Log.e("url" , url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject collection = response.getJSONObject("collection");
                    JSONArray items = collection.getJSONArray("items");
                    JSONObject object = items.getJSONObject(0);
                    String href = object.getString("href");
                    Log.e("asdfg" , href);
                    Log.e("asdfg" , "" + imageView);
//                    Picasso.with(getApplicationContext()).load(href).resize(362 , 659).into(imageView);
                    Glide.with(getApplicationContext()).asBitmap().load(href).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    loader.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);

    }
}