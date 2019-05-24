package fr.tas.esipe.tasclientmobile.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import fr.tas.esipe.tasclientmobile.exception.LoginException;
import fr.tas.esipe.tasclientmobile.model.User;
import fr.tas.esipe.tasclientmobile.service.LoginService;
import fr.tas.esipe.tasclientmobile.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LoginService loginService;
    public static User connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(loginService == null)
            loginService = new LoginService(getApplicationContext());

        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        checkPermission();
        Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login = (EditText)findViewById(R.id.loginEdit);
                EditText password = (EditText)findViewById(R.id.passwordEdit);

                if(login != null && password != null && !"".equals(login.getText().toString()) && !"".equals(password.getText().toString())) {
                    try{
                        User user = loginService.getLoggedUser(login.getText().toString(), password.getText().toString());
                        if(user != null){
                            connectedUser = user;
                            setUserLayout();
                        }
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill the fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(connectedUser != null){
            setUserLayout();
        }
    }

    private void setUserLayout(){
        LinearLayout loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
        loginLayout.setVisibility(LinearLayout.GONE);
        LinearLayout passwordLayout = (LinearLayout)findViewById(R.id.passwordLayout);
        passwordLayout.setVisibility(LinearLayout.GONE);

        TextView helloText = (TextView)findViewById(R.id.homeText);
        helloText.setText(String.format("Hello %s", connectedUser.getLogin()));
    }

    /**
     * Check permission
     */
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }else{

        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }else{

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(connectedUser != null) {
            if (id == R.id.maps_drawer) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
            if (id == R.id.bills_drawer) {
                Intent intent = new Intent(MainActivity.this, PdfAllBillsActivity.class);
                startActivity(intent);
            }
            if (id == R.id.unlock_drawer) {
                Intent intent = new Intent(MainActivity.this, UnlockActivity.class);
                startActivity(intent);
            }

            if (id == R.id.Bluetooth) {
                Intent intent = new Intent(MainActivity.this, BleActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Log in to access these features", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


