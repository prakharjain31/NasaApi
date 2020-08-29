package com.example.nasaapi;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.agrawalsuneet.dotsloader.loaders.AllianceLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

public class PicureofthedayFragment extends Fragment {

    EditText editText;
    DatePickerDialog datepicker;
    ImageView imageView;
    public WebView webView;
    public int mYear, mMonth, mDay;
    RequestQueue mRequestQueue;

    AllianceLoader loader;

    public PicureofthedayFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picureoftheday, container, false);
        editText = view.findViewById(R.id.date_edit);
        editText.setInputType(InputType.TYPE_NULL);
        imageView = view.findViewById(R.id.image_view);

        webView = view.findViewById(R.id.web_view);


        loader = view.findViewById(R.id.loader);
        loader.setVisibility(View.INVISIBLE);


        mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                final int month = c.get(Calendar.MONTH);
                final int year1 = c.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (year > 2020) {
                                    Toast.makeText(getContext(), "Enter a date not of the future", Toast.LENGTH_SHORT).show();
                                } else if (year == 2020 & monthOfYear > month) {
                                    Toast.makeText(getContext(), "Enter a date not of the future", Toast.LENGTH_SHORT).show();
                                } else if (monthOfYear == month & dayOfMonth > day) {
                                    Toast.makeText(getContext(), "Enter a date not of the future", Toast.LENGTH_SHORT).show();
                                } else {
                                    mYear = year;
                                    mMonth = monthOfYear + 1;
                                    mDay = dayOfMonth;
                                    loader.setVisibility(View.VISIBLE);
                                    editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    parseJSON();
                                }
                            }
                        }, year1, month, day);
                datepicker.show();
            }
        });


        return view;
    }

    private void parseJSON() {

//        String url = "https://api.nasa.gov/planetary/apod?date=2020-08-20&api_key=IAbKthtBFSJA0Qcolw2hEN04G9zE5qSV69wCjxlS";
        final String url = "https://api.nasa.gov/planetary/apod?date=" + mYear + "-" + mMonth + "-" + mDay + "&api_key=IAbKthtBFSJA0Qcolw2hEN04G9zE5qSV69wCjxlS";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("media_type").equals("image")) {

                                String imgurl = response.getString("url");
                                imageView.setVisibility(View.VISIBLE);
                                webView.setVisibility(View.INVISIBLE);
                                Picasso.with(getContext()).load(imgurl).resize(395, 620).into(imageView);

                            } else {
                                String imgurl = response.getString("url");

                                imageView.setVisibility(View.INVISIBLE);
                                webView.setVisibility(View.VISIBLE);
                                webView.loadUrl(imgurl);
                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.setWebViewClient(new WebViewClient());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        } finally {
                            loader.setVisibility(View.INVISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "errorbbbbbb", Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(request);

    }

}


