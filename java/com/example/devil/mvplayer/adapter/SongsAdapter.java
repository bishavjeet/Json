package com.example.devil.mvplayer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;

import java.util.ArrayList;

/**
 * Created by Devil on 3/24/2018<.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<SubCategoryGetter> categorylist;
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public SongsAdapter(Activity context, ArrayList<SubCategoryGetter> categorylist, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.context = context;
        this.categorylist = categorylist;
        this.onRecyclerViewItemClickListener =onRecyclerViewItemClickListener;
    }

    @Override
    public SongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_songs, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongsAdapter.MyViewHolder holder, final int position) {
       final SubCategoryGetter categoryGetter = categorylist.get(position);


       holder.title.setText(" ."+categoryGetter.getSubname());
       holder.img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LdPreferences.putString(context,"songid",categoryGetter.getSubid());
               LdPreferences.putString(context,"click","play");
               LdPreferences.putString(context, "play_other", "true");
               Constant.rember_status = "false";
               Constant.playlist = categorylist;
               LdPreferences.putString(context,"postion",position+"");
               onRecyclerViewItemClickListener.onRecyclerViewItemClicked(categoryGetter.getSubid(),position);
           }
       });
    }
    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

       public TextView title;
       private ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.txt_songname);
            img= (ImageView) itemView.findViewById(R.id.img);
        }
    }
}