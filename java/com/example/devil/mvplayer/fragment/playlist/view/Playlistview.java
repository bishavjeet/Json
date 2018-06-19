package com.example.devil.mvplayer.fragment.playlist.view;


import com.example.devil.mvplayer.adapter.GetPlayLIstAdapter;

/**
 * Created by whizkraft on 11/12/17.
 */

public interface Playlistview {

    public void showProgress();

    public void hideProgress();

    public void showmessage(String message);
   public void setPlayAdapter(GetPlayLIstAdapter getPlayLIstAdapter);
    public void noData(String message);
}
