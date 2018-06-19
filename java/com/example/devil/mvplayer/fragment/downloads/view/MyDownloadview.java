package com.example.devil.mvplayer.fragment.downloads.view;

import com.example.devil.mvplayer.adapter.DownloadAdapter;


/**
 * Created by whizkraft on 5/12/17.
 */

public interface MyDownloadview {
    public void showProgress();
    public void hideProgress();
    public void showmessage(String message);
    public void setAdapter(DownloadAdapter adapter);
    public void setPlaysong();

    public void downloadFile(String filepath, String name);



}
