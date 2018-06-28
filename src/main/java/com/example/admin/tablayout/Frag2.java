package com.example.admin.tablayout;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Frag2 extends Fragment implements MediaPlayer.OnCompletionListener {

    VideoView videoView;



    MediaMetadataRetriever mediaMetadataRetriever;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_frag2, container, false);

        videoView = view.findViewById(R.id.videoView);
        // final AssetFileDescriptor afd=getResources().openRawResourceFd(R.raw.cloud);
        //mediaMetadataRetriever.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());

        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.cloud;
        //int [] path=new int[]{R.raw.cloud,R.raw.hell};
        videoView.setVideoURI(Uri.parse(path));

        MediaController mc = new MediaController(getActivity());
        mc.setAnchorView(this.videoView);
        mc.setMediaPlayer(this.videoView);
        videoView.requestFocus();
        videoView.setMediaController(mc);
        videoView.pause();
        mc.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle previous click here
            }
        });
        videoView.setMediaController(mc);
      return view;

    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }
}