package com.example.devil.mvplayer.Model;


import java.util.ArrayList;


/**
 * Created by Devil on 3/26/2018.
 */

public class CategoryGetter {
    public  String catid;
    String catName;
    ArrayList<SubCategoryGetter>list;

    public CategoryGetter(String catid,String catName,ArrayList<SubCategoryGetter>list){
        this.catid = catid;
        this.catName = catName;
        this.list = list;
    }

    public String getCatid() {
        return catid;
    }

    public String getCatName() {
        return catName;
    }

    public ArrayList<SubCategoryGetter> getList() {
        return list;
    }
}
