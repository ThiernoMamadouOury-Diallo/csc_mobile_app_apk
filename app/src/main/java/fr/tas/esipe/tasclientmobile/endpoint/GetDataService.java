package fr.tas.esipe.tasclientmobile.endpoint;

import java.util.List;

import fr.tas.esipe.tasclientmobile.model.Location;
import fr.tas.esipe.tasclientmobile.model.Parking;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/restapimap/maps")
    Call<List<Parking>> getAllLocations();
}