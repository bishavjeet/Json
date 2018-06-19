package com.example.devil.mvplayer.fragment.listdetail.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.PlaylistBean;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.adapter.ListDetailAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;
import com.example.devil.mvplayer.fragment.listdetail.view.ListDetailview;
import com.example.devil.mvplayer.fragment.playlist.Playlist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by whizkraft on 12/12/17.
 */

public class ListDetailPresenter implements IlistDetail {
    public static ArrayList<SubCategoryGetter> finallist;
    public ArrayList<SubCategoryGetter> list;
    Activity activity;
    ListDetailview listDetailview;
    ListDetailAdapter listDetailAdapter;

    public ListDetailPresenter(Activity activity, ListDetailview listDetailview) {

        this.activity = activity;
        this.listDetailview = listDetailview;

    }

    @Override
    public void getlistDetail() {
        int number = Integer.parseInt(LdPreferences.readString(activity, "playlist_postion"));
        PlaylistBean playlistBean = Constant.favlist.get(number);
        finallist = playlistBean.getList();
        listDetailAdapter = new ListDetailAdapter(playlistBean.getList(), activity, new OnRecyclerViewItemClickListener() {

            @Override
            public void onRecyclerViewItemClicked(String view, int postion) {
                SubCategoryGetter subCategoryGetter = Constant.playlist.get(postion);
                switch (LdPreferences.readString(activity, "click")) {


                    case "view":
                        listDetailview.newsongs(subCategoryGetter.getSongurl());
                        break;
                    case "select":

                        String deletesongurl = Config.deletesong+"&UserId="+LdPreferences.readString(activity,"userId")+"&playlistId="+LdPreferences.readString(activity,"playlistid")+"&songId="+subCategoryGetter.getSongid();

                        new hitdelteSong().execute(deletesongurl);
                        break;
                }


            }
        });
        listDetailview.setListAdapter(listDetailAdapter);

    }

    @Override
    public void deletePlayList() {





/*
//
//  */
  /*private void replaceWithPlaylist() {
        //CSPreferences.putString(activity, "title_text", "My Playlist");
     Fragment fragment = new Playlist();
       android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();transaction.replace(R.id.fragment, fragment, null);
       transaction.addToBackStack(null);
       transaction.commit();
   }
*/

    }
    public class hitdelteSong extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("delete ", "--" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if (code.equalsIgnoreCase("1")) {

                       Fragment fragment = new Playlist();
                        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.contentFrame, fragment, null);
                        transaction.addToBackStack(null);
                        transaction.commit();


                    } else {
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, "Please check your internet conection", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
