package fr.tas.esipe.tasclientmobile.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.UUID;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.infrastructure.AcceptThread;
import fr.tas.esipe.tasclientmobile.infrastructure.ConnectedThread;
import fr.tas.esipe.tasclientmobile.infrastructure.FindBlueToCreateConnection;
import fr.tas.esipe.tasclientmobile.model.ConnectionInfo;
import fr.tas.esipe.tasclientmobile.service.MessageService;

public class BleActivity extends AppCompatActivity {

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ConnectedThread mConnectedThread;

    String TAG = "MainActivity";
    TextView view_data;
    Boolean isSocketUP = false;
    FindBlueToCreateConnection findBlueToCreateConnection;
    BluetoothSocket mmSocket;
    String ClientId_And_CarId = String.format("%s %s", MainActivity.connectedUser.getId(), "AA-000-ZZ");
    byte[] bytes = ClientId_And_CarId.getBytes(Charset.defaultCharset());
    ConnectionInfo connectionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        view_data = (TextView) findViewById(R.id.textView);

        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }
        connectionInfo=new ConnectionInfo();


    }

    public void createConnection(View view) {

        if(!isSocketUP){

            //Create connection to socket
            findBlueToCreateConnection = new FindBlueToCreateConnection(TAG, MY_UUID_INSECURE, isSocketUP, view_data, mConnectedThread, bluetoothAdapter, BleActivity.this);
            connectionInfo = findBlueToCreateConnection.pairdevice();
            isSocketUP = connectionInfo.getSocketUP();
        }

        MessageService messageService = new MessageService(connectionInfo);
        Boolean resultat = messageService.sendMessage(bytes);
    }

    public void start_Server(View view) {

        AcceptThread accept = new AcceptThread(mmSocket, TAG, MY_UUID_INSECURE, view_data, BleActivity.this);
        accept.start();

    }
}
