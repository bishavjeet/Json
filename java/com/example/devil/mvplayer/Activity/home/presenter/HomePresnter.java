package com.example.devil.mvplayer.Activity.home.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.devil.mvplayer.Activity.home.view.Homeview;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.database.MusicDataBase;
import com.example.devil.mvplayer.fragment.AllSongFrag;
import com.example.devil.mvplayer.fragment.HomeFrag;

import com.example.devil.mvplayer.fragment.ProfileFrag;
import com.example.devil.mvplayer.fragment.SearchFragment;
import com.example.devil.mvplayer.fragment.Setting.SettingFragment;
import com.example.devil.mvplayer.fragment.customplayer.CustomePlayerFragment;
import com.example.devil.mvplayer.fragment.downloads.MyDownload;
import com.example.devil.mvplayer.fragment.playlist.Playlist;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by whizkraft on 30/4/18.
 */

public class HomePresnter implements IHome {
    Activity activity;
    Homeview homeview;
    ProgressDialog progressDialog;
    File aioDir;
    MusicDataBase musicDataBase;
    ArrayList<SubCategoryGetter>downloadlist;
    public HomePresnter(Activity activity, Homeview homeview) {
        this.activity = activity;
        this.homeview = homeview;
        progressDialog = new ProgressDialog(activity);
        musicDataBase = new MusicDataBase(activity);
    }

    @Override
    public void replacewithMainFrament(Fragment fragment) {
        fragment = new HomeFrag();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void replacewithPlayer(Fragment fragment) {
        fragment = new CustomePlayerFragment();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void replaceContent(Fragment fragment) {
        fragment = new AllSongFrag();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void serachview(final MaterialSearchView materialSearchView) {
        materialSearchView.showSearch();
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                materialSearchView.closeSearch();
                LdPreferences.putString(activity,"search_text",query);
                LdPreferences.putString(activity,"title_text",query);
                replacewithSearchFragment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void replacewithSearchFragment() {
        Fragment fragment = new SearchFragment();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void replacewithplaylist(Fragment fragment) {
        fragment = new Playlist();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void replaceWithProfile(Fragment fragment) {
        fragment = new ProfileFrag();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void downloadFilesfromServer(String filepath, String name) {
        Log.e("pppp","ppp");

        Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            Log.e("is presented","");
            aioDir = new File(activity.getExternalCacheDir()+ "/"+name+""+".mp4"+"/");
            Log.e("path",Config.baseurl+filepath);
            new DownloadFileFromURL().execute(Config.baseurl+filepath);
        } else {
            Log.e("Not Presented","");
            aioDir = new File(activity.getCacheDir(), name+".mp4");
            Log.e("path",Config.baseurl+filepath);
            new DownloadFileFromURL().execute(Config.baseurl+filepath);
        }
    }

    @Override
    public void replacewithSetting(Fragment fragment) {
        fragment = new SettingFragment();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void replaceqwithDownload(Fragment fragment) {
        fragment = new MyDownload();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void replacewithPlaylist(Fragment fragment) {
        fragment = new Playlist();
        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            Log.e("cccc","ss");

           // progressbar.setVisibility(View.VISIBLE);

        }
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                Log.e("length of file ", lenghtOfFile+"");

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream(aioDir);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
           // progressbar.setProgress(Integer.parseInt(progress[0]));
            progressDialog.setTitle(progress[0]);
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
          //  progressbar.setVisibility(View.GONE);

            progressDialog.dismiss();
            LdPreferences.putString(activity,"download_status","finish");
            switch (LdPreferences.readString(activity,"from_down")){
                case "player":
                    addToSongLocaDataBase(Constant.playlist);
                    break;
              /*  case "content":
                    addToSongLocaDataBase(ContentPresenter.finallist);
                    if (CSPreferences.readString(activity,"from").equals("content")){
                        replacewithContentFragment();
                    }

                    break;
                case "search_text":
                    Log.e("jdhjd","ff");
                    addToSongLocaDataBase(SearchPresenter.finallist);
                    if (CSPreferences.readString(activity,"from").equals("search_text")){
                        replacewithSearchFragment();
                    }

                    break;
                case "down":
                    addToSongLocaDataBase(DownloadPresenter.downlist);
                    if (CSPreferences.readString(activity,"from").equals("down")){
                        replacewithDownload(new MyDownload());
                    }
                    break;*/

            }


        }

    }

    private void addToSongLocaDataBase(ArrayList<SubCategoryGetter> playlist) {
        SubCategoryGetter contentDetail = playlist.get(Integer.parseInt(LdPreferences.readString(activity,"download_postion")));
        boolean insert= musicDataBase.addtodownload(contentDetail.getSongid(),contentDetail.getSubname(),contentDetail.getArtist(),
                contentDetail.getSongurl(), Config.imageurl+contentDetail.getImageurl());
        if (insert){
            downloadlist = new ArrayList<>();
            downloadlist = (ArrayList<SubCategoryGetter>) musicDataBase.getDownloads();
            Constant.downloadList = new ArrayList<>();
            for (int i =0;i<downloadlist.size();i++){

                SubCategoryGetter contentDetail1 = downloadlist.get(i);
                Constant.downloadList.add(contentDetail.getSongid());
            }

          /*  if (LdPreferences.readString(activity,"fragment").equalsIgnoreCase("download")){

                replacewithDownload(new Fragment());
            }*/
            // Toast.makeText(activity, ""+contentDetail.getContentList().get(0).getContentType(), Toast.LENGTH_SHORT).show();

            //Toast.makeText(activity, "insert", Toast.LENGTH_SHORT).show();

        }
        else {

            // Toast.makeText(activity, "not", Toast.LENGTH_SHORT).show();
        }

    }

}
