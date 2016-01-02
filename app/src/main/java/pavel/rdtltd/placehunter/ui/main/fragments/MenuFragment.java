package pavel.rdtltd.placehunter.ui.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pavel.rdtltd.placehunter.R;

/**
 * Created by PAVEL on 23.12.2015.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        return view;


    }
}
