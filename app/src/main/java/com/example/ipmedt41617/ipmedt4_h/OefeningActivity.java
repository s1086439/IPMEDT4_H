package com.example.ipmedt41617.ipmedt4_h;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ipmedt41617.ipmedt4_h.Models.Oefening;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.R.attr.button;
import static com.example.ipmedt41617.ipmedt4_h.R.id.bewegenText;
import static com.example.ipmedt41617.ipmedt4_h.R.id.oefening;
import static com.example.ipmedt41617.ipmedt4_h.R.id.textView;

public class OefeningActivity extends AppCompatActivity {

    private VideoView videoView;
    private Oefening oefening;
    private TextView stappenText, oefeningText, btText, detecterenBewegenGif, bewegenText;
    private View dot;
    private int huidigeOefening, huidigeStap;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    boolean deviceConnected=false;
    private LinearLayout l2;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    private MediaPlayer mp;
    private int pitch, gereedTeller, tmp;
    private ArrayList<Integer> initStappenWaardenArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefening);

        initStappenWaardenArray = new ArrayList<>();

        huidigeStap = 0;

        Button vorigeButton = (Button)findViewById(R.id.vorigeButton);
        final Button volgendeButton = (Button)findViewById(R.id.volgendeButton);
        this.videoView = (VideoView)findViewById(R.id.video);
        stappenText = (TextView)findViewById(R.id.stappen);
        oefeningText = (TextView)findViewById(R.id.oefening);
        bewegenText = (TextView)findViewById(R.id.bewegenText);
        detecterenBewegenGif = (TextView)findViewById(R.id.detecterenBewegenGif);
        btText = (TextView)findViewById(R.id.bt);
        l2 = (LinearLayout)findViewById(R.id.linearLayout2);

        // Type oefening verkrijgen van het scherm met de oefeningen d.m.v. een klik.

        oefening = new Oefening(getIntent().getStringExtra("naam"), getIntent().getStringExtra("omschrijving"));

        // Aanmaken oefeningen.

        oefening.toevoegenStap("1. Plaats uw been schuin", "oefening_1_1", 0, 50);
        oefening.toevoegenStap("2. Beweeg uw been omhoog", "oefening_1_2", 0, 20);
        oefening.toevoegenStap("3. Beweeg uw been omlaag", "oefening_1_3", 0, 60);

        oefeningText.setText(oefening.getNaamOefening());

        stappenText.setText(oefening.getStapAtIndex(huidigeStap).getOmschrijving());

        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        speelVideoOefening();

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

        OpzettenBluetooth();
        ConnectenBluetooth();
        opvangenBluetoothData();

    }

    // Een functie om video's af te spelen.

    private void speelVideoOefening(){
        String video = oefening.getStapAtIndex(huidigeStap).getNaamVideo();
        int rawId = getResources().getIdentifier(video, "raw", getPackageName());
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+rawId);
        this.videoView.setVideoURI(uri);
        this.videoView.start();
    }

    /*  Controleer of Bluetooth aanwezig en is ingeschakeld, Zo ja, koppel het toestel met MAC-adres
        van de HC-05. Zo nee, vraag om Bluetooth in te schakelen. */

    public boolean OpzettenBluetooth()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Apparaat ondersteunt geen Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Koppel eerst apparaten",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                // MAC-Adres verkrijgen
                if(iterator.getAddress().equals("98:D3:31:FB:14:C0"))
                {
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    // Controleer of de gebruiker verbonden is met Bluetooth.
    // Zet een connectie op met een socket.

    public boolean ConnectenBluetooth()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return connected;
    }


    // Opvangen data van HC-05 m.b.v. een thread.
    // De data wordt constant vanaf de HC-05 verzonden.

    void opvangenBluetoothData()
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
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            pitch = inputStream.read();
                            //final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    if (pitch <= oefening.getStapAtIndex(huidigeStap).getBluetoothDoelWaarde() + 5 && pitch >= oefening.getStapAtIndex(huidigeStap).getBluetoothDoelWaarde() - 5) {
                                        volgendeStap();
                                    }

                                    btText.setText(String.valueOf(pitch));
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

    // Functie om naar de volgende stap te gaan.

    private void volgendeStap(){
        if(huidigeStap < oefening.getStappen().size()-1) {
            mp = MediaPlayer.create(this, R.raw.stap_voltooid);
            mp.start();
            //oefening.getStapAtIndex(huidigeStap).afspelenGeluidVoltooid();
            huidigeStap++;
            stappenText.setText(oefening.getStapAtIndex(huidigeStap).getOmschrijving());
            speelVideoOefening();
        } else {
            stopThread = true;
            mp = MediaPlayer.create(this, R.raw.oefening_voltooid);
            mp.start();
            oefening.getStappen().clear();
            oefening = null;
            oefeningText.setText("Voltooid!");
            detecterenBewegenGif.setVisibility(View.INVISIBLE);
            stappenText.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            bewegenText.setVisibility(View.INVISIBLE);
        }
    }

    // Functie om naar de vorige stap te gaan

    private void vorigeStap(){
        huidigeStap--;
        stappenText.setText(oefening.getStapAtIndex(huidigeStap).getOmschrijving());
        speelVideoOefening();
    }

}
