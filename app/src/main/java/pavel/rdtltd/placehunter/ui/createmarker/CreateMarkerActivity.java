package pavel.rdtltd.placehunter.ui.createmarker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import pavel.rdtltd.placehunter.R;
import pavel.rdtltd.placehunter.models.Marker;
import pavel.rdtltd.placehunter.network.RestAPI;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by PAVEL on 29.11.2015.
 */
public class CreateMarkerActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE_CODE = 0, GALLERY_PICTURE_CODE = 1;
    private boolean hasPicture = false;
    private int pickerStatus = 0, pickerValue = 2;
    private LinearLayout picker;
    private Toolbar toolbar;
    private LinearLayout snapshot, background;
    private ImageView pictureView;
    private TextView lifetimeView;
    private Button publish;
    private Bitmap snapshotImage;
    private double myLongitude, myLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_marker);

        Intent intent = getIntent();

        myLongitude = intent.getDoubleExtra("longitude", 0);
        myLatitude = intent.getDoubleExtra("latitude", 0);

        byte[] bytes = intent.getByteArrayExtra("snapshot");
        snapshotImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);;
        //System.out.println(snapshotImage);
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

        updateLifetimeView();

    }


    private void setBackground() {
        Drawable bitmapDrawable =   getDrawable(R.drawable.background);
        bitmapDrawable.setAlpha(210);
        background.setBackground(bitmapDrawable);

        Drawable drawable = new BitmapDrawable(snapshotImage);
        snapshot.setBackground(drawable);
    }
    private void setOnClickListeners() {

        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoDialog();
            }
        });
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLifetimeDialog();
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrect()) {
                    String encodedImage = "";
                    if (hasPicture) {
                        //drawable to Base64String
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) pictureView.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    }
                    //post
                    //waiting
                    //toast if succeed
                }
            }
        });

    }

    private void bindViews() {

        picker = (LinearLayout) findViewById(R.id.picker);
        snapshot = (LinearLayout) findViewById(R.id.snapshot);
        background = (LinearLayout) findViewById(R.id.background);
        pictureView = (ImageView) findViewById(R.id.pictureView);
        lifetimeView = (TextView) findViewById(R.id.lifetimeView);
        publish = (Button) findViewById(R.id.publish);
    }

    private void showPhotoDialog() {
        String[] values = {"Take a picture", "Chose from gallery"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme_DialogStyle);
        builder.setTitle(R.string.photo_dialog_title)
                .setItems(values, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0: Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePicture.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(takePicture, TAKE_PICTURE_CODE);
                                    }
                                    break;

                            case 1: Intent galleryIntent = new Intent(
                                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, GALLERY_PICTURE_CODE);
                        }
                    }
                });
        builder.show();
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
                setPickerValues(numberPicker, 1, 24, 12);
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
                setPickerValues(numberPicker, 1, 4, 1);
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
                setPickerValues(numberPicker, 1, 12, 2);
                hourView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                weekView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                monthView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
            }
        });
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pickerValue = numberPicker.getValue();
                        updateLifetimeView();
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

        //System.out.println(pickerStatus);
        switch (pickerStatus) {
            case 0: setPickerValues(numberPicker, 1, 24, pickerValue);
                    hourView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                    weekView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                    monthView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                    break;
            case 1: setPickerValues(numberPicker, 1, 4, pickerValue);
                    hourView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                    weekView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                    monthView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                    break;
            case 2: setPickerValues(numberPicker, 1, 12, pickerValue);
                    hourView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                    weekView.setTextColor(getResources().getColor(R.color.dialog_text_unpressed));
                    monthView.setTextColor(getResources().getColor(R.color.dialog_text_pressed));
                    break;
        }
    }

    private void updateLifetimeView() {
        String result = "";
        switch (pickerStatus) {
            case 0: result = String.valueOf(pickerValue) + " HR";
                    break;
            case 1: result = String.valueOf(pickerValue) + " W";
                    break;
            case 2: result = String.valueOf(pickerValue) + " M";
                    break;
        }

        lifetimeView.setText(result);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.basic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Drawable drawable = new BitmapDrawable(imageBitmap);
                    pictureView.setBackground(drawable);

                    hasPicture = true;
                }

                break;

            case GALLERY_PICTURE_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedImageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    pictureView.setBackground(drawable);

                    hasPicture = true;
                }
        }
    }

    private Bitmap getScaledImage(String filePath) {
        int targetW = pictureView.getWidth();
        int targetH = pictureView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);

        return bitmap;
    }
    public static void setPickerValues(NumberPicker numberPicker, int minValue, int maxValue, int currentValue) {
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(currentValue);
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


    private boolean isCorrect() {
        //need to add
        return true;
    }
}
