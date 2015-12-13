package pavel.rdtltd.placehunter;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import pavel.rdtltd.placehunter.models.Marker;
import pavel.rdtltd.placehunter.network.RestAPI;
import pavel.rdtltd.placehunter.ui.createmarker.CreateMarkerActivity;
import pavel.rdtltd.placehunter.ui.main.fragments.FavouriteFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.MapFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.TopFragment;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setSelectedTabIndicatorHeight(5);

        fabButton = (FloatingActionButton) findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMarkerActivity.class);
                startActivity(intent);
            }
        });

        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        /*Marker marker = new Marker();
        marker.setTitle("yo");
        marker.setSnippet("Loh");
        marker.setType("shmal");
        marker.setRateUp(1231231);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.100:3030")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestAPI api = retrofit.create(RestAPI.class);
        Call<String> call = api.createMarker(marker);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                System.out.println(response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.toString());
            }
        });*/
        /*RestAPI api = retrofit.create(RestAPI.class);
        Call<Marker> call = api.getMarker(1);
        call.enqueue(new Callback<Marker>() {
            @Override
            public void onResponse(Response<Marker> response, Retrofit retrofit) {
                System.out.println(gson.toJson(response.body()));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavouriteFragment(), "Favourite");
        adapter.addFragment(new MapFragment(), "Map");
        adapter.addFragment(new TopFragment(), "Topchik");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
