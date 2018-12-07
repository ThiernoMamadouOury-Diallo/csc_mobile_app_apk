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
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

import fr.tas.esipe.tasclientmobile.model.CustomOverLay;

public class MapsActivity extends Activity {

    private MapView mMapView;
    private MapController mMapController;
    private MyLocationNewOverlay myLocationNewOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        mMapView = findViewById(R.id.map);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setCenter(new GeoPoint(48.8594718, 2.3449232));
        mMapController.setZoom(25);


        //Test marker

        /*ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Title", "Description", new GeoPoint(48.8594718, 2.3449232))); // Lat/Lon decimal degrees

//the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items);
        mOverlay.setFocusItemsOnTap(true);

        Marker startMarker = new Marker(48.8594718, 2.3449232);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mMapView.getOverlays().add(startMarker);


        mMapView.getOverlays().add(mOverlay);*/

        List<Overlay> mapOverlays = mMapView.getOverlays();
        CustomOverLay overlays = new CustomOverLay(getResources().getDrawable(R.drawable.baseline_accessible_forward_black_18dp), mMapView);

        GeoPoint p = new GeoPoint(48.8594718, 2.3449232);
        OverlayItem overlayItem = new OverlayItem("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa", p);
        overlays.addOverlayItem(overlayItem);

        GeoPoint p2 = new GeoPoint(48.87, 2.34495);
        OverlayItem overlayItem2 = new OverlayItem("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa", p2);
        overlays.addOverlayItem(overlayItem2);

        mapOverlays.add(overlays);





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


}
