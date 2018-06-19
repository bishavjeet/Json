package com.example.devil.mvplayer.fragment.playlist.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.PlaylistBean;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.Utils.Util;
import com.example.devil.mvplayer.adapter.GetPlayLIstAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;
import com.example.devil.mvplayer.fragment.customplayer.PlayerPresenter;
import com.example.devil.mvplayer.fragment.listdetail.ListDetail;
import com.example.devil.mvplayer.fragment.playlist.view.Playlistview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by whizkraft on 11/12/17.
 */

public class Playlistpresenter implements IPlaylist {

    Activity activity;
    Playlistview playlistview;
    ArrayList<PlaylistBean>playlistsongs;
    ArrayList<SubCategoryGetter>songplaylist;
    GetPlayLIstAdapter getPlayListAdapter;

    public Playlistpresenter(Activity activity, Playlistview playlistview) {

        this.activity = activity;
        this.playlistview = playlistview;

    }

    @Override
    public void getPlaylist() {
        String getplaylisturl = Config.getplaylisturl + "&UserId=" + LdPreferences.readString(activity,"userId");
        new HitGetPlaylist().execute(getplaylisturl);


    }

    @Override
    public void createPlaylist() {
        createPlaylistDialog();
    }

    private void createPlaylistDialog() {
        final BottomSheetDialog createbottomdialog = Util.createBottomSheetDialog(activity, R.layout.createplaylistbottomlayout);
        createbottomdialog.show();
        RelativeLayout headinglayout;
        LinearLayout buttonlayoutback;

        final TextView createPlaylist = (TextView) createbottomdialog.findViewById(R.id.createPlaylistt);
        final EditText playlistName = (EditText) createbottomdialog.findViewById(R.id.edt_playlistname);
        headinglayout =(RelativeLayout)createbottomdialog.findViewById(R.id.headinglayout);
        buttonlayoutback =(LinearLayout)createbottomdialog.findViewById(R.id.buttonbacklayout);
        TextView cancel = (TextView) createbottomdialog.findViewById(R.id.cancelPlaylist);
       ;
        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (playlistName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, "Required", Toast.LENGTH_SHORT).show();
                } else {

                   // hitCreatePlaylistServie(createplayParms);
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createbottomdialog.dismiss();

            }
        });

    }




    public class HitGetPlaylist extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("createplaylist ", "--" + response);
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    int playlistcount;

                    playlistsongs = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            songplaylist = new ArrayList<>();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String playlistid = jsonObject1.getString("playlistId");
                            String playlistname = jsonObject1.getString("playlist_name");
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("playlists");
                            if (jsonArray1.length() > 0) {

                                playlistcount = jsonArray1.length();

                                for (int j = 0; j < jsonArray1.length(); j++) {

                                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                    String id = jsonObject2.getString("id");
                                    String title = jsonObject2.getString("song_name");
                                    String songurl = jsonObject2.getString("song_url");
                                    String relaesdate = "4-4-2017";
                                    String artist = "Abc";
                                    String status = "0";
                                    String imageurl = jsonObject2.getString("song_image");
                                    String songid = jsonObject2.getString("songId");
                                    SubCategoryGetter subCategoryGetter = new SubCategoryGetter(id,title,songurl,relaesdate,artist,status,imageurl,songid);
                                    songplaylist.add(subCategoryGetter);


                                }
                            } else {

                                playlistcount = 0;

                            }
                            PlaylistBean playlistBean = new PlaylistBean(playlistname,playlistid,songplaylist,playlistcount+"");
                            playlistsongs .add(playlistBean);




                        }
                        Constant.favlist = playlistsongs;
                        getPlayListAdapter = new GetPlayLIstAdapter(activity, playlistsongs, new OnRecyclerViewItemClickListener() {
                            @Override
                            public void onRecyclerViewItemClicked(String view, int postion) {

                                LdPreferences.putString(activity,"playlist_postion",postion+"");
                                replacedetailFragment();


                            }
                        });
                        playlistview.setPlayAdapter(getPlayListAdapter);


                    } else {

                        Toast.makeText(activity, "There is no playlist please create Playlist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, "Please check your internet coneection", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void replacedetailFragment() {
        Fragment fragment = new ListDetail();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }


 /*   private void replaceWithPlaylist() {
        CSPreferences.putString(activity, "title_text", "My Playlist");
        Fragment fragment = new imminent.app.musicapp.fragment.playlist.Playlist();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


  /*  private void replacedetailFragment() {
        Fragment fragment = new ListDetail();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/
}
