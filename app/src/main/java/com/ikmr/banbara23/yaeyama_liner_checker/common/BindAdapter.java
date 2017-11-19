package com.ikmr.banbara23.yaeyama_liner_checker.common;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class BindAdapter {
    @BindingAdapter("srcCompat")
    public static void srcCompat(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }
}
