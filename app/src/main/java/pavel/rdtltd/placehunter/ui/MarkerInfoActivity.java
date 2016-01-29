package pavel.rdtltd.placehunter.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import pavel.rdtltd.placehunter.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Pavel on 26.01.2016.
 */
public class MarkerInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private ImageView image;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_marker);

        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        initToolbar();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(image.getDrawable());
                zoomImageView(image, image.getDrawable());
            }
        });

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.info_marker_activity_title);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            case R.id.share: {
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void zoomImageView(final View thumbView, Drawable drawable) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        getSupportActionBar().setTitle(title.getText());
        getWindow().setStatusBarColor(Color.BLACK);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        final LinearLayout photoContainer = (LinearLayout) findViewById(R.id.photoContainer);
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        expandedImageView.setImageDrawable(drawable);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        photoContainer.setVisibility(View.VISIBLE);
        expandedImageView.setVisibility(View.VISIBLE);
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        photoContainer.setVisibility(View.GONE);
                        expandedImageView.setVisibility(View.GONE);

                        getSupportActionBar().setTitle("Подробнее");
                        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        photoContainer.setVisibility(View.GONE);
                        expandedImageView.setVisibility(View.GONE);
                        getSupportActionBar().setTitle("Подробнее");
                        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
