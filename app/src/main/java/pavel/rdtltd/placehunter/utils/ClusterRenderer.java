package pavel.rdtltd.placehunter.utils;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import pavel.rdtltd.placehunter.models.AbstractMarker;

/**
 * Created by Pavel on 21.01.2016.
 */
public class ClusterRenderer extends DefaultClusterRenderer<AbstractMarker> {

    public ClusterRenderer(Context context, GoogleMap map,
                          ClusterManager<AbstractMarker> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(AbstractMarker item,
                                               MarkerOptions markerOptions) {
        markerOptions.icon(item.getMarker().getIcon());
    }
}
