package com.example.nasaapi;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class SecondFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static SearchView searchView;
    public static RequestQueue mRequestQueue ;
    public static ArrayList<SearchData> dataArrayList;
    public static SearchDataAdapter searchDataAdapter;


    public SecondFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false));
        dataArrayList = new ArrayList<>();
        SearchView searchView1 = view.findViewById(R.id.search_bar);

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                parseTEXT(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dataArrayList.clear();
                parseTEXT(newText);
                return true;
            }
        });


        mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext())) ;
        return view;
    }


    private void parseTEXT(String newText) {
        if (newText.length() < 1) {
            newText = "p";
        }
        String url = "https://images-api.nasa.gov/search?q=" + newText ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject collection = response.getJSONObject("collection");
                    JSONArray jsonArray = collection.getJSONArray("items");
                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject data_object = (JSONObject) data.get(0);
                        String title = data_object.getString("title");
                        String description = data_object.getString("description");
                        String nasa_id = data_object.getString("nasa_id");
                        if (description.length() > 450) {
                            description = description.substring(0 , 450);
                        }
                        Log.e(title + nasa_id , description);
                        SearchData s = new SearchData(title , description , nasa_id);
                        Log.e("qwerty" , "" + s);
                        dataArrayList.add(s);

                    }
                    if (dataArrayList.size() == 0) {
                        dataArrayList.add(new SearchData("No Results\nEnter a different query" , "" , "-1"));
                    }
                    searchDataAdapter = new SearchDataAdapter(getContext() , dataArrayList);
                    recyclerView.setAdapter(searchDataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "errorbbbbbb", Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(request);

    }





}