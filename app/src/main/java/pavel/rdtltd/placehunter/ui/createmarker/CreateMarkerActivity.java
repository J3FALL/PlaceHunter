package pavel.rdtltd.placehunter.ui.createmarker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pavel.rdtltd.placehunter.R;
import pavel.rdtltd.placehunter.models.Marker;
import pavel.rdtltd.placehunter.network.RestAPI;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by PAVEL on 29.11.2015.
 */
public class CreateMarkerActivity extends AppCompatActivity {

    private EditText inputTitle, inputDesc;
    private AppCompatSeekBar durationBar;
    private TextView durationView, createMarkerView;
    private FloatingActionButton fab;
    private TextInputLayout inputLayoutTitle, inputLayoutDesc;
    public static final int DURATION_COUNTS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_marker);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Thin.ttf");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_title);
        inputLayoutDesc = (TextInputLayout) findViewById(R.id.input_layout_desc);
        inputTitle = (EditText) findViewById(R.id.inputTitle);
        createMarkerView = (TextView) findViewById(R.id.createMarkerView);
        createMarkerView.setTypeface(typeface);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        durationBar = (AppCompatSeekBar) findViewById(R.id.durationBar);
        durationView = (TextView) findViewById(R.id.durationView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createMarker request
                if (checkContent()) {
                    Marker marker = new Marker();
                    marker.setTitle(inputTitle.getText().toString());
                    marker.setSnippet(inputDesc.getText().toString());

                    final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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
                    });
                }
            }
        });
        inputTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkContent();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkContent();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkContent();
            }
        });

        /*inputDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkContent();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkContent();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkContent();
            }
        });*/

        durationBar.setMax(DURATION_COUNTS);
        durationBar.setProgress(1);
        updateDurationView(1);
        durationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateDurationView(progress);
                checkContent();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setSupportActionBar(myToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setHomeButtonEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

    }

    private boolean checkContent() {
        String title = inputTitle.getText().toString();
        //String desc = inputDesc.getText().toString();

        if (fab.isShown()) {
            if (title.equals("")) {
                inputLayoutTitle.setErrorEnabled(true);
                inputLayoutTitle.setError("Title is required");
                fab.hide();

                return false;
            } else {
                inputLayoutTitle.setErrorEnabled(false);
                return true;
            }
        } else {
            if (title.equals("")) {
                inputLayoutTitle.setErrorEnabled(true);
                inputLayoutTitle.setError("Title is required");
                return false;
            } else {
                inputLayoutTitle.setErrorEnabled(false);
                fab.show();
                return true;
            }
        }
    }

    private void updateDurationView(int progress) {
        switch (progress) {
            case 0: durationView.setText("30 min");
                    break;
            case 1: durationView.setText("1 hour");
                    break;
            case 2: durationView.setText("4 hours");
                    //fab.show();
                    break;
            case 3: durationView.setText("12 hours");
                    break;
            case 4: durationView.setText("1 day");
                    break;
            case 5: durationView.setText("1 week");
                    break;
            case 6: durationView.setText("1 month");
                    break;
            case 7: durationView.setText("all time");
                    break;
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
