package fr.tas.esipe.tasclientmobile.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.endpoint.ConnectToRestApi;
import fr.tas.esipe.tasclientmobile.model.BillFileBean;
import fr.tas.esipe.tasclientmobile.utils.BillsNotification;
import fr.tas.esipe.tasclientmobile.utils.PdfFileAdapter;

public class PdfAllBillsActivity extends AppCompatActivity {

    //get the absolute path of phone storage
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    static String jsonBillsUrls = "";
    final static String restApiClientUrl = "http://maven.apache.org/archives/maven-1.x/maven.pdf";
    final static String haProxyBaseUrl = "http://192.168.20.7:8088/home/tas/";
    final static String clientId = "12345";
    private ArrayList<String> listBills = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_alll_bills);
        setTitle("Dernières factures");
        //Check if permission is granted(for Marshmallow and higher versions)
        if (Build.VERSION.SDK_INT >= 23) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                checkPermission();
            } catch (ExecutionException | JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                initViews();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    ListView listView;
    ArrayList<BillFileBean> list;
    PdfFileAdapter pdfFileAdapter;

    public void initViews() throws ExecutionException, InterruptedException, JSONException {
        //views initialization
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        //calling the initList that will initialize the list to be given to Adapter for binding data
        initList();

        pdfFileAdapter = new PdfFileAdapter(this, R.layout.list_item, list);

        //set the adapter on listView
        listView.setAdapter(pdfFileAdapter);

        //when user chooses a particular pdf file from list,
        //start another activity that will show the pdf
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Download File from server
               //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getBillName())));
               startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(haProxyBaseUrl+list.get(position).getBillName())));
            }
        });
    }


    /**
     * Initialize Client Bills URLs Array
     */
    public void initList() throws ExecutionException, InterruptedException, JSONException {


//        String jsonBillsUrls = new ConnectToRestApi()
//                .execute("http://http://10.0.2.2:8000/lastBill/1").get();
////                .execute("http://api.dev.tas.inside.esiag.info/restapiclient/bills/1").get();

        ///Get Data
        BillsNotification billsNotification = new BillsNotification(jsonBillsUrls);
        list = billsNotification.initList();

    }

    //Handling permissions for Android Marshmallow and above
    public void checkPermission() throws ExecutionException, InterruptedException, JSONException {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //if permission granted, initialize the views
            initViews();
        } else {
            //show the dialog requesting to grant permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        initViews();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //permission is denied (this is the first time, when "never ask again" is not checked)
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        finish();
                    }
                    //permission is denied (and never ask again is  checked)
                    else {
                        //shows the dialog describing the importance of permission, so that user should grant
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("You have forcefully denied Read storage permission.\n\nThis is necessary for the working of app." + "\n\n" + "Click on 'Grant' to grant permission")
                                //This will open app information where user can manually grant requested permission
                                .setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                //close the app
                                .setNegativeButton("Don't", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        builder.setCancelable(false);
                        builder.create().show();
                    }
                }
        }
    }

}
