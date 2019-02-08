package fr.tas.esipe.tasclientmobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.tas.esipe.tasclientmobile.model.BillFileBean;

public class PdfAllBillsActivity extends AppCompatActivity {

    //get the absolute path of phone storage
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    final static String restApiClientUrl = "http://maven.apache.org/archives/maven-1.x/maven.pdf";
    final static String clientId = "12345";
    private ArrayList<String> listBills = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_alll_bills);
        setTitle("Dernières factures");
        //Check if permission is granted(for Marshmallow and higher versions)
        if (Build.VERSION.SDK_INT >= 23)
            checkPermission();
        else
            initViews();
    }
    ListView listView;
    ArrayList<BillFileBean> list;
    PdfFileAdapter pdfFileAdapter;

    public void initViews(){
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getBillName())));
            }
        });
    }


    /**
     * Initialize Client Bills URLs Array
     */
    public void initList(){

        //Connexion à l'API REST pour recupérer la liste des factures
        //Mettre le resultat dans le tableau d'URL
        new ConnectToRestApi().execute("http://192.168.0.43:5000/bills/1");
        // Remplir la list d'url ici
        listBills.add("http://maven.apache.org/archives/maven-1.x/maven.pdf");

        try{

            for(String fileName : listBills){
                //choose only the pdf files
                if(fileName.endsWith(".pdf")){
                    list.add(new BillFileBean(fileName));
                }
            }

        }catch(Exception e){
            Log.i("show","Something went wrong. "+e.toString());
        }
    }

    //Handling permissions for Android Marshmallow and above
    public void checkPermission() {
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
                    initViews();
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
