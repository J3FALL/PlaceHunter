package pavel.rdtltd.placehunter.ui.main.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Calendar;

import pavel.rdtltd.placehunter.MainActivity;
import pavel.rdtltd.placehunter.R;

/**
 * Created by PAVEL on 29.11.2015.
 */
public class MapFragment extends android.support.v4.app.Fragment{

    private GoogleMap map;
    private static Double latitude, longitude;

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment, container, false);

        latitude = 26.78;
        longitude = 72.56;
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
            }
        }
    }

    private void setUpMap() {
        map.setMyLocationEnabled(true);//выводим индикатор своего местоположения
    }

    public void makeSnapshot(GoogleMap.SnapshotReadyCallback callback) {
        map.snapshot(callback);
    }

}
