package fr.tas.esipe.tasclientmobile.infrastructure;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {

    // The local server socket
    private final BluetoothServerSocket mmServerSocket;
    private String TAG;
    BluetoothAdapter bluetoothAdapter;
    ConnectedThread mConnectedThread;
    TextView textView;
    Activity activity;


    public AcceptThread(BluetoothSocket mmSocket, String TAG, UUID MY_UUID_INSECURE, TextView textView, Activity activity) {
        BluetoothServerSocket tmp = null;
        mmServerSocket = tmp;
        this.textView = textView;
        this.activity = activity;
        this.TAG = TAG;

        // Create a new listening server socket
        try {
            tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("appname", MY_UUID_INSECURE);

            Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
        } catch (IOException e) {
            Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());
        }
    }



    public void run() {
        Log.d(TAG, "run: AcceptThread Running.");

        BluetoothSocket socket = null;

        try {
            // This is a blocking call and will only return on a
            // successful connection or an exception
            Log.d(TAG, "run: RFCOM server socket start.....");

            socket = mmServerSocket.accept();

            Log.d(TAG, "run: RFCOM server socket accepted connection.");

        } catch (IOException e) {
            Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());
        }

        //talk about this is in the 3rd
        if (socket != null) {
            connected(socket);
        }

        Log.i(TAG, "END mAcceptThread ");
    }

    public void cancel() {
        Log.d(TAG, "cancel: Canceling AcceptThread.");
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage());
        }
    }
    private void connected(BluetoothSocket mmSocket) {
        Log.d(TAG, "connected: Starting.");

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket, textView, activity);
        mConnectedThread.start();
    }

}