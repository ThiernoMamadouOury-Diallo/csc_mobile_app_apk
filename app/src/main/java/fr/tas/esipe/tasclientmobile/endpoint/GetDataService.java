package fr.tas.esipe.tasclientmobile.endpoint;

import java.util.List;

import fr.tas.esipe.tasclientmobile.model.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/maps")
    Call<List<Location>> getAllLocations();
}