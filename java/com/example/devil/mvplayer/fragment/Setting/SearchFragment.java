package com.example.devil.mvplayer.fragment.Setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.CategoryGetter;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.adapter.AllSongAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;
import com.example.devil.mvplayer.adapter.SongSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment {

    public static ArrayList<CategoryGetter> categorylist;
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    ArrayList<SubCategoryGetter> sublist;
    //  ProgressDialog progressDialog;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtPlayAll)TextView textPlayAll;

    Activity activity;
    private SongSearchAdapter allSongAdapter;
    public static   boolean clickable = true;
    ProgressDialog progressDialog;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag_all_song, container, false);
        ButterKnife.bind(this, layout);

        progressDialog = new ProgressDialog(getActivity());
        activity = getActivity();
        progressDialog = new ProgressDialog(activity);
        ((Home) getActivity()).setToolBaricon();
        LdPreferences.putString(activity,"back_status","true");
        LdPreferences.putString(activity,"fragment","4");
        LdPreferences.putString(activity,"from","search");

        init(layout);


        return (layout);

    }

    private void init(View layout) {
        textPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity, "playall", Toast.LENGTH_SHORT).show();

                Constant.rember_status = "true";
                Constant.playlist = sublist;
                Constant.playlist = sublist;
                ((Home) getActivity()).playAll();
            }
        });

        String url = Config.serachurl+LdPreferences.readString(activity, "search_text");
        Log.e("search url ", url);
        new Test().execute(url);


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


            Log.e("search ", "Song response--" + response);

            return response;

        }

        @OnClick({R.id.txtPlayAll})
        public void buttonClicks(View view) {
            switch (view.getId()) {
                case R.id.txtPlayAll:

                    break;
                case R.id.main_layout:
                    break;

            }
        }

        @Override
        protected void onPostExecute(final String s) {
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

                        Constant.playlist = sublist;


                    }
                    AllSongAdapter allSongAdapter = new AllSongAdapter(activity, sublist, new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onRecyclerViewItemClicked(String view, int postion) {

                            switch (LdPreferences.readString(activity,"click")){

                                case "view":
                                    if (clickable) {
                                        showProgress();
                                        clickable = false;
                                        Constant.playlist = sublist;
                                        LdPreferences.putString(activity, "postion", String.valueOf(postion));
                                        // Toast.makeText(activity, ""+positionnumber, Toast.LENGTH_SHORT).show();
                                        SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                                        playNewSong(contentDetail.getSongurl());


                                    }
                                    break;
                            }


                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                    recyclerView.setAdapter(allSongAdapter);
                } else {
                    Toast.makeText(activity, "no category", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showProgress(){
        progressDialog.show();
    }

    public void dismissDialog(){
        progressDialog.dismiss();
    }


    public void playNewSong(String path){

        LdPreferences.putString(activity,"from","content");
        LdPreferences.putString(activity,"play_status","play");
        ((Home) getActivity()).playSong(Config.songBaseurl+path);
        Handler hanler = new Handler();
        hanler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialog();
                clickable=true;
            }
        }, 2000);

    }
}




