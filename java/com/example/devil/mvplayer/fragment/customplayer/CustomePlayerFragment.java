package com.example.devil.mvplayer.fragment.customplayer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.Utils.Util;

/**
 * Created by whizkraft on 12/4/18.
 */

public class CustomePlayerFragment extends Fragment implements Customview, View.OnClickListener {
    ProgressDialog progressDialog;
    Activity activity;
    PlayerPresenter playerPresenter;
    TextView textDownalodsomg,textMore,textSongName,textArtistName,textCurrenttime,textdurationtime;
    ImageView imageTitle,imageshuffle,imagePrevious,imageNext,imagePlAyPAuse,imageRepeat;
    ProgressBar progressBar;
    SeekBar seekBar;
    RelativeLayout layoutMore,layoutDownload;
    boolean ismovingseekbar = false;
    long totalDuration, currentDuration;
    public Handler mHandler = new Handler();
    public static String previousstatus = "true";
    public static String nextstatus = "true";
    int songpostion;
    Dialog moreDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cutomeplayer, container, false);
        activity = getActivity();
        progressDialog = new ProgressDialog(getActivity());
        LdPreferences.putString(activity, "back_status", "true");
        LdPreferences.putString(activity, "fragment", "2");
        LdPreferences.putString(activity,"title_text","Player");
        ((Home) getActivity()).setToolBaricon();
        initview(layout);
        return layout;
    }

    private void initview(View layout) {
        Home.FilesInFolder =  ((Home) getActivity()).GetFiles(getContext().getExternalCacheDir());
        playerPresenter= new PlayerPresenter(activity,this);
        textDownalodsomg=(TextView)layout.findViewById(R.id.txtdownloadicon);
        textMore=(TextView)layout.findViewById(R.id.txtMore);

        textSongName=(TextView)layout.findViewById(R.id.txt_songname);
        textArtistName=(TextView)layout.findViewById(R.id.txt_artistname);
        textCurrenttime=(TextView)layout.findViewById(R.id.currenttime);
        progressBar=(ProgressBar) layout.findViewById(R.id.progressbar);
        textdurationtime=(TextView)layout.findViewById(R.id.duration_time);
        seekBar =(SeekBar)layout.findViewById(R.id.seekbar);
        imageshuffle =(ImageView)layout.findViewById(R.id.img_shuffle);
        imagePrevious =(ImageView)layout.findViewById(R.id.img_previous);
        imagePlAyPAuse =(ImageView)layout.findViewById(R.id.img_playpause);
        imageNext =(ImageView)layout.findViewById(R.id.img_next);
        imageRepeat =(ImageView)layout.findViewById(R.id.img_repeat);
        imageTitle =(ImageView)layout.findViewById(R.id.img_title);
        layoutMore =(RelativeLayout)layout.findViewById(R.id.more_layout) ;
        layoutDownload =(RelativeLayout)layout.findViewById(R.id.layout_download);

        imageNext.setOnClickListener(this);
        imagePlAyPAuse.setOnClickListener(this);
        imageRepeat.setOnClickListener(this);
        imagePrevious.setOnClickListener(this);
        imageshuffle.setOnClickListener(this);
        layoutMore.setOnClickListener(this);
        layoutDownload.setOnClickListener(this);
        textDownalodsomg.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e("onprogress changed", "");
                if (ismovingseekbar) {
                    int totalDuration = ((Home) getActivity()).mPlayer.getDuration();
                    int currentPosition = Util.progressToTimer(i, totalDuration);
                    ((Home) getActivity()).mPlayer.seekTo(currentPosition);
                    ((Home) getActivity()).updateProgressBar();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ismovingseekbar = true;



            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                ismovingseekbar = false;
                Log.e("onprogress stop", "");
            }
        });
        setDatainPlayer();


