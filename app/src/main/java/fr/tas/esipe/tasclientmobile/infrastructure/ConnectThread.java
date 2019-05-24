package fr.tas.esipe.tasclientmobile.infrastructure;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private BluetoothSocket mmSocket;
    String TAG;
    BluetoothDevice mmDevice;
    private UUID deviceUUID;
    Boolean isSocketUP;
    TextView view_data;
    ConnectedThread mConnectedThread;
    Activity activity;


    public ConnectThread(BluetoothDevice device, UUID uuid, String TAG, Boolean isSocketUP, TextView view_data, ConnectedThread mConnectedThread, Activity activity) {
        this.mConnectedThread = mConnectedThread;
        this.view_data = view_data;
        this.isSocketUP = isSocketUP;
        this.activity = activity;
        this.TAG = TAG;
        Log.d(TAG, "ConnectThread: started.");
        mmDevice = device;
        deviceUUID = uuid;
    }

    public void run() {
        BluetoothSocket tmp = null;
        Log.i(TAG, "RUN mConnectThread ");

        // Get a BluetoothSocket for a connection with the
        // given BluetoothDevice
        try {
            Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                    + deviceUUID);
            tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
        } catch (IOException e) {
            Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
        }

        mmSocket = tmp;

        // Make a connection to the BluetoothSocket

        try {
            // This is a blocking call and will only return on a
            // successful connection or an exception
            mmSocket.connect();

        } catch (IOException e) {
            // Close the socket
            try {
                mmSocket.close();
                Log.d(TAG, "run: Closed Socket.");
            } catch (IOException e1) {
                Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage());
            }
            Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + deviceUUID);
        }

        // Start the thread to manage the connection and perform transmissions
        Log.d(TAG, "connected: Starting.");
        mConnectedThread = new ConnectedThread(mmSocket, view_data, activity);
        mConnectedThread.start();
    }

    public void cancel() {
        try {
            Log.d(TAG, "cancel: Closing Client Socket.");
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
        }
    }

    public Boolean statusSocket(){

        isSocketUP=true;
        return isSocketUP;
    }

    public ConnectedThread getThread(){
        return mConnectedThread;
    }
}