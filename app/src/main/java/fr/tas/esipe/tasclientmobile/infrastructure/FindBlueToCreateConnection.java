package fr.tas.esipe.tasclientmobile.infrastructure;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.TextView;

import fr.tas.esipe.tasclientmobile.model.ConnectionInfo;

import java.util.Set;
import java.util.UUID;

public class FindBlueToCreateConnection {
    String TAG;
    String myDeviceName="pi_car_dashboard";
    private UUID deviceUUID;
    Boolean isSocketUP;
    TextView view_data;
    ConnectedThread mConnectedThread;
    BluetoothAdapter bluetoothAdapter;
    Activity activity;

    ConnectThread connect;
    ConnectionInfo connectionInfo;

    public FindBlueToCreateConnection(String TAG, UUID deviceUUID, Boolean isSocketUP, TextView view_data, ConnectedThread mConnectedThread, BluetoothAdapter bluetoothAdapter, Activity activity) {

        this.TAG = TAG;
        this.deviceUUID = deviceUUID;
        this.isSocketUP = isSocketUP;
        this.view_data = view_data;
        this.mConnectedThread = mConnectedThread;
        this.bluetoothAdapter = bluetoothAdapter;
        this.activity = activity;
    }

    public ConnectionInfo pairdevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        Log.e(TAG, "" + pairedDevices.size());
        if (pairedDevices.size() > 0) {
            Object[] devices = pairedDevices.toArray();
            int i;
            for (i = 0; i < pairedDevices.size(); i++) {
                BluetoothDevice device = (BluetoothDevice) devices[i];

                if (device.getName().equals(myDeviceName)) {

                    Log.e("Nom appareil:", device.getName());
                    Log.e("Adresse mac:", device.getAddress());

                    try {
                        connect = new ConnectThread(device, deviceUUID, TAG, isSocketUP, view_data, mConnectedThread, activity);
                        connect.start();

                        //Envoie du message
                        Thread.sleep(2500);

                        isSocketUP = connect.statusSocket();

                        connectionInfo = new ConnectionInfo(connect.getThread(), isSocketUP);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        }
        return connectionInfo;
    }
}