// This fuction used to play the Song ****************************************************






    }

    @Override
    public void downloadFile(String filepath, String name) {

        Log.e("djdjd","ddd");
        ((Home) getActivity()).downloadSongs(filepath,name);
    }

    private void setDatainPlayer() {

        int postion = Integer.parseInt(LdPreferences.readString(activity,"postion"));
        SubCategoryGetter subCategoryGetter = Constant.playlist.get(postion);
        textSongName.setText(""+subCategoryGetter.getSubname());
        textArtistName.setText(""+subCategoryGetter.getArtist());
        LdPreferences.putString(activity,"song_id",subCategoryGetter.getSongid());
        playSong(Config.songBaseurl+subCategoryGetter.getSongurl());
    }

    private void playSong(String s) {

        if (LdPreferences.readString(activity, "play_other").equals("false")) {
            if (LdPreferences.readString(activity, "play_status").equals("pause")) {
                imagePlAyPAuse.setImageResource(R.drawable.ic_icon_pause);
                totalDuration = ((Home) getActivity()).mPlayer.getDuration();
                currentDuration = ((Home) getActivity()).mPlayer.getCurrentPosition();
                String totalTimeduration = Util.milliSecondsToTimer(totalDuration);
                String currentdurationtime = Util.milliSecondsToTimer(currentDuration);
                textdurationtime.setText(totalDuration + "");
                int fisrtprogress = (int) (Util.getProgressPercentage(currentDuration, totalDuration));
                setprogress(totalTimeduration, currentdurationtime, fisrtprogress, ((Home) getActivity()).bufferedprogress);

            } else {
                //   Toast.makeText(activity, "play", Toast.LENGTH_SHORT).show();
                totalDuration = ((Home) getActivity()).mPlayer.getDuration();
                currentDuration = ((Home) getActivity()).mPlayer.getCurrentPosition();
                Log.e("totalDuration  ", totalDuration + "\n" + currentDuration);
                String totalTimeduration = Util.milliSecondsToTimer(totalDuration);
                String currentdurationtime = Util.milliSecondsToTimer(currentDuration);
                textdurationtime.setText(totalDuration + "");
                int fisrtprogress = (int) (Util.getProgressPercentage(currentDuration, totalDuration));
                setprogress(totalTimeduration, currentdurationtime, fisrtprogress, ((Home) getActivity()).bufferedprogress);
                imagePlAyPAuse.setImageResource(R.drawable.ic_icon_play);
                updateProgressBar();
            }

        } else {
            ((Home) getActivity()).playSong(s);
            imagePlAyPAuse.setImageResource(R.drawable.ic_icon_play);
            updateProgressBar();
            LdPreferences.putString(activity, "play_status", "play");
           // Home.playerstaus = "play";
        }
    }
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 1000);
        Log.e("aaa", "handler");
    }
    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            totalDuration = ((Home) getActivity()).mPlayer.getDuration();
            currentDuration = ((Home) getActivity()).mPlayer.getCurrentPosition();
            if (currentDuration>=1){
                progressBar.setVisibility(View.GONE);
                textdurationtime.setVisibility(View.VISIBLE);
                clickable();
            }
            // Displaying Total Duration time
            String totalTimeduration = Util.milliSecondsToTimer(totalDuration);
            // Displaying time completed playing
            String currentdurationtime = Util.milliSecondsToTimer(currentDuration);
            textdurationtime.setText(totalDuration + "");


            // Updating progress bar
            int fisrtprogress = (int) (Util.getProgressPercentage(currentDuration, totalDuration));
            ((Home) getActivity()).setCurrentTime(currentdurationtime+"");
            //Log.d("Progress", ""+progress);
            // secondProgress = (int)(Utils.progressToTimer(fisrtprogress, totalDuration));
            setprogress(totalTimeduration, currentdurationtime, fisrtprogress, ((Home) getActivity()).bufferedprogress);
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 1000);
        }

    };

    private void clickable(){
        imagePrevious.setClickable(true);
        imageNext.setClickable(true);
        imagePlAyPAuse.setClickable(true);
    }
    private void unClickable(){
        imagePrevious.setClickable(false);
        imageNext.setClickable(false);
        imagePlAyPAuse.setClickable(false);
    }

    public void setprogress(String totaltime, String cureenttime, int progress1, int progress2) {
        seekBar.setProgress(progress1);
        seekBar.setSecondaryProgress(progress2);
        textCurrenttime.setText(cureenttime);
        textdurationtime.setText(totaltime);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.txtdownloadicon:

                if (LdPreferences.readString(activity,"download_status").equals("finish")) {
                    playerPresenter.downloadDialog();
                }

                else {
                    Toast.makeText(activity, "Please wait downloading in progress", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_next:
                mHandler.removeCallbacks(mUpdateTimeTask);
                ((Home) getActivity()).mPlayer.pause();
                textdurationtime.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                songpostion = Integer.parseInt(LdPreferences.readString(activity, "postion"));
                playerPresenter.nextSong(songpostion, progressBar,textdurationtime);
                break;
            case R.id.img_previous:
                mHandler.removeCallbacks(mUpdateTimeTask);
                ((Home) getActivity()).mPlayer.pause();
                progressBar.setVisibility(View.VISIBLE);
                textdurationtime.setVisibility(View.GONE);
                songpostion = Integer.parseInt(LdPreferences.readString(activity, "postion"));
                playerPresenter.previoussong(songpostion, progressBar,textdurationtime);
                break;

            case R.id.img_shuffle:
                playerPresenter.shuffle(Constant.playlist, imageshuffle);
                break;

            case R.id.img_playpause:
                if (LdPreferences.readString(activity, "play_status").equals("play")) {
                   // ((Home) getActivity()).setNotificationicon();
                    LdPreferences.putString(activity, "play_status", "pause");
                    Home.playerstaus = "pause";
                    imagePlAyPAuse.setImageResource(R.drawable.ic_icon_pause);
                    ((Home) getActivity()).mPlayer.pause();

                } else {
                   // ((Home) getActivity()).setNotificationicon();
                    LdPreferences.putString(activity, "play_status", "play");
                    Home.playerstaus = "play";
                    imagePlAyPAuse.setImageResource(R.drawable.ic_icon_play);
                    ((Home) getActivity()).mPlayer.start();
                    updateProgressBar();
                    ((Home) getActivity()).updateProgressBar();
                }

                break;

            case R.id.more_layout:
                boolean network = Util.networkcheck(activity);
                if (network) {
                    moreDialog = Util.moreDialog(activity, R.layout.more_dialog);
                    playerPresenter.moreDialog(moreDialog);
                } else {
                    Toast.makeText(activity, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_repeat:
                playerPresenter.repeatSongStatus(imageRepeat);
                break;

        }
    }

    public void setdetail() {
      SubCategoryGetter  contentDetail = Constant.playlist.get(Integer.parseInt(LdPreferences.readString(activity, "postion")));
        Glide.with(activity).load(contentDetail.getImageurl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageTitle);

        textSongName.setText(contentDetail.getSubname());
        textArtistName.setText(contentDetail.getArtist());
      /*  if (LdPreferences.readString(activity,"from").equals("down")){
          *//*  LdPreferences.putString(activity,"ispurchased","1");
            setDownloadedtext1();*//*
        }
        else {
            if (contentDetail.getContentList().get(0).getIsPurchased()==1){
                CSPreferences.putString(activity,"ispurchased","1");
                setDownloadedtext1();
            }
            else {
                CSPreferences.putString(activity,"ispurchased","0");
                downldnowtext();
            }*/


        }



    @Override
    public void playNextSong(String filepath) {
        setdetail();
        ((Home) getActivity()).mPlayer.release();
        ((Home) getActivity()).mPlayer = new MediaPlayer();
        ((Home) getActivity()).playSong(Config.songBaseurl+filepath);
        LdPreferences.putString(activity, "play_status", "play");
        Home.playerstaus = "play";
        imagePlAyPAuse.setImageResource(R.drawable.ic_icon_play);
        updateProgressBar();

    }

    @Override
    public void visibilty() {
        imagePrevious.setClickable(true);
        imageNext.setClickable(true);
        imagePlAyPAuse.setClickable(true);
    }

    @Override
    public void unvisible() {
        imagePrevious.setClickable(false);
        imageNext.setClickable(false);
        imagePlAyPAuse.setClickable(false);
    }

    @Override
    public void noSongnextPre() {
        ((Home) getActivity()).mPlayer.start();
        totalDuration = ((Home) getActivity()).mPlayer.getDuration();
        currentDuration = ((Home) getActivity()).mPlayer.getCurrentPosition();
        Log.e("totalDuration  ", totalDuration + "\n" + currentDuration);
        String totalTimeduration = Util.milliSecondsToTimer(totalDuration);
        String currentdurationtime = Util.milliSecondsToTimer(currentDuration);
        textdurationtime.setText(totalDuration + "");
        int fisrtprogress = (int) (Util.getProgressPercentage(currentDuration, totalDuration));
        setprogress(totalTimeduration, currentdurationtime, fisrtprogress, ((Home) getActivity()).bufferedprogress);
        imagePlAyPAuse.setImageResource(R.drawable.ic_icon_play);
        updateProgressBar();

    }
}
