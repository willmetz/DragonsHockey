package com.slapshotapps.dragonshockey.bindingAdapters;

import androidx.databinding.BindingAdapter;
import android.view.View;

public class FadeInBindingAdapter {

    @BindingAdapter("fadeInView")
    public static void fadeInView(View view, boolean fadeInView) {
        if (fadeInView) {
            view.animate().alpha(1f).setDuration(500);
        } else {
            view.animate().alpha(0f).setDuration(500);
        }
    }
}
