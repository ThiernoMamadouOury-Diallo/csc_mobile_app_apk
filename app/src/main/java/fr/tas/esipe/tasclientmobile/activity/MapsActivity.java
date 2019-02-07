package fr.tas.esipe.tasclientmobile.activity;

import android.app.Activity;
import android.os.Bundle;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.endpoint.RetrofitClientInstance;
import fr.tas.esipe.tasclientmobile.model.CustomOverLay;
import fr.tas.esipe.tasclientmobile.model.Location;
import fr.tas.esipe.tasclientmobile.endpoint.GetDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends Activity implements Callback<List<Location>> {

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

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Location>> call = service.getAllLocations();

        call.enqueue(this);


//        List<GeoPoint> locationsList = new ArrayList<>();
//        locationsList.add(new GeoPoint(48.8594718, 2.3449232));
//        locationsList.add(new GeoPoint(48.8594720, 2.3449228));



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
    public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
        if(response.isSuccessful()) {
            List<Overlay> mapOverlays = mMapView.getOverlays();
            List<Location> locationsList = response.body();
            CustomOverLay overlays = new CustomOverLay(getResources().getDrawable(R.drawable.baseline_accessible_forward_black_18dp), mMapView);

//        GeoPoint p = new GeoPoint(48.8594718, 2.3449232);
//        GeoPoint p = new GeoPoint(vehiculesLocationsResourcesapiServices.get(0).ListLocation.get(0).latitude, vehiculesLocationsResourcesapiServices.get(0).ListLocation.get(0).longitude);
            GeoPoint p = new GeoPoint(locationsList.get(0).getLatitude(), locationsList.get(0).getLongitude());
            OverlayItem overlayItem = new OverlayItem("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa", p);
            overlays.addOverlayItem(overlayItem);

//        GeoPoint p2 = new GeoPoint(48.87, 2.34495);
            GeoPoint p2 = new GeoPoint(locationsList.get(1).getLatitude(), locationsList.get(1).getLongitude());
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
        } else {
            System.out.println(response.errorBody());
        }

    }

    @Override
    public void onFailure(Call<List<Location>> call, Throwable t) {
        t.printStackTrace();
    }
}
