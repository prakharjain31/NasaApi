package com.example.nasaapi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
    }

    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new PicureofthedayFragment(), "APOD");
        viewPagerAdapter.addFragments(new SecondFragment(), "Search");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search , menu);
//        MenuItem menuItem = menu.findItem(R.id.search_button);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                parseTEXT(newText);
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

//    private void parseTEXT(String newText) {
//        String url = "https://images-api.nasa.gov/search?q=" + newText ;
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONObject collection = response.getJSONObject("collection");
//                    JSONArray jsonArray = collection.getJSONArray("items");
//                    for (int i = 0 ; i < jsonArray.length() ; i++) {
//                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                        JSONArray data = jsonObject.getJSONArray("data");
//                        JSONObject data_object = (JSONObject) data.get(0);
//                        String title = data_object.getString("title");
//                        String description = data_object.getString("description");
//                        String nasa_id = data_object.getString("nasa_id");
//                        dataArrayList.add(new SearchData(title , description , nasa_id));
//
//                    }
//                    searchDataAdapter = new SearchDataAdapter(getApplicationContext() , dataArrayList);
//                    recyclerView.setAdapter(searchDataAdapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Toast.makeText(getApplicationContext(), "errorbbbbbb", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        void addFragments(Fragment fragment , String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }}
