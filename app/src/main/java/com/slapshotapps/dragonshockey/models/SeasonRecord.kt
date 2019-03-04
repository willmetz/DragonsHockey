package com.slapshotapps.dragonshockey.models

import androidx.annotation.Keep

/**
 * Created by willmetz on 8/20/16.
 */
@Keep
class SeasonRecord {

  var wins: Int = 0
  var losses: Int = 0
  var overtimeLosses: Int = 0
  var ties: Int = 0
}
