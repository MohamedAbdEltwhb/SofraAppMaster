package com.example.mm.sofraappmaster.ui.custom.radio_button_custom_font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.example.mm.sofraappmaster.R;

@SuppressLint("AppCompatCustomView")
public class RadioButtonCustomFont extends RadioButton {
    String customFont;

    public RadioButtonCustomFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public RadioButtonCustomFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        style(context, attrs);
    }


    private void style(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadioButtonCustomFont);
        int cf = a.getInteger(R.styleable.RadioButtonCustomFont_fontName, 0);
        int fontName = 0;
        switch (cf) {
            case 1:
                fontName = R.string.cairo_extra_light;
                break;
            case 2:
                fontName = R.string.cairo_light;
                break;
            case 3:
                fontName = R.string.cairo_regular;
                break;
            default:
                fontName = R.string.cairo_regular;
                break;
        }

        customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + customFont + ".ttf");
        setTypeface(tf);
        a.recycle();
    }
}
