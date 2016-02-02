package pavel.rdtltd.placehunter;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by PAVEL on 09.12.2015.
 */
public class PlaceHunter extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    protected void initializeDB() {

    }
}
