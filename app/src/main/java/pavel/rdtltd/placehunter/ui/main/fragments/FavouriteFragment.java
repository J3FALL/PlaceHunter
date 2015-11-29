package pavel.rdtltd.placehunter.ui.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pavel.rdtltd.placehunter.R;

/**
 * Created by PAVEL on 29.11.2015.
 */
public class FavouriteFragment extends Fragment {

    public FavouriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        return view;


    }
}
