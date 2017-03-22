package ru.majestic.yandextranslateapp.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import ru.majestic.yandextranslateapp.R;


/**
 * Created by arkadiy.zakharov on 17.01.2017.
 */

public class TypefacedTextView extends AppCompatTextView {

    public TypefacedTextView(Context context) {
        super(context);
    }

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initFont(context, attrs);
    }

    public TypefacedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypefacedTextView, 0, 0);

        try {
            setTypeface(Typeface.createFromAsset(context.getAssets(), a.getString(R.styleable.TypefacedTextView_font)));
        } finally {
            a.recycle();
        }
    }
    //===== </PRIVATE_METHODS> =====
}
