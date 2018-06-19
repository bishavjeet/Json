package com.example.devil.mvplayer.fragment.downloads.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.adapter.DownloadAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;
import com.example.devil.mvplayer.database.MusicDataBase;
import com.example.devil.mvplayer.fragment.downloads.view.MyDownloadview;

import java.util.ArrayList;


public class DownloadPresenter implements IMydownload {
    public static ArrayList<SubCategoryGetter> downlist;
    public static ArrayList<String> idlist;

    Activity activity;
    MyDownloadview myDownloadview;
    ArrayList<SubCategoryGetter> list;

    DownloadAdapter downloadAdapter;
    MusicDataBase musicDataBase;
   // PendinglistAdapter pendinglistAdapter;

    public DownloadPresenter(Activity activity, MyDownloadview myDownloadview) {
        this.activity = activity;
        this.myDownloadview = myDownloadview;
        musicDataBase = new MusicDataBase(activity);
    }


    @Override
    public void getDownloadfromLocal(ProgressBar progressBar) {
        downlist = new ArrayList<>();
        idlist = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        getdatafromLocalDatabase(progressBar);

    }
    private void getdatafromLocalDatabase(ProgressBar progressBar) {
        list = (ArrayList<SubCategoryGetter>) musicDataBase.getDownloads();
        if (list.size() > 0) {

                setAdapter(list);

            //  Toast.makeText(activity, "" + locallist1.size() + " " + Home.FilesInFolder.size(), Toast.LENGTH_SHORT).show();
           // setAdapter(locallist1);

        } else {

           Toast.makeText(activity, "There is no song Download yet", Toast.LENGTH_SHORT).show();
        }


    }




    private void setAdapter(final ArrayList<SubCategoryGetter> locallist) {
        downloadAdapter = new DownloadAdapter(activity, locallist, new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClicked(String view, int postion) {
                LdPreferences.putString(activity, "postion", postion + "");
                Constant.playlist = list;
                LdPreferences.putString(activity, "title_text", "Player");
                myDownloadview.setPlaysong();
            }
        });

        myDownloadview.setAdapter(downloadAdapter);


    }
}
