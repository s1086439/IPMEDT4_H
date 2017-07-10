package com.example.ipmedt41617.ipmedt4_h.Activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ipmedt41617.ipmedt4_h.DatabaseInfo;
import com.example.ipmedt41617.ipmedt4_h.Models.Bluetooth;
import com.example.ipmedt41617.ipmedt4_h.BluetoothConnectie;
import com.example.ipmedt41617.ipmedt4_h.DatabaseHelper;
import com.example.ipmedt41617.ipmedt4_h.Models.Oefening;
import com.example.ipmedt41617.ipmedt4_h.Models.Stap;
import com.example.ipmedt41617.ipmedt4_h.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OefeningActivity extends BluetoothConnectie {

    private VideoView videoView;

    private TextView stappenText, oefeningText, btText, detecterenBewegenGif, bewegenText, registrerenText;
    private Button volgendeButton, vorigeButton;
    private View indicator;
    private int huidigeOefening, huidigeStap;
    private LinearLayout l2;
    private MediaPlayer mp;
    private ArrayList<Integer> initStappenWaardenArray;
    private ObjectAnimator indicatorAnimator;
    private DatabaseHelper dbHelper;
    private Bluetooth bluetooth;
    private Oefening oefening;
    private ArrayList<Stap> stappenList;
    private Bundle intentExtras;
    private Stap stap;
    private Thread thread;
    private int pitch;
    private Activity act;
    private boolean stopThread;
    private byte buffer[];
    private SeekBar progressie;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefening);


        this.act = this;

        dbHelper = DatabaseHelper.getHelper(this);

        intentExtras = getIntent().getExtras();

        huidigeStap = 0;

        oefening = (Oefening) getIntent().getSerializableExtra("Oefening");

        stappenList = dbHelper.querySqliteStappen("SELECT * FROM STAPPEN WHERE oefeningType=" + oefening.getType());

        stap = stappenList.get(huidigeStap);

        vorigeButton = (Button)findViewById(R.id.vorigeButton);
        volgendeButton = (Button)findViewById(R.id.volgendeButton);
        videoView = (VideoView)findViewById(R.id.video);
        stappenText = (TextView)findViewById(R.id.stappen);
        oefeningText = (TextView)findViewById(R.id.oefeningText);
        frame = (FrameLayout)findViewById(R.id.frame);

        registrerenText = (TextView)findViewById(R.id.registerernText);
        progressie = (SeekBar) findViewById(R.id.seekBar);

        progressie.setMax(stappenList.size()-1);
        progressie.setProgress(huidigeStap);

        //bewegenText = (TextView)findViewById(R.id.bewegenText);
        detecterenBewegenGif = (TextView)findViewById(R.id.detecterenBewegenGif);
        btText = (TextView)findViewById(R.id.bt);
        l2 = (LinearLayout)findViewById(R.id.linearLayout2);

        oefeningText.setText("Oefening " + oefening.getNaam());
        stappenText.setText("Stap " + stap.getStapNummer() + "/" + stappenList.size() + ": " + stap.getOmschrijving());

        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        vorigeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                vorigeStap();
            }
        });

        volgendeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                volgendeStap();
            }
        });

        speelVideoOefening();

        progressie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        if(super.connectenBluetooth()){
           if(super.opzettenBluetoothStream()){
               bluetooth = super.bluetooth;
               //opvangenBluetoothData();
           }
        }
    }

    private void volgendeStap(){
        if(huidigeStap < stappenList.size() - 1) {
            huidigeStap++;
            stap = stappenList.get(huidigeStap);
            progressie.setProgress(huidigeStap);
            oefeningText.setText("Oefening " + oefening.getNaam());
            stappenText.setText("Stap " + stap.getStapNummer() + "/" + stappenList.size() + ": " + stap.getOmschrijving());
            mp = MediaPlayer.create(this, R.raw.stap_voltooid);
            mp.start();
            speelVideoOefening();
        } else {
            stopThread = true;
            dbHelper.updateQuery("OEFENINGEN", "voltooid", 1, oefening.getId());
            volgendeButton.setVisibility(VideoView.INVISIBLE);
            vorigeButton.setVisibility(VideoView.INVISIBLE);
            detecterenBewegenGif.setVisibility(ImageView.INVISIBLE);
            registrerenText.setVisibility(TextView.INVISIBLE);
            oefeningText.setVisibility(TextView.INVISIBLE);
            frame.setVisibility(VideoView.GONE);
            progressie.setVisibility(SeekBar.INVISIBLE);

            mp = MediaPlayer.create(this, R.raw.oefening_voltooid);
            mp.start();

            stappenText.setText("Oefening " + oefening.getNaam().toLowerCase() + " voltooid!");


            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("tab", oefening.getWeek());
                    startActivity(intent);
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.oefening_voltooid_2);
                    mp.start();
                    finish();
                    //act.finish();
                }
            }, 4000);

            //this.finish();
        }
    }

    private void vorigeStap(){
        if(huidigeStap > 0) {
            huidigeStap--;
            stap = stappenList.get(huidigeStap);
            progressie.setProgress(huidigeStap);
            oefeningText.setText("Oefening " + oefening.getNaam());
            stappenText.setText("Stap " + stap.getStapNummer() + "/" + stappenList.size() + ": " + stap.getOmschrijving());
            speelVideoOefening();
        }
    }

    // Een functie om video's af te spelen.

    private void speelVideoOefening(){
        int rawId = getResources().getIdentifier(stap.getVideoNaam(), "raw", getPackageName());
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+rawId);
        this.videoView.setVideoURI(uri);
        this.videoView.start();
    }

    private void opvangenBluetoothData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = bluetooth.getInputStream().available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            pitch = bluetooth.getInputStream().read();
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    if(pitch < stap.getBluetoothdoelwaarde() - 8 || pitch > stap.getBluetoothdoelwaarde() + 8){
                                        volgendeStap(); // volgende stap als de doelwaarde tussen -8 en +8 ligt
                                    }

                                }

                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

}
