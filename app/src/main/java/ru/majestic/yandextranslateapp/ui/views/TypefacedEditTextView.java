package ru.majestic.yandextranslateapp.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import ru.majestic.yandextranslateapp.R;

/**
 * Created by arkadiy.zakharov on 17.01.2017.
 */

public class TypefacedEditTextView extends AppCompatEditText {

    public TypefacedEditTextView(Context context) {
        super(context);
    }

    public TypefacedEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initFont(context, attrs);
    }

    public TypefacedEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initFont(context, attrs);
    }

    //===== <PRIVATE_METHODS> =====

    /**
     * Инициализирует шрифт
     *
     * @param context
     * @param attrs
     */
    private void initFont(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypefacedEditTextView, 0, 0);

        try {
            setTypeface(Typeface.createFromAsset(context.getAssets(), a.getString(R.styleable.TypefacedEditTextView_font)));
        } finally {
            a.recycle();
        }
    }
    //===== </PRIVATE_METHODS> =====
}
