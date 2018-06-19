package com.example.devil.mvplayer.Model;

import java.util.ArrayList;

/**
 * Created by whizkraft on 2/5/18.
 */

public class PlaylistBean {

    String playlistname, playlistid,playlistcount;
    ArrayList<SubCategoryGetter>list;

    public PlaylistBean(String playlistname, String playlistid,ArrayList<SubCategoryGetter>list,String playlistcount) {
        this.playlistname = playlistname;
        this.playlistid = playlistid;
        this.list=list;
        this.playlistcount = playlistcount;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public String getPlaylistid() {
        return playlistid;
    }

    public ArrayList<SubCategoryGetter> getList() {
        return list;
    }

    public String getPlaylistcount() {
        return playlistcount;
    }
}
