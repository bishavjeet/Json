package com.example.devil.mvplayer.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by whizkraft on 12/12/17.
 */

public class ListDetailAdapter extends RecyclerView.Adapter<ListDetailAdapter.ViewHolder> {
    Activity activity;
    ArrayList<SubCategoryGetter> list;

    OnRecyclerViewItemClickListener catInterface;

    View view;

    public ListDetailAdapter(ArrayList<SubCategoryGetter> contentDetail, Activity activity, OnRecyclerViewItemClickListener catInterface) {
        this.list = contentDetail;
        this.activity = activity;
        this.catInterface = catInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdetailadapter, null);
        ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SubCategoryGetter contentDetail = list.get(position);
        Glide.with(activity).load(Config.imageurl + contentDetail.getImageurl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.text_songname.setText(contentDetail.getSubname());
        holder.text_artistname.setText(contentDetail.getArtist());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Constant.playlist = list;
                LdPreferences.putString(activity, "play_other", "true");
                Constant.rember_status = "false";

                LdPreferences.putString(activity, "postion", position + "");
                LdPreferences.putString(activity, "click", "view");
                catInterface.onRecyclerViewItemClicked("view", position);


            }
        });
        holder.selectsong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                  /*  LdPreferences.putString(activity, "click", "select");
                    catInterface.onRecyclerViewItemClicked("", position);*/

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage("Are you sure to delte this sonbg from the playlist.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Constant.playlist = list;
                                    LdPreferences.putString(activity, "click", "select");
                                    catInterface.onRecyclerViewItemClicked("", position);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    holder.selectsong.setChecked(false);
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {

                    /*LdPreferences.putString(activity, "click", "unselect");
                    catInterface.onRecyclerViewItemClicked("", position);*/

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_songtitle)
        TextView text_songname;
        @BindView(R.id.txt_artistname)
        TextView text_artistname;
        @BindView(R.id.img_title)
        ImageView imageView;
        @BindView(R.id.checkfordelete)
        CheckBox selectsong;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}



