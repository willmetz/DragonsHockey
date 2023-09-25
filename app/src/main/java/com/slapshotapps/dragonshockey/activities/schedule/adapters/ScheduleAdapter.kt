package com.slapshotapps.dragonshockey.activities.schedule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.databinding.ViewGameDetailsBinding
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

        val binding = ViewGameDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = schedule.allGames[position]

        holder.setGameDate(game.gameTimeToDate())
        holder.setGameOpponent(game.opponent)
        holder.setGameResult(FormattingUtils.getGameScore(game.gameResult, game.opponent))
    }

    class GameViewHolder(private val binding: ViewGameDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setGameDate(gameDate: Date?) {
            val calendar = Calendar.getInstance()
            if (gameDate != null) {
                calendar.time = gameDate

                val gameDayStr = (calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
                        + " "
                        + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                        + " "
                        + FormattingUtils.getValueWithSuffix(calendar.get(Calendar.DAY_OF_MONTH)))

                binding.gameDay.text = gameDayStr
                binding.gameTime.text = DateFormatter.getGameTime(gameDate)
            }
        }

        fun setGameOpponent(opponent: String?) {
            binding.opponent.text = opponent
        }

        fun setGameResult(result: String) {
            binding.gameResult.text = result
        }
    }
}
