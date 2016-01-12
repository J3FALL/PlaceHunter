package pavel.rdtltd.placehunter.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Pavel on 12.01.2016.
 */
public class NumberPicker extends android.widget.NumberPicker {

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if(view instanceof EditText){
            ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            ((EditText) view).setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

}