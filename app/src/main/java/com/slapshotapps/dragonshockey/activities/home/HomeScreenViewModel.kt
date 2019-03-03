package com.slapshotapps.dragonshockey.activities.home

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.slapshotapps.dragonshockey.BR
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.DateFormaters
import com.slapshotapps.dragonshockey.Utils.FormattingUtils
import com.slapshotapps.dragonshockey.models.HomeContents
import java.util.*


@Keep
class HomeScreenViewModel(contents: HomeContents?) : BaseObservable() {
  private var homeContents: HomeContents? = null


  @get:Bindable
  var isDataReady: Boolean = false
    set(value) {
      field = value
      notifyPropertyChanged(BR.dataReady);
    }


  val wins: String
    get() = if (homeContents?.seasonRecord == null) {
      ""
    } else {
      homeContents?.seasonRecord?.wins.toString()
    }

  val losses: String
    get() = if (homeContents?.seasonRecord == null) {
      ""
    } else {
      homeContents?.seasonRecord?.losses.toString()
    }

  val overtimeLosses: String
    get() = if (homeContents?.seasonRecord == null) {
      ""
    } else {
      homeContents?.seasonRecord?.overtimeLosses.toString()
    }

  val ties: String
    get() = if (homeContents?.seasonRecord == null) {
      ""
    } else {
      homeContents?.seasonRecord?.ties.toString()
    }

  init {
    if (contents == null) {
      homeContents = HomeContents()
      isDataReady = false
    } else {
      homeContents = contents
      isDataReady = true
    }
  }


  fun getNextGameTime(context: Context): String {
    if (homeContents!!.nextGame == null) {
      return context.getString(R.string.no_more_games)
    }

    val date = homeContents!!.nextGame.gameTimeToDate()

    if (date != null) {
      val calendar = Calendar.getInstance()
      calendar.time = date

      return if (DateUtils.isToday(date.time)) {

        context.getString(R.string.today_gameday, DateFormaters.getGameTime(date),
            if (homeContents!!.nextGame.home) "Home" else "Guest")
      } else {
        context.getString(R.string.gameday, DateFormaters.getGameDateTime(date),
            if (homeContents!!.nextGame.home) "Home" else "Guest")
      }
    }

    return context.getString(R.string.no_more_games)
  }

  fun getLastGameResult(context: Context): String {
    if (homeContents!!.lastGame != null && homeContents!!.lastGame.gameResult != null) {

      val lastGame = homeContents!!.lastGame
      val gameResultString = FormattingUtils.getGameResultAsString(
          homeContents!!.lastGame.gameResult)
      return String.format(context.getString(R.string.last_game_score),
          lastGame.gameResult?.dragonsScore, lastGame.opponent,
          lastGame.gameResult?.opponentScore, gameResultString)
    } else return if (homeContents!!.lastGame != null) {
      context.getString(R.string.update_pending)
    } else {
      ""
    }
  }

  fun showLastGameInfo(): Int {
    return if (homeContents!!.lastGame == null) View.INVISIBLE else View.VISIBLE
  }
}
