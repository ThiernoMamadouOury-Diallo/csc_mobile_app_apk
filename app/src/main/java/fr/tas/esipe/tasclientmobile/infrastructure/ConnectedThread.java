package fr.tas.esipe.tasclientmobile.infrastructure;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    String TAG = "MainActivity";
    TextView view_data;
    Activity activity;


    public ConnectedThread(BluetoothSocket socket, TextView view_data, Activity activity) {
        Log.d(TAG, "ConnectedThread: Starting.");

        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;


        try {
            tmpIn = mmSocket.getInputStream();
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;

        this.view_data = view_data;
        this.activity = activity;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream

        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            // Read from the InputStream
            try {
                bytes = mmInStream.read(buffer);
                final String incomingMessage = new String(buffer, 0, bytes);
                Log.d(TAG, "InputStream: " + incomingMessage);

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        view_data.setText(incomingMessage);
                    }
                });


            } catch (IOException e) {
                Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage());
                break;
            }
        }
    }


    public Boolean write(byte[] bytes) {
        Boolean isSent=false;
        String text = new String(bytes, Charset.defaultCharset());
        Log.d(TAG, "write: Writing to outputstream: " + text);
        try {
            mmOutStream.write(bytes);
            isSent = true;
        } catch (IOException e) {
            Log.e(TAG, "write: Error writing to output stream. " + e.getMessage());
        }
        return isSent;
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}