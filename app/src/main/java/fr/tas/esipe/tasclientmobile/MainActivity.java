package fr.tas.esipe.tasclientmobile;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity extends Activity {

    private MapView mMapView;
    private MapController mMapController;
    private MyLocationNewOverlay myLocationNewOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();

        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.map);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setCenter(new GeoPoint(48.8594718, 2.3449232));
        mMapController.setZoom(25);
        final MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(this, mMapView);
        myLocationNewOverlay.enableFollowLocation();
        myLocationNewOverlay.enableMyLocation();
        mMapView.getOverlays().add(myLocationNewOverlay);
        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                GeoPoint g = myLocationNewOverlay.getMyLocation();
                if(g != null){
                    mMapController.setCenter(g);
                    mMapController.animateTo(g);
                    mMapController.setZoom(50);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

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
}
