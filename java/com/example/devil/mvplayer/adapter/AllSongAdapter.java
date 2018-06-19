package com.example.devil.mvplayer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;

import java.util.ArrayList;


public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.MyViewHolder> {
    private ArrayList<SubCategoryGetter> categorylist;
    Activity activity;
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    public AllSongAdapter(Activity activity,ArrayList<SubCategoryGetter>categorylist,OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){


        this.activity=activity;
        this.categorylist=categorylist;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener ;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_all_songs, null);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AllSongAdapter.MyViewHolder holder, final int position) {
        final SubCategoryGetter categoryGetter = categorylist.get(position);



        holder.textSongName.setText(categoryGetter.getSubname());
        holder.textArtistname.setText(categoryGetter.getArtist());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LdPreferences.putString(activity, "click", "view");
                Constant.rember_status = "false";
                LdPreferences.putString(activity,"first","true");
                LdPreferences.putString(activity, "from", "content");
                LdPreferences.putString(activity, "play_other", "true");
                LdPreferences.putString(activity, "title_text", "Player");
                onRecyclerViewItemClickListener.onRecyclerViewItemClicked("view",position);


            }
        });

        holder.layoutDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* CSPreferences.putString(activity,"ispurchased", String.valueOf(contentDetail.getContentList().get(0).getIsPurchased()));
                //  Toast.makeText(activity, ""+String.valueOf(contentDetail.getContentList().get(0).getIsPurchased()), Toast.LENGTH_SHORT).show();
                CSPreferences.putString(activity,"click","download");
                catInterface.onCatgoryClick(position);*/

            }
        });
        holder.layoutmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LdPreferences.putString(activity,"click","more");
                onRecyclerViewItemClickListener.onRecyclerViewItemClicked("view",position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textSongName,textArtistname;
        private ImageView img;
        RelativeLayout layoutmore,layoutDownload,mainLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            textSongName =(TextView)itemView.findViewById(R.id.txt_songtitle);
            textArtistname =(TextView)itemView.findViewById(R.id.txt_artistname);
            layoutmore=(RelativeLayout)itemView.findViewById(R.id.layout_more);
            layoutDownload =(RelativeLayout)itemView.findViewById(R.id.layout_download);
            mainLayout =(RelativeLayout)itemView.findViewById(R.id.layoutmain);



           /* title=(TextView)itemView.findViewById(R.id.textViewTitle);
            artist= (TextView) itemView.findViewById(R.id.textViewArtist);*/
        }


    }
}
