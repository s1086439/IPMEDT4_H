package com.example.ipmedt41617.ipmedt4_h;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ipmedt41617.ipmedt4_h.Models.Oefening;

import static android.R.attr.button;

public class OefeningActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView stappen, oefening;
    private View dot;
    private int huidigeStap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefening);

        this.huidigeStap = 0;

        // Oefening oefening1 = new Oefening(3, "Been omhoog", "Doe je been omhoog.");

        Button vorigeButton = (Button)findViewById(R.id.vorigeButton);
        Button volgendeButton = (Button)findViewById(R.id.volgendeButton);

        dot = (View)findViewById(R.id.dot);

        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.dots);


        this.videoView = (VideoView)findViewById(R.id.video);

        stappen = (TextView)findViewById(R.id.stappen);
        oefening = (TextView)findViewById(R.id.oefening);

        oefening.setText(getIntent().getStringExtra("oefening"));

        stappen.setText("Stap 1");

        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        speelVideoOefening(1, 1);

        vorigeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                speelVideoOefening(1, 1);
                stappen.setText("Stap 1");

            }
        });

        volgendeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                speelVideoOefening(1, 2);
                stappen.setText("Stap 2");

            }
        });
    }

    private void speelVideoOefening(int oefening, int stap){
        String video = String.format("oefening_%d_%d", oefening, stap);
        int rawId = getResources().getIdentifier(video, "raw", getPackageName());
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+rawId);
        this.videoView.setVideoURI(uri);
        this.videoView.start();
    }

    private void toevoegenDots(){

    }
}
