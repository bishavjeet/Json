package com.example.devil.mvplayer.fragment.listdetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.adapter.ListDetailAdapter;
import com.example.devil.mvplayer.fragment.listdetail.presenter.ListDetailPresenter;
import com.example.devil.mvplayer.fragment.listdetail.view.ListDetailview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by whizkraft on 12/12/17.
 */

public class ListDetail  extends Fragment implements ListDetailview {
    Activity activity;
    ListDetailPresenter listDetailPresenter;
    @BindView(R.id.listdetail) RecyclerView listdetail;
    @BindView(R.id.txtPlayAll) TextView textPlayAll;
    @BindView(R.id.txtDeletePlaylist) TextView textDeletePlaylist;
    @BindView(R.id.headinglayout)LinearLayout headinglayout;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.listdetail, container, false);
        ButterKnife.bind(this, view1);
        activity = getActivity();
        LdPreferences.putString(activity, "back_status", "true");
        LdPreferences.putString(activity, "fragment", "8");
        ((Home) getActivity()).setToolBaricon();
        initview(view1);
        return view1;
    }

    private void initview(View view1) {

        changeColor();

        listDetailPresenter = new ListDetailPresenter(activity,this);
        listDetailPresenter.getlistDetail();
    }

    @Override
    public void showProgress() {
        showProgress1();

    }

    @Override
    public void setListAdapter(ListDetailAdapter listAdapter) {
        listdetail.setLayoutManager(new GridLayoutManager(activity,1));
        listdetail.setAdapter(listAdapter);
    }

    @Override
    public void newsongs(String path) {
        LdPreferences.putString(activity,"from","playlist");
        LdPreferences.putString(activity,"play_status","play");
        ((Home) getActivity()).playSong(Config.songBaseurl+path);
    }
    public void showProgress1(){
        progressDialog.show();
    }

    public void dismissDialog(){
        progressDialog.dismiss();
    }

    @Override
    public void hideProgress() {
     dismissDialog();
    }

    @Override
    public void showmessage(String message) {
      //  showToast(message);

    }
    @OnClick({R.id.txtDeletePlaylist,R.id.txtPlayAll})
    public void buttonClicks(View view) {
        switch (view.getId()) {
            case R.id.txtDeletePlaylist:
                listDetailPresenter.deletePlayList();
                break;
            case R.id.txtPlayAll:
                LdPreferences.putString(activity,"from","playlist");
                Constant.rember_status = "true";
                ((Home) getActivity()).playAll();
                break;
        }
    }

    public void changeColor(){
       /* try {
            File f=new File(CSPreferences.readString(activity,"imagepath"), "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Drawable drawable = new BitmapDrawable(getResources(), b);
            mainLayout.setBackgroundDrawable(drawable);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }*/





    }

}
