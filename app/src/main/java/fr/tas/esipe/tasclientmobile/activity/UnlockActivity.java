package fr.tas.esipe.tasclientmobile.activity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.adapter.BookingAdapter;
import fr.tas.esipe.tasclientmobile.service.LoginService;
import fr.tas.esipe.tasclientmobile.service.NFCManager;
import fr.tas.esipe.tasclientmobile.service.UnlockService;

public class UnlockActivity extends AppCompatActivity {

    private NFCManager nfcMger;
    private NdefMessage msg;
    private ProgressDialog dialog;
    private Tag currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        try {
            ArrayList<Integer> reservations = new UnlockService(getApplicationContext()).getReservation(MainActivity.connectedUser);

            final ListView listView = (ListView)findViewById(R.id.list);
            listView.setAdapter(new BookingAdapter(this, R.layout.item_booking, reservations));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int idBooking = (int)listView.getItemAtPosition(position);
                    msg = nfcMger.createTextMessage("{\"id_account\": " + MainActivity.connectedUser.getId() + ",\"id_reservation\":" + idBooking + "}");
                    if(msg != null){
                        dialog = new ProgressDialog(UnlockActivity.this);
                        dialog.setMessage("Tag NFC Tag please");
                        dialog.show();
                    }
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nfcMger = new NFCManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            nfcMger.verifyNFC();

            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[] {};
            String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }

        catch(NFCManager.NFCNotSupported nfcnsup) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();

        }
        catch(NFCManager.NFCNotEnabled nfcnEn) {
            Toast.makeText(this, "NFC Not enabled", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        nfcMger.disableDispatch();
    }

    @Override
    public void onNewIntent(Intent intent) {
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (msg != null) {
            nfcMger.writeTag(currentTag, msg);
            dialog.dismiss();
            Toast.makeText(this, "Tag written", Toast.LENGTH_LONG).show();
        }
    }


}
