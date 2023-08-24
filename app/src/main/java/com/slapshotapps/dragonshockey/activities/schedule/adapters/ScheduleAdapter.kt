package com.slapshotapps.dragonshockey.activities.schedule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.DateFormatter
import com.slapshotapps.dragonshockey.utils.FormattingUtils
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import java.util.*

/**
 *
 */
class ScheduleAdapter(
        private val schedule: SeasonSchedule) : RecyclerView.Adapter<ScheduleAdapter.GameViewHolder>() {

    override fun getItemCount(): Int {
        return schedule.numberOfGames()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {

        val gameView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_game_details, parent, false)

        return GameViewHolder(gameView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = schedule.allGames[position]

        holder.setGameDate(game.gameTimeToDate())
        holder.setGameOpponent(game.opponent)
        holder.setGameResult(FormattingUtils.getGameScore(game.gameResult, game.opponent))
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameDay: TextView
        private val gameTime: TextView
        private val opponent: TextView
        private val gameResult: TextView

        init {

            gameDay = itemView.findViewById<View>(R.id.game_day) as TextView
            gameTime = itemView.findViewById<View>(R.id.game_time) as TextView
            opponent = itemView.findViewById<View>(R.id.opponent) as TextView
            gameResult = itemView.findViewById<View>(R.id.game_result) as TextView
        }

        fun setGameDate(gameDate: Date?) {
            val calendar = Calendar.getInstance()
            if (gameDate != null) {
                calendar.time = gameDate

                val gameDayStr = (calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
                        + " "
                        + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                        + " "
                        + FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH)))

                gameDay.text = gameDayStr
                gameTime.text = DateFormatter.getGameTime(gameDate)
            }
        }

        fun setGameOpponent(opponent: String?) {
            this.opponent.text = opponent
        }

        fun setGameResult(result: String) {
            gameResult.text = result
        }
    }
}
