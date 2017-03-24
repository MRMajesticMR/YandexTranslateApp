package ru.majestic.yandextranslateapp.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by arkadiy.zakharov on 28.11.2016.
 */

public class DimensionsConverter {

    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

}
