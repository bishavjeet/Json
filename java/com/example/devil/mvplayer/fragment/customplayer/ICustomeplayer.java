package com.example.devil.mvplayer.fragment.customplayer;

import android.app.Dialog;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.devil.mvplayer.Model.SubCategoryGetter;

import java.util.ArrayList;

/**
 * Created by whizkraft on 12/4/18.
 */

public interface ICustomeplayer {
    public void previoussong(int postion, ProgressBar progressBar, TextView txtTotaltime);
    public void nextSong(int postion,ProgressBar progressBar,TextView txtTotaltime);
    public void shuffle(ArrayList<SubCategoryGetter> list, ImageView image_shuffle);

    public void repeatSongStatus(ImageView image_repeat);

    public void  moreDialog(Dialog moredialog);
    public void  downloadDialog();

}
