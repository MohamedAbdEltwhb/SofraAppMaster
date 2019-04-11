package com.example.mm.sofraappmaster.ui.custom.custom_fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CairoRegularText extends TextView{

    public CairoRegularText(Context context) {
        super(context);
        initFont();
    }

    public CairoRegularText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public CairoRegularText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    private void initFont() {

        if (!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/cairo_regular.ttf");
            setTypeface(typeface);
        }
    }
}
