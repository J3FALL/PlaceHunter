package pavel.rdtltd.placehunter.models;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pavel.rdtltd.placehunter.R;

/**
 * Created by Pavel on 20.01.2016.
 */
public class BaseMarker extends AbstractMarker {

    private static BitmapDescriptor baseMarkerIcon = null;

    public BaseMarker(double latitude, double longitude) {
        super(latitude, longitude);
        setBitmapDescriptor();
        setMarker(new MarkerOptions()
                    .position(new LatLng(getLatitude(), getLongitude()))
                    .icon(baseMarkerIcon));
    }

    public String toString() {
        return "Trade place: " +  "Someshit";
    }

    public static void setBitmapDescriptor() {
        if (baseMarkerIcon == null) {
            //choose markerIcon
            baseMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.bluecirclethumb);
        }
    }
}
