package pavel.rdtltd.placehunter.ui.createmarker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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

    private LinearLayout picker;
    private Toolbar toolbar;
    private LinearLayout snapshot, background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_marker);

        snapshot = (LinearLayout) findViewById(R.id.snapshot);
        background = (LinearLayout) findViewById(R.id.background);

        setBackground();
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Thin.ttf");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.create_marker_title);
            toolbar.setNavigationIcon(R.mipmap.ic_clear_white_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        //bindViews();
        //setOnClickListeners();


    }


    private void setBackground() {
        Drawable bitmapDrawable =   getDrawable(R.drawable.background);
        bitmapDrawable.setAlpha(210);
        background.setBackground(bitmapDrawable);
    }
    private void setOnClickListeners() {


        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLifetimeDialog();
            }
        });
    }

    private void bindViews() {
        //picker = (LinearLayout) findViewById(R.id.picker);
    }

    private void showLifetimeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme_DialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.lifetime_dialog, null);

        final TextView hourView, weekView, monthView;
        hourView = (TextView) view.findViewById(R.id.hourView);
        weekView = (TextView) view.findViewById(R.id.weekView);
        monthView = (TextView) view.findViewById(R.id.monthView);

        hourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                weekView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                monthView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
            }
        });
        weekView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                weekView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                monthView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
            }
        });
        monthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                weekView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                monthView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
            }
        });
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.basic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home: {
                onBackPressed();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
