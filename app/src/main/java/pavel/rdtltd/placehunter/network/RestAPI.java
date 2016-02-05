package pavel.rdtltd.placehunter.network;

import java.util.List;

import pavel.rdtltd.placehunter.models.Marker;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by PAVEL on 09.12.2015.
 */
public interface RestAPI {

    @GET("/markers/{id}")
    Call<Marker> getMarker(@Path("id") int id);

    @GET("/")
    Call<String> get();

    @POST("/markers")
    Call<String> createMarker(@Body Marker marker);

    @GET("/markers/{lat1},{lat2},{long1},{long2}")
    Call<List<Marker>> getMarkers(@Path("lat1") double lat1,
                            @Path("lat2") double lat2,
                            @Path("long1") double long1,
                            @Path("long2") double long2);
}
