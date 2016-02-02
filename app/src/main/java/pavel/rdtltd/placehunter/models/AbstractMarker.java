package pavel.rdtltd.placehunter.models;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Pavel on 20.01.2016.
 */
public abstract class AbstractMarker implements ClusterItem {
    protected double latitude;
    protected double longitude;

    protected MarkerOptions marker;
    
    @Override
    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }

    protected AbstractMarker(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    @Override
    public abstract String toString();

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void setMarker(MarkerOptions marker) {
        this.marker = marker;
    }
}