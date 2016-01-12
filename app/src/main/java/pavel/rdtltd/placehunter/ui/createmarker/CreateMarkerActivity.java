package pavel.rdtltd.placehunter.ui.createmarker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.lang.reflect.Field;

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

    private static final int TAKE_PICTURE_CODE = 0;
    private int pickerStatus = 0;
    private LinearLayout picker;
    private Toolbar toolbar;
    private LinearLayout snapshot, background;
    private ImageView pictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_marker);



        //Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Thin.ttf");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.create_marker_title);
            toolbar.setNavigationIcon(R.mipmap.ic_clear_white_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        bindViews();
        setOnClickListeners();
        setBackground();
    }


    private void setBackground() {
        Drawable bitmapDrawable =   getDrawable(R.drawable.background);
        bitmapDrawable.setAlpha(210);
        background.setBackground(bitmapDrawable);
    }
    private void setOnClickListeners() {

        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicture, TAKE_PICTURE_CODE);
                }
            }
        });
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLifetimeDialog();
            }
        });
    }

    private void bindViews() {

        picker = (LinearLayout) findViewById(R.id.picker);
        snapshot = (LinearLayout) findViewById(R.id.snapshot);
        background = (LinearLayout) findViewById(R.id.background);
        pictureView = (ImageView) findViewById(R.id.pictureView);
    }

    private void showLifetimeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme_DialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.lifetime_dialog, null);

        final TextView hourView, weekView, monthView;
        final NumberPicker numberPicker;

        hourView = (TextView) view.findViewById(R.id.hourView);
        weekView = (TextView) view.findViewById(R.id.weekView);
        monthView = (TextView) view.findViewById(R.id.monthView);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        setNumberPickerTextColor(numberPicker, getResources().getColor(R.color.white));

        hourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hours
                pickerStatus = 0;
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(24);
                numberPicker.setValue(12);
                hourView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                weekView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                monthView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
            }
        });
        weekView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //weeks
                pickerStatus = 1;
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(4);
                numberPicker.setValue(1);
                hourView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                weekView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                monthView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
            }
        });
        monthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //month
                pickerStatus = 2;
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(12);
                numberPicker.setValue(2);
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
        //String[] values = {"a", "b", "c", "d"};
        //numberPicker.setDisplayedValues(values);

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Drawable drawable = new BitmapDrawable(imageBitmap);
                    pictureView.setBackground(drawable);
                }

                break;
        }
    }


    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){

                }
                catch(IllegalAccessException e){

                }
                catch(IllegalArgumentException e){

                }
            }
        }
        return false;
    }

}
