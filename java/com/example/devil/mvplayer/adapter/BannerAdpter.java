package com.example.devil.mvplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.devil.mvplayer.R;

import java.util.ArrayList;

/**
 * Created by whizkraft on 12/4/18.
 */

public class BannerAdpter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<String>bannerlist;
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    public BannerAdpter(Activity activity, ArrayList<String> bannerlist,OnRecyclerViewItemClickListener catInterface) {
        this.activity = activity;
        this.bannerlist = bannerlist;
        this.onRecyclerViewItemClickListener = catInterface;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banneradapzter, container, false);
        ImageView bannerimge = (ImageView) view.findViewById(R.id.imgbanner);
        bannerimge.setImageResource(R.mipmap.demo);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /*     if (bannerBean.getDetails().get(position).getClickevent().equals("show-data")) {
                    if (bannerBean.getDetails().get(position).getNt().equals("n")) {
                        CSPreferences.putString(activity,"title_text",bannerBean.getDetails().get(position).getItemname());
                        catInterface.onBottomReached(Integer.parseInt(bannerBean.getDetails().get(position).getCid()),"category");
                    } else {
                        Log.e("lllll","");
                        CSPreferences.putString(activity,"title_text",bannerBean.getDetails().get(position).getItemname());
                        catInterface.onBottomReached(Integer.parseInt(bannerBean.getDetails().get(position).getCid()),"content");

                    }
                }
                else {
                    CSPreferences.putString(activity,"title_text",bannerBean.getDetails().get(position).getItemname());
                    catInterface.onBottomReached(position,"https://discoverflow.co/jamaica/");
                }*/

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return bannerlist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}



