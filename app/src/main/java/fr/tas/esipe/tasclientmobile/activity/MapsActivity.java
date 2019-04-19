package fr.tas.esipe.tasclientmobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.tas.esipe.tasclientmobile.R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;
import java.util.logging.Logger;

import fr.tas.esipe.tasclientmobile.endpoint.RetrofitClientInstance;
import fr.tas.esipe.tasclientmobile.model.CustomOverLay;
import fr.tas.esipe.tasclientmobile.endpoint.GetDataService;
import fr.tas.esipe.tasclientmobile.model.Parking;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements Callback<List<Parking>> {

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

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getInstanceForMaps().create(GetDataService.class);
        Call<List<Parking>> call = service.getAllLocations();

        call.enqueue(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }


    @Override
    public void onResponse(Call<List<Parking>> call, Response<List<Parking>> response) {
        if(response.isSuccessful()) {
            List<Overlay> mapOverlays = mMapView.getOverlays();
            List<Parking> parkingsList = response.body();
            CustomOverLay overlays = new CustomOverLay(getResources().getDrawable(R.drawable.common_full_open_on_phone, getTheme()), mMapView, this );

            for(Parking parking : parkingsList){
                GeoPoint p = new GeoPoint(parking.getLatitude(), parking.getLongitude());
                OverlayItem overlayItem = new OverlayItem("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa", p);

                overlays.addOverlayItem(overlayItem);
            }
            mapOverlays.add(overlays);
        } else {
            Logger.getGlobal().info(response.errorBody().toString());
        }

    }

    @Override
    public void onFailure(Call<List<Parking>> call, Throwable t) {
        t.printStackTrace();
    }
}
