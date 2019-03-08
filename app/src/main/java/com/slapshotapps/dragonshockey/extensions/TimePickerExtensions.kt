package com.slapshotapps.dragonshockey.extensions

import android.os.Build
import android.widget.TimePicker


fun TimePicker.setMilitaryTime(time: Int) {

  val hourToSet = time / 100
  val minuteToSet = time - (hourToSet * 100)

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    hour = hourToSet
    minute = minuteToSet
  } else {
    currentHour = hourToSet
    currentMinute = minuteToSet
  }
}