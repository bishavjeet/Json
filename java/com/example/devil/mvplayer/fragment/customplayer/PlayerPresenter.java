package com.example.devil.mvplayer.fragment.customplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.PlaylistBean;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;
import com.example.devil.mvplayer.Utils.Util;
import com.example.devil.mvplayer.adapter.GetPlayLIstAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by whizkraft on 12/4/18.
 */

public class PlayerPresenter implements ICustomeplayer {

    Activity activity;
    Customview customview;
    ArrayList<PlaylistBean>playlistsongs;

    public PlayerPresenter(Activity activity, Customview customview) {

        this.activity = activity;
        this.customview = customview;
    }


    @Override
    public void downloadDialog() {
        final SubCategoryGetter contentDetail = Constant.playlist.get(Integer.parseInt(LdPreferences.readString(activity, "postion")));
        String amount = "Rs 15";
        final String file = contentDetail.getSongurl();


        if (Home.FilesInFolder.size() > 0) {

            if (Home.FilesInFolder.contains(contentDetail.getSongid() + ".mp4")) {
                Log.e("sizeeeee ", "contains");
                Toast.makeText(activity, "Already download", Toast.LENGTH_SHORT).show();
            } else {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setMessage("Click  to  pay " + amount + " to download");
                alertDialogBuilder.setPositiveButton("Pay",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //Log.e("itemid ,", "" + contentDetail.getItemid() + "\n" + contentDetail.getItemname());

                                LdPreferences.putString(activity, "from_down", "player");
                                Log.e("sizeeeee ", "Not contain");
                                LdPreferences.putString(activity, "download_postion", LdPreferences.readString(activity, "postion"));
                                customview.downloadFile(file, contentDetail.getSongid());

                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    }
                });
                alertDialog.show();
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setMessage("Click  to  pay " + amount + " to download");
            alertDialogBuilder.setPositiveButton("Pay",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Log.e("itemid ,", "" + contentDetail.getItemid() + "\n" + contentDetail.getItemname());

                            LdPreferences.putString(activity, "from_down", "player");
                            Log.e("sizeeeee ", "Not contain");
                            LdPreferences.putString(activity, "download_postion", LdPreferences.readString(activity, "postion"));
                            customview.downloadFile(file,contentDetail.getSongid());

                        }
                    });

            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });
            alertDialog.show();
        }


      //  Log.e("purchadsed ",  CSPreferences.readString(activity,"ispurchased"));
      /*  if (contentDetail.getContentList().get(0).getIsPurchased()==1){
            if (Contsant.downloadList.contains(contentDetail.getItemid())){

                playerview.showmessage(activity.getResources().getString(R.string.alreadDownload));
            }
            else {
                if (Home.FilesInFolder.size() > 0) {

                    if (Home.FilesInFolder.contains(contentDetail.getItemid() + ".mp4")) {
                        Log.e("sizeeeee ", "contains");
                        playerview.showmessage(activity.getResources().getString(R.string.alreadDownload));
                    } else {
                        CSPreferences.putString(activity, "from_down", "player");
                        Log.e("sizeeeee ", "Not contain");
                        CSPreferences.putString(activity, "download_postion", CSPreferences.readString(activity, "postion"));
                        playerview.downloadFile(file, contentDetail.getItemid());
                    }
                } else {
                    CSPreferences.putString(activity, "from_down", "player");
                    CSPreferences.putString(activity, "download_postion", CSPreferences.readString(activity, "postion"));
                    playerview.downloadFile(file, contentDetail.getItemid());
                }
            }
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setMessage("Click  to  pay " + amount + " to download");
            alertDialogBuilder.setPositiveButton("Pay",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Log.e("itemid ,", "" + contentDetail.getItemid() + "\n" + contentDetail.getItemname());
                            purchaseParms.setAppid(Contsant.Appmode);
                            purchaseParms.setSrvid(Contsant.servid);
                            purchaseParms.setInterface(Contsant.Interface);
                            purchaseParms.setTid(Contsant.Tid);
                            purchaseParms.setUid(CSPreferences.readString(activity, "token_id"));
                            purchaseParms.setType("event");
                            purchaseParms.setLangId(1);
                            purchaseParms.setService("mod");
                            purchaseParms.setItemId(contentDetail.getItemid());
                            purchaseParms.setItemName(contentDetail.getItemname());
                            purchaseParms.setFuid("null");
                            purchaseParms.setSubType(Contsant.subType);
                            purchaseParms.setCountryCode(Contsant.ContryCode);
                            String file = contentDetail.getContentList().get(0).getFilePath();
                            purchaseParms.setContentType(contentDetail.getContentList().get(0).getContentType());
                            arg0.dismiss();
                            hitPurchaseService(purchaseParms, file, contentDetail.getItemid());
                        }
                    });

            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });
            alertDialog.show();
        }*/
    }

    @Override
    public void previoussong(int postion, ProgressBar progressBar, TextView txtTotaltime) {

        if (postion >= 0) {
            if (postion == 0) {
                if (Constant.rember_status.equalsIgnoreCase("true")) {
                    postion = Constant.playlist.size() - 1;
                    CustomePlayerFragment.previousstatus = "true";
                    SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                    LdPreferences.putString(activity, "postion", postion + "");
                    if (LdPreferences.readString(activity, "from").equals("down")) {
                       /* String path =activity.getExternalCacheDir()+"/"+ Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity,"postion")));
                        playerview.playNextSong(path);*/
                    } else {
                        customview.playNextSong(contentDetail.getSongurl());
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    txtTotaltime.setVisibility(View.VISIBLE);
                    customview.visibilty();
                    Toast.makeText(activity, "There is no song in the previous", Toast.LENGTH_SHORT).show();
                    customview.noSongnextPre();
                }
            } else {
                CustomePlayerFragment.previousstatus = "true";
                postion = postion - 1;
                SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                LdPreferences.putString(activity, "postion", postion + "");
                if (LdPreferences.readString(activity, "from").equals("down")) {
                   /* String path =activity.getExternalCacheDir()+"/"+Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity,"postion")));
                    playerview.playNextSong(path);*/
                } else {
                    customview.playNextSong(contentDetail.getSongurl());
                }


            }
        } else {
            if (Constant.rember_status.equalsIgnoreCase("true")) {
                postion = Constant.playlist.size() - 1;
                CustomePlayerFragment.previousstatus = "true";
                SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                LdPreferences.putString(activity, "postion", postion + "");
                if (LdPreferences.readString(activity, "from").equals("down")) {
                   /* String path = activity.getExternalCacheDir() + "/" + Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity, "postion")));
                    playerview.playNextSong(path);*/
                } else {
                    //  playerview.playNextSong(contentDetail.getContentList().get(0).getFilePath());
                }
            } else {
                progressBar.setVisibility(View.GONE);
                txtTotaltime.setVisibility(View.VISIBLE);
                customview.visibilty();
                Toast.makeText(activity, "There is no song in the previous", Toast.LENGTH_SHORT).show();
                customview.noSongnextPre();
            }

        }

    }

    @Override
    public void nextSong(int postion, ProgressBar progressBar, TextView txtTotaltime) {
        if (postion < Constant.playlist.size()) {
            int ss = Constant.playlist.size() - 1;
            if (postion == ss) {
                if (Constant.rember_status.equalsIgnoreCase("true")) {
                    CustomePlayerFragment.nextstatus = "true";
                    postion = 0;
                    SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                    LdPreferences.putString(activity, "postion", postion + "");
                    if (LdPreferences.readString(activity, "from").equals("down")) {
                        // String path = activity.getExternalCacheDir() + "/" + Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity, "postion")));
                        //playerview.playNextSong(path);
                    } else {
                        customview.playNextSong(contentDetail.getSongurl());
                    }
                } else {


                    progressBar.setVisibility(View.GONE);
                    txtTotaltime.setVisibility(View.VISIBLE);
                    customview.visibilty();
                    customview.noSongnextPre();
                    Toast.makeText(activity, "There is no song in the next", Toast.LENGTH_SHORT).show();
                }

            } else {
                CustomePlayerFragment.nextstatus = "true";
                postion = postion + 1;
                SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                LdPreferences.putString(activity, "postion", postion + "");
                if (LdPreferences.readString(activity, "from").equals("down")) {
                    //String path =activity.getExternalCacheDir()+"/"+Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity,"postion")));
                    // playerview.playNextSong(path);
                } else {
                    customview.playNextSong(contentDetail.getSongurl());
                }
            }

        } else {
            if (Constant.rember_status.equalsIgnoreCase("true")) {
                CustomePlayerFragment.nextstatus = "true";
                postion = 0;
                SubCategoryGetter contentDetail = Constant.playlist.get(postion);
                LdPreferences.putString(activity, "postion", postion + "");
                if (LdPreferences.readString(activity, "from").equals("down")) {
                    // String path =activity.getExternalCacheDir()+"/"+Home.FilesInFolder.get(Integer.parseInt(CSPreferences.readString(activity,"postion")));
                    //  playerview.playNextSong(path);
                } else {
                    // playerview.playNextSong(contentDetail.getContentList().get(0).getFilePath());
                }

            } else {
                progressBar.setVisibility(View.GONE);
                txtTotaltime.setVisibility(View.VISIBLE);
                customview.visibilty();
                Toast.makeText(activity, "There is no song in teh next", Toast.LENGTH_SHORT).show();
                customview.noSongnextPre();
            }
        }

    }

    @Override
    public void shuffle(ArrayList<SubCategoryGetter> list, ImageView image_shuffle) {
        if (Constant.shuffle_status.equals("false")) {
            Constant.shuffle_status = "true";
            image_shuffle.setImageResource(R.drawable.ic_icon_suffel);
            Collections.reverse(Constant.playlist);

        } else {
            Constant.shuffle_status = "false";
            image_shuffle.setImageResource(R.drawable.ic_icon_shuffle);
        }
    }

    @Override
    public void repeatSongStatus(ImageView image_repeat) {
        if (LdPreferences.readString(activity, "repeat_status").equals("false")) {
            LdPreferences.putString(activity, "repeat_status", "true");
            Constant.rember_status = "true";
            image_repeat.setImageResource(R.drawable.ic_icon_repet);
        } else {
            LdPreferences.putString(activity, "repeat_status", "false");
            Constant.rember_status = "false";
            image_repeat.setImageResource(R.drawable.ic_icon_whitrepet);
        }

    }

    @Override
    public void moreDialog(final Dialog moredialog) {
        moredialog.show();

        TextView txtCancel = (TextView) moredialog.findViewById(R.id.cancel);
        TextView addToPlayList = (TextView) moredialog.findViewById(R.id.addtoPlaylist);
        TextView createPlaylist = (TextView) moredialog.findViewById(R.id.createPlaylistt);

        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moredialog.dismiss();
                createPlaylistDialog();


            }
        });
        addToPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moredialog.dismiss();
                String getplaylisturl = Config.getplaylisturl + "&UserId=" + LdPreferences.readString(activity,"userId");

                new HitGetPlaylist().execute(getplaylisturl);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moredialog.dismiss();
            }
        });


    }

    private void showplaylistname(ArrayList<PlaylistBean> playlists) {
        BottomSheetDialog bottomSheetDialog = Util.createBottomSheetDialog(activity, R.layout.bottomplaylist);
        RecyclerView recyclviewplaylist = (RecyclerView) bottomSheetDialog.findViewById(R.id.playlist);
        final ProgressBar progressBar = (ProgressBar) bottomSheetDialog.findViewById(R.id.progressbar);
        bottomSheetDialog.show();


        setPlayListAdapter(playlists, recyclviewplaylist, bottomSheetDialog);
    }


    private void setPlayListAdapter(final ArrayList<PlaylistBean> playlists, RecyclerView recyclerViewplaylist, final BottomSheetDialog bottomSheetDialog) {
      GetPlayLIstAdapter getPlayLIstAdapter = new GetPlayLIstAdapter(activity, playlists, new OnRecyclerViewItemClickListener() {
          @Override
          public void onRecyclerViewItemClicked(String view, int postion) {

              String addplaylisturl =Config.addtoPlaylist+"&UserId="+LdPreferences.readString(activity,"userId")+"&playlistId="+view+"&songId="+LdPreferences.readString(activity,"song_id");

              Log.e("addtoplaylisturl ",addplaylisturl);

              new HitAddtoplaylist().execute(addplaylisturl);
              bottomSheetDialog.dismiss();
          }


        });
        recyclerViewplaylist.setLayoutManager(new GridLayoutManager(activity, 1));
        recyclerViewplaylist.setAdapter(getPlayLIstAdapter);


    }


    private void createPlaylistDialog() {
        final BottomSheetDialog createbottomdialog = Util.createBottomSheetDialog(activity, R.layout.createplaylistbottomlayout);
        createbottomdialog.show();
        RelativeLayout headinglayout;
        LinearLayout buttonlayoutback;
        final TextView createPlaylist = (TextView) createbottomdialog.findViewById(R.id.createPlaylistt);
        final EditText playlistName = (EditText) createbottomdialog.findViewById(R.id.edt_playlistname);
        TextView cancel = (TextView) createbottomdialog.findViewById(R.id.cancelPlaylist);
        headinglayout = (RelativeLayout) createbottomdialog.findViewById(R.id.headinglayout);
        buttonlayoutback = (LinearLayout) createbottomdialog.findViewById(R.id.buttonbacklayout);


        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (playlistName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, "Required", Toast.LENGTH_SHORT).show();
                } else {

                    //String createplaylisturl = Config.createplaylisturl+"&uId="+LdPreferences.readString(activity,"user_id")+"&playlistname="+playlistName.getText().toString();

                    String createplaylisturl = Config.createplaylisturl + "&uId=" + LdPreferences.readString(activity,"userId") + "&playlistname=" + playlistName.getText().toString();

                    new HitCreatePlaylist().execute(createplaylisturl);
                    createbottomdialog.dismiss();

                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createbottomdialog.dismiss();

            }
        });

    }

    public class HitCreatePlaylist extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("createplaylist ", "--" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if (code.equalsIgnoreCase("1")) {

                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, "Please check your internet coneection", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class HitGetPlaylist extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("createplaylist ", "--" + response);
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    int playlistcount;
                    ArrayList<SubCategoryGetter>songplaylist;
                    playlistsongs = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            songplaylist = new ArrayList<>();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String playlistid = jsonObject1.getString("playlistId");
                            String playlistname = jsonObject1.getString("playlist_name");
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("playlists");
                            if (jsonArray1.length() > 0) {

                                playlistcount = jsonArray1.length();

                                for (int j = 0; j < jsonArray1.length(); j++) {

                                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                    String id = jsonObject2.getString("id");
                                    String title = jsonObject2.getString("song_name");
                                    String songurl = jsonObject2.getString("song_url");
                                    String relaesdate = "4-4-2017";
                                    String artist = "Abc";
                                    String status = "0";
                                    String imageurl = jsonObject2.getString("song_image");
                                    String songid = jsonObject2.getString("songId");
                                    SubCategoryGetter subCategoryGetter = new SubCategoryGetter(id,title,songurl,relaesdate,artist,status,imageurl,songid);
                                    songplaylist.add(subCategoryGetter);


                                }
                            } else {

                                playlistcount = 0;

                            }
                            PlaylistBean playlistBean = new PlaylistBean(playlistname,playlistid,songplaylist,playlistcount+"");
                            playlistsongs .add(playlistBean);




                        }
                        Constant.favlist = playlistsongs;
                        showplaylistname(playlistsongs);


                    } else {

                        Toast.makeText(activity, "There is no playlist please create Playlist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, "Please check your internet coneection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class HitAddtoplaylist extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("addto playlist ", "--" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if (code.equalsIgnoreCase("1")) {

                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, "Please check your internet coneection", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
