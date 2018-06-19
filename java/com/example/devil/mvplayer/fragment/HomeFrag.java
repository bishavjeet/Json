package com.example.devil.mvplayer.fragment;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.devil.mvplayer.adapter.BannerAdpter;
import com.example.devil.mvplayer.fragment.AllSongFrag;
import com.example.devil.mvplayer.Model.CategoryGetter;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.adapter.CustomCategoryAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;
import com.example.devil.mvplayer.fragment.customplayer.CustomePlayerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Devil on 3/22/2018.
 */

public class HomeFrag extends android.support.v4.app.Fragment {
    public static ArrayList<CategoryGetter> categorylist;
    Activity activity;
    ArrayList<SubCategoryGetter> sublist;
    BannerAdpter bannerAdpter;
    ArrayList<String> bannerlist;
    ProgressDialog progressDialog;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private CustomCategoryAdapter adapter;
    ViewPager viewPager;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_home, container, false);
        ButterKnife.bind(this, layout);
        activity = getActivity();
        progressDialog = new ProgressDialog(getActivity());
        LdPreferences.putString(activity, "fragment", "1");
        viewPager=(ViewPager)layout.findViewById(R.id.viewpager);


        bannerlist = new ArrayList<>();
        bannerlist.add("https://gaana.com/playlist/sksujauddin2ganacomsongs");

        bannerlist.add("https://gaana.com/playlist/sksujauddin2ganacomsongs");

        bannerlist.add("https://gaana.com/playlist/sksujauddin2ganacomsongs");

        bannerlist.add("https://gaana.com/playlist/sksujauddin2ganacomsongs");
        bannerlist.add("https://gaana.com/playlist/sksujauddin2ganacomsongs");
        bannerlist.add("https://gaana.com/playlist/sksujauddin2ganacomsongs");
        init(layout);
        return layout;

    }

    private void init(View layout) {

        bannerAdpter = new BannerAdpter(activity, bannerlist, new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClicked(String view, int postion) {

            }
        });
        viewPager.setAdapter(bannerAdpter);

        new Test().execute(Config.getCategory);


    }

    public void AllSongFrag() {
        FragmentManager fm = getFragmentManager();

// Transaction start
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentFrame, new AllSongFrag());
        ft.commit();
    }

    public class Test extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading.....");
            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("response ", "login response--" + response);

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            categorylist = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                String code = jsonObject.getString("code");
                if (code.equalsIgnoreCase("1")) {
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        sublist = new ArrayList<>();
                        JSONObject jsonObject3 = jsonArray1.getJSONObject(i);
                        String title = jsonObject3.getString("title");
                        String id = jsonObject3.getString("id");
                        JSONArray jsonArray = jsonObject3.getJSONArray("data");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject4 = jsonArray.getJSONObject(j);
                            String subid = jsonObject4.getString("id");
                            String subname = jsonObject4.getString("name");
                            String songurl = jsonObject4.getString("location");
                            String releasedate = jsonObject4.getString("releaseDate");
                            String status = jsonObject4.getString("status");
                            String artist = jsonObject4.getString("artist");
                            String image = jsonObject4.getString("Image");
                            String songid = jsonObject4.getString("song_id");
                            SubCategoryGetter subCategorygetter = new SubCategoryGetter(subid, subname, songurl, releasedate, "vijay", status, image, songid);
                            sublist.add(subCategorygetter);
                        }
                        CategoryGetter categoryGetter = new CategoryGetter(id, title, sublist);
                        categorylist.add(categoryGetter);

                    }

                  /*  adapter = new CustomCategoryAdapter(activity, categorylist, new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onRecyclerViewItemClicked(String view, int postion) {

                        }
                    });*/
                    recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));


                    adapter = new CustomCategoryAdapter(activity, categorylist, new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onRecyclerViewItemClicked(String view, int postion) {

                            switch (LdPreferences.readString(activity, "click")) {

                                case "seeall":
                                    AllSongFrag();


                                    break;
                                case "detail":

                                    replacePlayerFragment();


                                    break;
                            }
                        }
                    });

                    recyclerView.setAdapter(adapter);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }private void replacePlayerFragment() {
        Fragment fragment = new CustomePlayerFragment();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}




