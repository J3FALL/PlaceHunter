package pavel.rdtltd.placehunter.ui.main.fragments;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import pavel.rdtltd.placehunter.R;
import pavel.rdtltd.placehunter.models.AbstractMarker;
import pavel.rdtltd.placehunter.models.BaseMarker;
import pavel.rdtltd.placehunter.network.RestAPI;
import pavel.rdtltd.placehunter.ui.MarkerInfoActivity;
import pavel.rdtltd.placehunter.utils.ClusterRenderer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by PAVEL on 29.11.2015.
 */
public class MapFragment extends android.support.v4.app.Fragment{

    private Context context;
    private GoogleMap map;
    private Bundle savedInstanceState;
    //private static Double latitude, longitude;
    private ClusterManager<AbstractMarker> clusterManager;
    private AbstractMarker clickedMarker;
    private Location myLocation;
    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment, container, false);


        setUpMapIfNeeded();
        return view;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();

                clusterManager = new ClusterManager<AbstractMarker>(context, map);
                clusterManager.setRenderer(new ClusterRenderer(context, map, clusterManager));
                map.setOnCameraChangeListener(clusterManager);
                map.setOnMarkerClickListener(clusterManager);
                map.setInfoWindowAdapter(clusterManager.getMarkerManager());
                map.setOnInfoWindowClickListener(clusterManager);
                clusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<AbstractMarker>() {
                    @Override
                    public void onClusterItemInfoWindowClick(AbstractMarker abstractMarker) {
                        //launch MarkerInfoActivity
                        System.out.println("!");
                        Intent intent = new Intent(context, MarkerInfoActivity.class);
                        intent.putExtra("title", abstractMarker.getMarker().getTitle());
                        startActivity(intent);
                    }
                });
                clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<AbstractMarker>() {
                    @Override
                    public boolean onClusterItemClick(AbstractMarker abstractMarker) {
                        clickedMarker = abstractMarker;
                        return false;
                    }
                });
                clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater(savedInstanceState).inflate(R.layout.custom_infowindow, null);

                        TextView title = (TextView) v.findViewById(R.id.title);
                        title.setText(clickedMarker.getTitle());
                        return v;
                    }
                });

                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        //get camera bounds
                        clusterManager.onCameraChange(cameraPosition);
                        LatLngBounds curBounds = map.getProjection()
                                .getVisibleRegion().latLngBounds;

                        LatLng northWest = new LatLng(curBounds.southwest.longitude, curBounds.northeast.latitude);
                        LatLng southEast = new LatLng(curBounds.northeast.longitude, curBounds.southwest.latitude);

                        System.out.println(northWest.toString() + " " + southEast.toString());
                        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://93.100.180.230:3030")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        RestAPI api = retrofit.create(RestAPI.class);

                        //Call<List<pavel.rdtltd.placehunter.models.Marker>> call = api.getMarkers(1, 100, 1, 100);
                        Call<List<pavel.rdtltd.placehunter.models.Marker>> call = api.getMarkers(northWest.latitude, southEast.latitude, northWest.longitude, southEast.longitude);
                        call.enqueue(new Callback<List<pavel.rdtltd.placehunter.models.Marker>>() {
                            @Override
                            public void onResponse(Response<List<pavel.rdtltd.placehunter.models.Marker>> response, Retrofit retrofit) {
                                if (response.body() != null) System.out.println(response.body().get(0).getTitle());
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println(t.toString());
                            }
                        });
                    }
                });

            }
        }
    }

    private void setUpMap() {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);

        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocation = location;
            }
        });

         //remove map toolbar
        /*map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater(savedInstanceState).inflate(R.layout.custom_infowindow, null);
                return v;
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(context, MarkerInfoActivity.class);
                startActivity(intent);
                return false;
            }
        });*/

    }

    public void makeSnapshot(GoogleMap.SnapshotReadyCallback callback) {
        map.snapshot(callback);
    }
    public Location getMyLocation() { return myLocation; }

}
