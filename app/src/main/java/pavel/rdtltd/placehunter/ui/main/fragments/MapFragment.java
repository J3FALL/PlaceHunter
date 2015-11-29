package pavel.rdtltd.placehunter.ui.main.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

import pavel.rdtltd.placehunter.R;

/**
 * Created by PAVEL on 29.11.2015.
 */
public class MapFragment extends android.support.v4.app.Fragment{

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        return view;


    }
}
