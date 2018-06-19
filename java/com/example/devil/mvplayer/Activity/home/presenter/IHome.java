package com.example.devil.mvplayer.Activity.home.presenter;

import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by whizkraft on 30/4/18.
 */

public interface IHome {
    public void replacewithMainFrament(Fragment fragment);
    public void replacewithPlayer(Fragment fragment);
    public void replaceContent(Fragment fragment);
    public void replacewithplaylist(Fragment fragment);
    public void replacewithPlaylist(Fragment fragment);
    public void downloadFilesfromServer(String filepath,String name);
    public void replacewithSetting(Fragment fragment);
    public void replaceqwithDownload(Fragment fragment);
    public void replaceWithProfile(Fragment fragment);
        public void serachview(MaterialSearchView materialSearchView);
}

