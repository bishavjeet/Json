package com.example.devil.mvplayer.fragment.listdetail.view;


import com.example.devil.mvplayer.adapter.ListDetailAdapter;

/**
 * Created by whizkraft on 12/12/17.
 */

public interface ListDetailview {
    public void showProgress();

    public void hideProgress();

    public void showmessage(String message);

    public void setListAdapter(ListDetailAdapter listAdapter);
    public void newsongs(String path);

}
