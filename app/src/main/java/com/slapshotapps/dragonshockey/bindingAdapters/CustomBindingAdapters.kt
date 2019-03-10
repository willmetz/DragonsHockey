package com.slapshotapps.dragonshockey.bindingAdapters

import android.view.View
import android.widget.TimePicker
import androidx.databinding.BindingAdapter
import com.slapshotapps.dragonshockey.extensions.setMilitaryTime


@BindingAdapter("fadeInView")
fun fadeInView(view: View, fadeInView: Boolean) {
    if (fadeInView) {
        view.animate().alpha(1f).duration = 500
    } else {
        view.animate().alpha(0f).duration = 500
    }
}

@BindingAdapter("timeOfDay")
fun setTimeOfDay(timePicker: TimePicker, militaryTime: Int) {
    timePicker.setMilitaryTime(militaryTime)
}