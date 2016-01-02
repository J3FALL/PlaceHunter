package pavel.rdtltd.placehunter;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import pavel.rdtltd.placehunter.models.Marker;
import pavel.rdtltd.placehunter.network.RestAPI;
import pavel.rdtltd.placehunter.ui.createmarker.CreateMarkerActivity;
import pavel.rdtltd.placehunter.ui.main.fragments.FavouriteFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.MapFragment;
import pavel.rdtltd.placehunter.ui.main.fragments.MenuFragment;
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
    private MenuFragment menuFragment;
    private TopFragment topFragment;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private int status;

    private LinearLayout linearLayout;
    private TextView text1, text2, text3, text4;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        status = 0;
        toolbar.setNavigationIcon(R.mipmap.ic_action_dehaze);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setSelectedTabIndicatorHeight(5);
        */
        fabButton = (FloatingActionButton) findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMarkerActivity.class);
                startActivity(intent);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.menu);
        ll = (LinearLayout) findViewById(R.id.ll);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);


        linearLayout.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("!!!");
            }
        });
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

        menuFragment = new MenuFragment();
        topFragment = new TopFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void slideToBottom(View view){
        //TranslateAnimation animate = new TranslateAnimation(0, 0, 130, 0);
        ScaleAnimation animate = new ScaleAnimation(1f, 1f, 0f, 1f);
        //TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(250);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.VISIBLE);
                text4.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    public void slideToTop(final View view){
        ScaleAnimation animate = new ScaleAnimation(1f, 1f, 1f, 0f);
        animate.setDuration(250);
        animate.setFillAfter(true);
        animate.setFillEnabled(false);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.layout(0, 0, 0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        if (menuItem.getItemId() == android.R.id.home) {

            //fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up);
            if (status == 0) {
                toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);
                status = 1;
                slideToBottom(linearLayout);
                //fragmentTransaction.addToBackStack(null);
            } else {
                slideToTop(linearLayout);
                toolbar.setNavigationIcon(R.mipmap.ic_action_dehaze);
                status = 0;


            }
            //fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void toggleMenu() {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
            //ft.replace(R.id.fragmentContainer, new MenuFragment());
            ft.commit();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavouriteFragment(), "Favourite");
        adapter.addFragment(new MapFragment(), "Map");
       // adapter.addFragment(new TopFragment(), "Topchik");
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
