package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep

/**
 * A custom object for the home screen contents
 */
@Keep
class HomeContents {

  var lastGame: Game? = null
  var nextGame: Game? = null
  var seasonRecord =  SeasonRecord()
}
