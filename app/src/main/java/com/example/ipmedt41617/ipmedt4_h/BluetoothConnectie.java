package com.example.ipmedt41617.ipmedt4_h;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.ipmedt41617.ipmedt4_h.Models.Bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public abstract class BluetoothConnectie extends AppCompatActivity{
    private Thread thread;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private boolean deviceConnected=false;
    private boolean stopThread;
    byte buffer[];
    private int pitch;
    protected Bluetooth bluetooth;


    // Het connecten met de HC-05 module

    protected boolean connectenBluetooth()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            found = false;
        }
        if(!bluetoothAdapter.isEnabled())
        {
            found = false;
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
            found = false;
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

    // Opzetten van een stream voor Bluetooth data

    protected boolean opzettenBluetoothStream()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            socket.connect();
            bluetooth = new Bluetooth(socket, socket.getOutputStream(), socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream= bluetooth.getSocket().getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = bluetooth.getSocket().getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return connected;
    }

    protected void stopThread(){
        stopThread = true;
    }
}
