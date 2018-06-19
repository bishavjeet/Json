package com.example.devil.mvplayer.Activity.home;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.devil.mvplayer.Activity.Login;
import com.example.devil.mvplayer.Activity.home.presenter.HomePresnter;
import com.example.devil.mvplayer.Activity.home.view.Homeview;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.Utils.Util;
import com.example.devil.mvplayer.fragment.AllSongFrag;
import com.example.devil.mvplayer.fragment.FirstFrag;
import com.example.devil.mvplayer.fragment.HomeFrag;
import com.example.devil.mvplayer.R;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.devil.mvplayer.R.id.contentFrame;

import com.example.devil.mvplayer.fragment.ProfileFrag;
import com.example.devil.mvplayer.fragment.SecondFrag;
import com.example.devil.mvplayer.fragment.Setting.SettingFragment;
import com.example.devil.mvplayer.fragment.customplayer.CustomePlayerFragment;
import com.example.devil.mvplayer.fragment.downloads.MyDownload;
import com.example.devil.mvplayer.fragment.playlist.Playlist;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Home extends AppCompatActivity implements View.OnClickListener,Homeview {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ListView listView;
    String[] array;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public String totalTimeduration, currentdurationtime;
    public int fisrtprogress, secondProgress, bufferedprogress;
    TextView textSongname,textArtistname,textNosong,textTotalDurstaion;
    public Handler mHandler = new Handler();
    Activity activity;
    MaterialSearchView materialSearchView;
    public static String playerstaus = "play";
    RelativeLayout search_layout, setting_layout, slider_layout, navigate_layout, back_layout;
    RelativeLayout toollayout;
    ImageView image_title;
    TextView text_title;
    public static ArrayList<String> FilesInFolder = new ArrayList<String>();
    RelativeLayout player_layout;
    ImageView imagePlayPause,imageSongName;
    HomePresnter homePresnter;


    public static MediaPlayer mPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = this;
        initview();


    }
    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mPlayer.isPlaying()) {
                long totalDuration = mPlayer.getDuration();
                long currentDuration = mPlayer.getCurrentPosition();

                // Displaying Total Duration time
                totalTimeduration = Util.milliSecondsToTimer(totalDuration);
                // Displaying time completed playing
                currentdurationtime = Util.milliSecondsToTimer(currentDuration);
                textTotalDurstaion.setText(currentdurationtime + "");

                // Updating progress bar
                fisrtprogress = (int) (Util.getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);
                // secondProgress = (int)(Utils.progressToTimer(fisrtprogress, totalDuration));
                //playerfragment.setprogress(totalTimeduration, currentdurationtime, fisrtprogress, bufferedprogress);

                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 1000);
            }
        }
    };
    public void setCurrentTime(String currenttime) {

        textTotalDurstaion.setText(currenttime);
    }


    public void setToolBaricon() {
        visibiltyBack();
    }

    //****************************Set thevisibilty of tool bar*********************
    private void visibiltyBack() {
        Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
        if (LdPreferences.readString(activity, "fragment").equals("2")||LdPreferences.readString(activity,"fragment").equalsIgnoreCase("video")) {
            player_layout.setVisibility(View.GONE);
        }
        if (LdPreferences.readString(activity, "fragment").equals("3")||LdPreferences.readString(activity,"fragment").equals("search")) {
            search_layout.setVisibility(View.VISIBLE);
        } else {
            search_layout.setVisibility(View.GONE);
        }
        navigate_layout.setVisibility(View.GONE);
        image_title.setVisibility(View.GONE);
        text_title.setVisibility(View.VISIBLE);
        back_layout.setVisibility(View.VISIBLE);
        text_title.setText(LdPreferences.readString(activity, "title_text"));



    }
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 1000);
    }

    private void initview() {
        materialSearchView=(MaterialSearchView)findViewById(R.id.search_view);
        LdPreferences.putString(activity,"download_status","finish");

        homePresnter = new HomePresnter(activity,this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.navigationDrawerListView);
        player_layout =(RelativeLayout)findViewById(R.id.layout_player);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);

        LayoutInflater mInflater = LayoutInflater.from(activity);
        View mCustomView = mInflater.inflate(R.layout.toolbar, null);

        search_layout = (RelativeLayout) mCustomView.findViewById(R.id.layout_search);
        toollayout =(RelativeLayout)mCustomView.findViewById(R.id.toolbar);
        setting_layout = (RelativeLayout) mCustomView.findViewById(R.id.layout_setting);
        //   slider_layout = (RelativeLayout) mCustomView.findViewById(R.id.layout_slider);
        navigate_layout = (RelativeLayout) mCustomView.findViewById(R.id.layout_navigation);
        back_layout = (RelativeLayout) mCustomView.findViewById(R.id.layout_back);
        text_title = (TextView) mCustomView.findViewById(R.id.txt_titletext);
        image_title = (ImageView) mCustomView.findViewById(R.id.img_titile);

        toolbar.addView(mCustomView);
       setSupportActionBar(toolbar);

        setting_layout.setOnClickListener(this);
        search_layout.setOnClickListener(this);
        navigate_layout.setOnClickListener(this);
        back_layout.setOnClickListener(this);
        player_layout.setOnClickListener(this);


        imageSongName =(ImageView)findViewById(R.id.img_song);
        imagePlayPause =(ImageView)findViewById(R.id.img_playpause);
        textSongname=(TextView)findViewById(R.id.txt_songname);
        textArtistname=(TextView)findViewById(R.id.txt_artistname);
        textNosong=(TextView)findViewById(R.id.txt_nosong);
        textTotalDurstaion=(TextView)findViewById(R.id.txt_totaltime);
        //Drawer items
        array = new String[]{"Home", "Profile", "Playlist", "Downloads", "Settings", "Logout"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(adapter);

     /*   actionBarDrawerToggle = new ActionBarDrawerToggle(Home.this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
*/
     /*   actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        // Setting ActionBarDrawerToggle on DrawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);*/

    /*    // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Enabling Home button();
        getSupportActionBar().setHomeButtonEnabled(true);*/
        loadHomeFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectItem(position);
                drawerLayout.closeDrawer(listView);

            }
        });
    }

    public void downloadSongs(String filepath, String name) {
        Log.e("nnnn","nnn");
        LdPreferences.putString(activity, "download_status", "running");
        homePresnter.downloadFilesfromServer(filepath, name);
    }

    public ArrayList<String> GetFiles(File DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(String.valueOf(DirectoryPath));
        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return MyFiles;
        else {
            for (int i = 0; i < files.length; i++)

                MyFiles.add(files[i].getName());

        }
        return MyFiles;
    }

    //This fuction used to play the Song *****************************************
    public void playSong(String songIndex) {
//        playerfragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        Log.e("songIndex", songIndex + "");
        try {
            mPlayer.reset();

                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(songIndex);

             mPlayer.prepare();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();
                    if (mPlayer.isPlaying()) {
                        updateProgressBar();
                        setBottom();

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }

        mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

                bufferedprogress = i;
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (Constant.rember_status.equals("true")) {
                   /* if (CSPreferences.readString(activity,"songfrom").equalsIgnoreCase("main")){
                        Contsant.playlist = Contsant.queuelist;
                        CSPreferences.putString(activity,"postion","0");
                    }
                    else if (CSPreferences.readString(activity,"songfrom").equalsIgnoreCase("content")){
                        Contsant.playlist = Contsant.queuelist;
                        CSPreferences.putString(activity,"postion","0");
                    }*/
                    int pos = Integer.parseInt(LdPreferences.readString(activity, "postion"));
                    if (pos<Constant.playlist.size()-1) {
                        pos = pos + 1;
                        LdPreferences.putString(activity, "postion", pos + "");
                        repeatsong();
                    }
                    else {
                        pos =0;
                        LdPreferences.putString(activity, "postion", pos + "");
                        repeatsong();
                    }
                }
                else {

                    // Play  the Queue Song

                }
            }
        });
    }


    // This fuction used to play a specific Song****************

    public void repeatsong() {

        int pos = Integer.parseInt(LdPreferences.readString(activity, "postion"));
        if (Constant.playlist.size() > 0 && pos < Constant.playlist.size()) {

                if (Constant.playlist.size() - 1 == pos) {
                    //Toast.makeText(activity, "00", Toast.LENGTH_SHORT).show();
                    pos = 0;
                    LdPreferences.putString(activity, "postion", pos + "");
                    SubCategoryGetter contentDetail = Constant.playlist.get(pos);
                    playSong(Config.songBaseurl+contentDetail.getSongurl());

                } else if (pos == 0) {
                    //   Toast.makeText(activity, "00", Toast.LENGTH_SHORT).show();
                    SubCategoryGetter contentDetail = Constant.playlist.get(pos);
                    playSong(Config.songBaseurl+contentDetail.getSongurl());
                } else {
                    pos = pos + 1;
                    LdPreferences.putString(activity, "postion", pos + "");
                    SubCategoryGetter contentDetail = Constant.playlist.get(pos);
                    playSong(Config.songBaseurl+contentDetail.getSongurl());
                }

        } else {

            Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show();
        }
    }



    // Set the Content oon the Bottom player********************************
    private void setBottom() {
        if (Constant.playlist.size() > 0) {
            textSongname.setVisibility(View.VISIBLE);
            textArtistname.setVisibility(View.VISIBLE);
            textNosong.setVisibility(View.GONE);
            SubCategoryGetter contentDetail = Constant.playlist.get(Integer.parseInt(LdPreferences.readString(activity, "postion")));
            textSongname.setText(contentDetail.getSubname());
            textArtistname.setText(contentDetail.getArtist());
            if (LdPreferences.readString(activity, "play_status").equals("pause")) {

                imagePlayPause.setImageResource(R.drawable.ic_icon_pause);

            } else {
                imagePlayPause.setImageResource(R.drawable.ic_icon_play);
            }
            Glide.with(activity).load(contentDetail.getImageurl())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageSongName);

        } else {



                imagePlayPause.setImageResource(R.drawable.ic_icon_pause);
                textNosong.setVisibility(View.VISIBLE);
                textTotalDurstaion.setText("00:00");
                textSongname.setVisibility(View.GONE);
                textArtistname.setVisibility(View.GONE);
                textNosong.setText(activity.getResources().getString(R.string.no_request));
                imageSongName.setImageResource(R.mipmap.demo);

        }


    }


    private void loadHomeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.contentFrame, new HomeFrag());

        ft.commit();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItem(int pos) {
        String item = array[pos];
        //Set Title
        // setTitle(item);
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Toast.makeText(Home.this, item + " Selected ", Toast.LENGTH_LONG).show();
        switch (pos) {
            case 0:
                fragment = new HomeFrag();
                fragmentTransaction.replace(contentFrame, fragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragment = new ProfileFrag();
                fragmentTransaction.replace(contentFrame, fragment);
                fragmentTransaction.commit();
                break;
            case 2:
               homePresnter.replacewithplaylist(new Playlist());
                break;

            case 4:
                homePresnter.replacewithSetting(new SettingFragment());
                break;

            case 3:
                homePresnter.replaceqwithDownload(new MyDownload());
                break;

            case 5:
                LdPreferences.putString(activity,"login_status","false");
                Intent in = new Intent(activity, Login.class);
                startActivity(in);
                finish();

        }
    }

// Check  the Back Button of  Androide Phone .....
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (LdPreferences.readString(activity, "fragment").equals("1")) {
                moveTaskToBack(true);
                return true;
            } else if (LdPreferences.readString(activity, "fragment").equals("8")) {
                //homePresenter.replacewithPlaylist(fragment);
            } else if (LdPreferences.readString(activity, "fragment").equals("5")) {
               // visiblityviewBottomlayout();
              //  homePresenter.replaceSetting(fragment);
            } else if (LdPreferences.readString(activity, "fragment").equals("10")) {
                //visiblityviewBottomlayout();
                //homePresenter.replaceSetting(fragment);
            } else if (LdPreferences.readString(activity, "fragment").equals("2")) {
                if (LdPreferences.readString(activity, "from").equals("content")) {
                    LdPreferences.putString(activity, "title_text", LdPreferences.readString(activity, "back_title"));
                  //  homePresenter.replaceContent(fragment);
                   // visiblityviewBottomlayout();
                } else if (LdPreferences.readString(activity, "from").equals("down")) {
                  //  homePresenter.replacewithDownload(fragment);
                  //  visiblityviewBottomlayout();
                } else {
                    boolean network = Util.networkcheck(activity);
                    if (network) {
                       // visibleNavigation();
                       // homePresenter.replacewithMainFrament(fragment);
                    } else {

                      //  homePresenter.replacewithDownload(fragment);
                       // visiblityviewBottomlayout();
                    }

                }
            } else if (LdPreferences.readString(activity, "fragment").equals("download")) {
                boolean network = Util.networkcheck(activity);
                if (network) {
                  //  visibleNavigation();
                  //  homePresenter.replacewithMainFrament(fragment);
                } else {

                   // (activity.getResources().getString(R.string.checking_interneterror));
                }
            } else {
                boolean network = Util.networkcheck(activity);
                if (network) {
                 //   visibleNavigation();
                    //homePresenter.replacewithMainFrament(fragment);
                } else {
                   // homePresenter.replacewithDownload(fragment);
                }




          /*  else if (CSPreferences.readString(activity, "fragment").equals("8")) {
                homePresenter.replacewithPlaylist(fragment);
            } else if (CSPreferences.readString(activity, "fragment").equals("5")) {
                visiblityviewBottomlayout();
                homePresenter.replaceSetting(fragment);
            } else if (CSPreferences.readString(activity, "fragment").equals("10")) {
                visiblityviewBottomlayout();
                homePresenter.replaceSetting(fragment);
            }
            }*/


            }


        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.layout_search:
                    homePresnter.serachview(materialSearchView);
                break;
            case R.id.layout_navigation:
                Toast.makeText(activity, "dsdd", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(Gravity.LEFT);
               //homePresenter.drawelLayout(drawerLayout);
                break;
            case R.id.layout_back:
                if (LdPreferences.readString(activity, "fragment").equals("8")) {
                    homePresnter.replacewithPlaylist(new Playlist());
                } else if (LdPreferences.readString(activity, "fragment").equals("5")) {
                    //visiblityviewBottomlayout();
                   // homePresenter.replaceSetting(fragment);
                } else if (LdPreferences.readString(activity, "fragment").equals("10")) {
                    //visiblityviewBottomlayout();
                    //homePresenter.replaceSetting(fragment);
                } else if (LdPreferences.readString(activity, "fragment").equals("2")) {
                    if (LdPreferences.readString(activity, "from").equals("content")) {
                        LdPreferences.putString(activity,"title_text",LdPreferences.readString(activity,"back_title"));
                       homePresnter.replaceContent(new AllSongFrag());
                        visiblityviewBottomlayout();
                    }
                    else if (LdPreferences.readString(activity,"from").equals("down")){
                       // homePresenter.replacewithDownload(fragment);
                      //  visiblityviewBottomlayout();
                    }
                    else {
                        boolean network = Util.networkcheck(activity);
                        if (network) {
                            visibleNavigation();
                            homePresnter.replacewithMainFrament(new HomeFrag());
                        } else {
                            // homePresenter.replacewithDownload(fragment);
                           // visiblityviewBottomlayout();
                        }

                    }

                } else if (LdPreferences.readString(activity, "fragment").equals("download")) {
                    boolean network = Util.networkcheck(activity);
                    if (network) {
                        //visibleNavigation();
                     //   homePresenter.replacewithMainFrament(fragment);
                    } else {

                        Toast.makeText(activity, "Your internet connection is off Please trun on your Coneection", Toast.LENGTH_SHORT).show();
                       // showToast("Your internet connection is off Please trun on your Coneection");
                    }
                }
                else if (LdPreferences.readString(activity,"fragment").equalsIgnoreCase("video")){
                    if (LdPreferences.readString(activity, "from").equals("content")) {
                        LdPreferences.putString(activity,"title_text",LdPreferences.readString(activity,"back_title"));
                       // homePresenter.replaceContent(fragment);
                    }
                    else if (LdPreferences.readString(activity,"from").equals("down")){
                       // homePresenter.replacewithDownload(fragment);
                        // visiblityviewBottomlayout();
                    }
                    else if (LdPreferences.readString(activity,"from").equalsIgnoreCase("playlist")){
                       // homePresenter.replacewithPlaylist(fragment);
                    }
                    else {
                      //  visibleNavigation();
                        //homePresenter.replacewithMainFrament(fragment);
                    }
                }
                else {
                    boolean network = Util.networkcheck(activity);
                    if (network) {
                        visibleNavigation();
                        homePresnter.replacewithMainFrament(new HomeFrag());
                    } else {
                       // homePresenter.replacewithDownload(fragment);
                    }
                }
                break;

            case R.id.layout_player:
                if (Constant.playlist.size() > 0) {
                    LdPreferences.putString(activity, "play_other", "false");
                    player_layout.setVisibility(View.GONE);
                    LdPreferences.putString(activity, "title_text", "Player");
                    homePresnter.replacewithPlayer(new CustomePlayerFragment());
                } else {

                    Toast.makeText(activity, "There  is no song in the list", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.img_playpause:
                boolean status = Util.networkcheck(activity);
                if (status && !LdPreferences.readString(activity, "fargment").equals("down")) {
                    if (Constant.playlist.size() > 0) {
                        if (LdPreferences.readString(activity, "play_status").equals("play")) {
                            //setNotificationicon();
                            LdPreferences.putString(activity, "play_status", "pause");
                            playerstaus = "pause";
                            imagePlayPause.setImageResource(R.drawable.ic_icon_pause);
                            mPlayer.pause();
                        } else {
                            //setNotificationicon();
                            playerstaus = "play";
                            LdPreferences.putString(activity, "play_status", "play");
                            imagePlayPause.setImageResource(R.drawable.ic_icon_play);
                            mPlayer.start();
                            updateProgressBar();
                        }
                    } else {

                            Toast.makeText(activity, "No song selected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity,"", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
    public void playAll() {
        LdPreferences.putString(activity, "postion", "0");
        LdPreferences.putString(activity, "play_status", "play");
        Constant.rember_status = "true";
        repeatsong();
    }

    private void visibleNavigation() {
        if (LdPreferences.readString(activity, "fragment").equals("2")||LdPreferences.readString(activity,"fragment").equalsIgnoreCase("video")
                ||LdPreferences.readString(activity,"fragment").equalsIgnoreCase("3")) {
            player_layout.setVisibility(View.VISIBLE);
            setBottom();
        }
        search_layout.setVisibility(View.VISIBLE);
        navigate_layout.setVisibility(View.VISIBLE);
        image_title.setVisibility(View.VISIBLE);
        back_layout.setVisibility(View.GONE);
        text_title.setVisibility(View.GONE);
    }

    public void visibilityGoneBottomlayout() {
        player_layout.setVisibility(View.GONE);
    }

    public void visiblityviewBottomlayout() {
        player_layout.setVisibility(View.VISIBLE);
    }
}
