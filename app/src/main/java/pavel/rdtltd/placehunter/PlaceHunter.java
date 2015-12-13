package pavel.rdtltd.placehunter;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by PAVEL on 09.12.2015.
 */
public class PlaceHunter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }


    protected void initializeDB() {

    }
}
