package com.example.devil.mvplayer.adapter;
import android.app.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.devil.mvplayer.Model.CategoryGetter;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.adapter.SongsAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

public class CustomCategoryAdapter extends RecyclerView.Adapter<CustomCategoryAdapter.MyViewHolder> {
    private Activity context;
    private LayoutInflater inflater;
    SongsAdapter adapter;
    RecyclerView recyclerView;

    private ArrayList<CategoryGetter> categorylist;
    LinearLayoutManager horizontalLayoutManager ;
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public CustomCategoryAdapter(Activity activity, ArrayList<CategoryGetter> categorylist,OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        super();
        this.context = activity;
        this.categorylist = categorylist;

        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public CustomCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_category_adapter, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomCategoryAdapter.MyViewHolder holder, final int position) {
       final CategoryGetter categoryGetter = categorylist.get(position);
        horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        adapter = new SongsAdapter(context,categoryGetter.getList(), new OnRecyclerViewItemClickListener() {

            @Override
            public void onRecyclerViewItemClicked(String view, int position) {
                LdPreferences.putString(context,"songid",view);
                LdPreferences.putString(context,"click","detail");
                onRecyclerViewItemClickListener.onRecyclerViewItemClicked(view,position);

            }
        });

    holder.recyclerView.setLayoutManager(horizontalLayoutManager);
        holder.recyclerView.setAdapter(adapter);
        holder.textCategory.setText(categoryGetter.getCatName());

        holder.textViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LdPreferences.putString(context,"CID",categoryGetter.getCatid());
                LdPreferences.putString(context,"click","seeall");
                LdPreferences.putString(context,"title_text",categoryGetter.getCatName());
                onRecyclerViewItemClickListener.onRecyclerViewItemClicked(categoryGetter.getCatid(),position);
            }
        });

   }
    @Override
    public int getItemCount() {
        return categorylist.size();

    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView textViewAll;
        private TextView textCategory;
        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerView =(RecyclerView) itemView.findViewById(R.id.SongsListview);

            textViewAll =(TextView) itemView.findViewById(R.id.textViewAll);
            textCategory =(TextView)itemView.findViewById(R.id.txtCategoryName);
            textViewAll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {



                    }
            });
        }
    }
}