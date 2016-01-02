package pavel.rdtltd.placehunter.ui.view;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by PAVEL on 02.01.2016.
 */
public abstract class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private final int SWIPE_MIN_DISTANCE = 120;
    private final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            // Right to left, your code here
            return false;
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >  SWIPE_THRESHOLD_VELOCITY) {
            // Left to right, your code here
            return false;
        }
        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            // Bottom to top, your code here
            onBottomToTop();
            return true;
        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            // Top to bottom, your code here
            return false;
        }
        return false;
    }

    public abstract void onBottomToTop();
}
