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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;

import pavel.rdtltd.placehunter.R;
import pavel.rdtltd.placehunter.models.AbstractMarker;
import pavel.rdtltd.placehunter.models.BaseMarker;
import pavel.rdtltd.placehunter.ui.MarkerInfoActivity;
import pavel.rdtltd.placehunter.utils.ClusterRenderer;

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





                double longitude = 35.0;
                double latitude = 35.0;
                double curLong = longitude - 2.0;
                double curLat = latitude - 2.0;

                while (curLong != longitude + 2.0) {
                   curLat = latitude - 2.0;
                   while (curLat != latitude + 2.0) {
                       clusterManager.addItem(new BaseMarker(curLat, curLong, "YOYO this is test"));
                       curLat += 0.25;
                   }
                   curLong += 0.25;
                }
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
