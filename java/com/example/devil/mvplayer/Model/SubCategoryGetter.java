package com.example.devil.mvplayer.Model;

/**
 * Created by Devil on 3/26/2018.
 */

public class SubCategoryGetter {  String subid,subname,songurl,releasedate,artist,status, imageurl,songid;


    public SubCategoryGetter(String subid,String subname,String songurl,String releasedate,String artist,String status,String imageurl,String songid){
        this.subid = subid;
        this.subname = subname;
        this.songurl = songurl;
        this.releasedate= releasedate;
        this.artist =artist;
        this.status = status;
        this.imageurl = imageurl;
        this.songid=songid;
    }

    public String getSubname() {
        return subname;
    }

    public String getSubid() {
        return subid;
    }

    public String getSongurl() {
        return songurl;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public String getArtist() {
        return artist;
    }

    public String getStatus() {
        return status;
    }

    public String getImageurl() {
        return imageurl;
    }
    public String getSongid() {
        return songid;
    }
}



