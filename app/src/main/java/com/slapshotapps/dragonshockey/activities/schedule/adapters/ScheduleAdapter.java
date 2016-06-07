package com.slapshotapps.dragonshockey.activities.schedule.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.GameViewHolder>
{
    private SeasonSchedule schedule;

    public ScheduleAdapter(SeasonSchedule schedule)
    {
        this.schedule = schedule;
    }

    @Override
    public int getItemCount()
    {
        return schedule.numberOfGames();
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View gameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_game_details, parent, false);

        return new GameViewHolder(gameView);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position)
    {
        Game game = schedule.getAllGames().get(position);

        holder.setGameDate(game.gameTimeToDate());
        holder.setGameOpponent(game.opponent);
        holder.setGameResult( FormattingUtils.getGameScore(game.gameResult, game.opponent));

    }

    public static class GameViewHolder extends RecyclerView.ViewHolder
    {
        private TextView gameDay;
        private TextView gameTime;
        private TextView opponent;
        private TextView gameResult;

        public GameViewHolder(View itemView) {
            super(itemView);

            gameDay = (TextView)itemView.findViewById(R.id.game_day);
            gameTime = (TextView)itemView.findViewById(R.id.game_time);
            opponent = (TextView)itemView.findViewById(R.id.opponent);
            gameResult = (TextView)itemView.findViewById(R.id.game_result);
        }

        public void setGameDate(Date gameDate)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gameDate);

            final String gameDayStr = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US) +
                    " " + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) +
                    " " + calendar.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.US);

            gameDay.setText(gameDayStr);
            gameTime.setText(DateFormaters.getGameTime(gameDate));
        }

        public void setGameOpponent(String opponent)
        {
            this.opponent.setText(opponent);
        }

        public void setGameResult(String result)
        {
            gameResult.setText(result);
        }

    }
}
