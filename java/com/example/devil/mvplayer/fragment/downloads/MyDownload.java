package com.example.devil.mvplayer.fragment.downloads;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Util;
import com.example.devil.mvplayer.adapter.DownloadAdapter;
import com.example.devil.mvplayer.database.MusicDataBase;
import com.example.devil.mvplayer.fragment.downloads.presenter.DownloadPresenter;
import com.example.devil.mvplayer.fragment.downloads.view.MyDownloadview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;


/**
 * Created by whizkraft on 5/12/17.
 */

public class MyDownload extends Fragment implements MyDownloadview{
    Activity activity;
    DownloadPresenter downloadPresenter;
    @BindView(R.id.downLoadList) RecyclerView downLoadList;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.pendlist) RecyclerView pendlistview;

    @BindView(R.id.layoutplay)RelativeLayout layoutdowndsongs;
    @BindView(R.id.txtdownloadsong)TextView textDownloadSongs;
    @BindView(R.id.txtPlayAll)TextView textPlayAll;
    @BindView(R.id.headinglayout)LinearLayout heading_layout;
    @BindView(R.id.main_layout)RelativeLayout mainLayout;
    MusicDataBase musicDataBase;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.mydownload, container, false);
        ButterKnife.bind(this, view1);
        initview(view1);
        LdPreferences.putString(activity, "back_status", "true");
        LdPreferences.putString(activity, "fragment", "download");
       // CSPreferences.putString(activity,"from","down");

        musicDataBase = new MusicDataBase(activity);
        ((Home) getActivity()).setToolBaricon();
        Log.e("Total size ", Util.getAvailableInternalMemorySize()+"\n "+ Util.getTotalInternalMemorySize());
        Home.FilesInFolder =  ((Home) getActivity()).GetFiles(getActivity().getApplicationContext().getExternalCacheDir());
        return view1;
    }

    private void initview(View view1){
        activity = getActivity();

        Home.FilesInFolder =  ((Home) getActivity()).GetFiles(getActivity().getApplicationContext().getExternalCacheDir());
    //    Toast.makeText(activity, "filesise"+Home.FilesInFolder.size(), Toast.LENGTH_SHORT).show();
        downloadPresenter = new DownloadPresenter(activity,this);
        downLoadList.setVisibility(View.VISIBLE);
        layoutdowndsongs.setVisibility(View.VISIBLE);

        downloadPresenter.getDownloadfromLocal(progressBar);
       /* boolean network = Utils.networkcheck(activity);
        if (network) {
           downloadPresenter.getDownloadList(progressBar);
        } else {
            downloadPresenter.getDownloadfromLocal(progressBar);

        }*/
     //   downloadPresenter.getDownloadfromLocal(progressBar);

       //downloadPresenter.getDownloadList(progressBar);

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showmessage(String message) {

        showmessage(message);

    }

    @Override
    public void setPlaysong() {
        LdPreferences.putString(activity,"from","down");
        String path =getContext().getExternalCacheDir()+"/"+LdPreferences.readString(activity,"play_path");
        //String path =getContext().getExternalCacheDir()+"/"+Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity,"postion")));
        ((Home) getActivity()).playSong(path);

    }



    @Override
    public void setAdapter(DownloadAdapter adapter) {
        downLoadList.setLayoutManager(new GridLayoutManager(activity,1));
        downLoadList.setAdapter(adapter);
    }
    @OnClick({R.id.txtPlayAll,R.id.txtdownloadsong})
    public void buttonClicks(View view) {
        switch (view.getId()) {
            case R.id.txtPlayAll:
               /* DownloadPresenter.locallist1 = (ArrayList<ContentDetail>) musicDataBase.getDownloads();
              //  Toast.makeText(activity, ""+DownloadPresenter.locallist1.size(), Toast.LENGTH_SHORT).show();
                CSPreferences.putString(activity,"from","down");
                Contsant.playlist= DownloadPresenter.locallist1;
                Contsant.queuestatus="true";
                ((Home) getActivity()).playAll();*/
                break;

            case R.id.txtdownloadsong:
                LdPreferences.putString(activity,"from","down");
                downLoadList.setVisibility(View.VISIBLE);
                layoutdowndsongs.setVisibility(View.VISIBLE);
                pendlistview.setVisibility(View.GONE);
                downloadPresenter.getDownloadfromLocal(progressBar);
                break;
        }
    }
    @Override
    public void downloadFile(String filepath, String name) {
        ((Home) getActivity()).downloadSongs(filepath,name);
    }




}




