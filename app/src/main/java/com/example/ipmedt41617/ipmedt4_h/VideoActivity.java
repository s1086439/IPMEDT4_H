package com.example.ipmedt41617.ipmedt4_h;

/**
 * Created by jimmyvanviersen on 07/06/2017.
 */

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.ipmedt41617.ipmedt4_h.R;

public class VideoActivity extends AppCompatActivity {
    Button clk;
    VideoView videov;
    MediaController mediaC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        clk = (Button) findViewById(R.id.btnVideo);
        videov = (VideoView) findViewById(R.id.videoView);
        mediaC = new MediaController(this);
    }

    public void videoPlay(View v) {
        String videoPath = "/Users/jimmyvanviersen/AndroidStudioProjects/IPMEDT4_H/app/src/main/res/raw/fysio.mp4";
        Uri uri = Uri.parse(videoPath);
        videov.setVideoURI(uri);
        videov.setMediaController(mediaC);
        mediaC.setAnchorView(videov);
        videov.start();
    }
}
