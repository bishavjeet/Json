package com.example.devil.mvplayer.fragment.playlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.adapter.GetPlayLIstAdapter;
import com.example.devil.mvplayer.fragment.playlist.presenter.Playlistpresenter;
import com.example.devil.mvplayer.fragment.playlist.view.Playlistview;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;



/**
 * Created by whizkraft on 11/12/17.
 */

public class Playlist extends Fragment implements Playlistview {
    Activity activity;
     Playlistpresenter playlistpresenter;
     ProgressDialog progressDialog;
    @BindView(R.id.createPlayList) TextView createnewPlaylist;
    @BindView(R.id.playlist) RecyclerView playlist;
    @BindView(R.id.txtnodata) TextView txtNoData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.playlistfragment, container, false);
        ButterKnife.bind(this, view1);
        activity = getActivity();
        LdPreferences.putString(activity, "back_status", "true");
        LdPreferences.putString(activity, "fragment", "7");
        LdPreferences.putString(activity,"title_text","Playlist");
        ((Home) getActivity()).setToolBaricon();
        initview(view1);
        return view1;
    }

    private void initview(View view1) {
        playlistpresenter = new Playlistpresenter(activity,this);
        playlistpresenter.getPlaylist();

    }

   /* @Override
    public void setPlayAdapter(AddPlayadapter playAdapter) {
        playlist.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
        playlist.setLayoutManager(new GridLayoutManager(activity,1));
        playlist.setAdapter(playAdapter);
    }
*/

    @Override
    public void setPlayAdapter(GetPlayLIstAdapter getPlayLIstAdapter) {
        playlist.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
        playlist.setLayoutManager(new GridLayoutManager(activity,1));
        playlist.setAdapter(getPlayLIstAdapter);
    }

    @Override
    public void noData(String message) {
        txtNoData.setVisibility(View.VISIBLE);
        txtNoData.setText(message);
    }

    @OnClick({R.id.createPlayList})
    public void buttonClicks(View view) {
        switch (view.getId()) {
            case R.id.createPlayList:
                playlistpresenter.createPlaylist();
                break;


        }
    }

    @Override
    public void hideProgress() {
       dismissDialog();
    }

    @Override
    public void showmessage(String message) {
        showToast(message);
    }

    private void showToast(String message) {
        Toast.makeText(activity, ""+message, Toast.LENGTH_SHORT).show();
    }


    public void showProgress(){
        progressDialog.show();
    }

    public void dismissDialog(){
        progressDialog.dismiss();
    }

}
