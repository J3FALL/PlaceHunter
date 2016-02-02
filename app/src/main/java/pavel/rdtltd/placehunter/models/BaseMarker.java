package pavel.rdtltd.placehunter.models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pavel.rdtltd.placehunter.PlaceHunter;
import pavel.rdtltd.placehunter.R;

/**
 * Created by Pavel on 20.01.2016.
 */
public class BaseMarker extends AbstractMarker {

    private static BitmapDescriptor baseMarkerIcon = null;

    public BaseMarker(double latitude, double longitude, String title) {
        super(latitude, longitude, title);
        setBitmapDescriptor();
        setMarker(new MarkerOptions()
                    .position(new LatLng(getLatitude(), getLongitude()))
                    .icon(baseMarkerIcon)
                    .title(getTitle()));
    }

    public String toString() {
        return "Trade place: " +  "Someshit";
    }

    public static void setBitmapDescriptor() {
        if (baseMarkerIcon == null) {
            //choose markerIcon
            Context context = PlaceHunter.getContext();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mapicon), 80, 80, false);
            baseMarkerIcon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
        }
    }
}
