package pavel.rdtltd.placehunter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import pavel.rdtltd.placehunter.models.Marker;
import pavel.rdtltd.placehunter.network.RestAPI;
import pavel.rdtltd.placehunter.ui.createmarker.CreateMarkerActivity;
import pavel.rdtltd.placehunter.ui.main.fragments.FavouriteFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.MapFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.MenuFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.TopFragment;
import pavel.rdtltd.placehunter.ui.view.GestureListener;
import pavel.rdtltd.placehunter.ui.view.OnFlingGestureListener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;

    private int status;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        bindViews();
        status = 0;
        //toolbar.setNavigationIcon(R.mipmap.ic_action_dehaze);

        initToolbar();
        initDrawer();

        setOnClickListeners();
        // Set the list's click listener

        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setSelectedTabIndicatorHeight(5);
        */

        //final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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

        getSupportFragmentManager().beginTransaction()
                .add(R.id.map, new MapFragment())
                .commit();
    }


    private void bindViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("ALL MARKS");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
    private void initDrawer() {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("YOUR");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("ALL");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("FAVOURITE");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("FRIEND'S");
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withDividerBelowHeader(false)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .build();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels / 2;
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withDrawerWidthDp(width)
                .withRootView(R.id.drawer_layout)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        //fab.hide();
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        fab.show();
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        if (fab.isShown()) {
                            fab.hide();
                        }
                    }
                })
                .addDrawerItems(
                        new DividerDrawerItem(),
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3,
                        new DividerDrawerItem(),
                        item4,
                        new DividerDrawerItem()
                )
                .withSelectedItemByPosition(4)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        System.out.println(position);
                        switch (position) {
                            case 2:
                                getSupportActionBar().setTitle("YOUR MARKS");
                                break;
                            case 4:
                                getSupportActionBar().setTitle("ALL MARKS");
                                break;
                            case 6:
                                getSupportActionBar().setTitle("FAVOURITE MARKS");
                                break;
                            case 8:
                                getSupportActionBar().setTitle("FRIENDS MARKS");
                                break;
                        }
                        return false;
                    }
                })
                .build();

    }

    private void setOnClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                fragment.makeSnapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        Intent intent = new Intent(context, CreateMarkerActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra("snapshot", bytes);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
