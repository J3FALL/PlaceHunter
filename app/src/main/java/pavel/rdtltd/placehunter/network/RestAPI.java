package pavel.rdtltd.placehunter.network;

import pavel.rdtltd.placehunter.models.Marker;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

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
}
