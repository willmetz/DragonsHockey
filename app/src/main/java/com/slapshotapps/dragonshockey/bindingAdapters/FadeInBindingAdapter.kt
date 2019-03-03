package com.slapshotapps.dragonshockey.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("fadeInView")
fun fadeInView(view: View, fadeInView: Boolean) {
  if (fadeInView) {
    view.animate().alpha(1f).duration = 500
  } else {
    view.animate().alpha(0f).duration = 500
  }
}
