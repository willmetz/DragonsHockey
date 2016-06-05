package com.slapshotapps.dragonshockey.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.slapshotapps.dragonshockey.R;

import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 *
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.GameViewHolder>
{

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View gameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_game_details, parent, false);

        GameViewHolder viewHolder = new GameViewHolder(gameView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {

    }

    public static class GameViewHolder extends RecyclerView.ViewHolder
    {
        TextView gameDay;
        TextView gameTime;
        TextView opponent;
        TextView gameResult;

        public GameViewHolder(View itemView) {
            super(itemView);

            gameDay = (TextView)itemView.findViewById(R.id.game_day);
            gameTime = (TextView)itemView.findViewById(R.id.game_time);
            opponent = (TextView)itemView.findViewById(R.id.opponent);
            gameResult = (TextView)itemView.findViewById(R.id.game_result);
        }

        public void setGameDate(Date gameDate)
        {
            gameDay.setText("");
            gameTime.setText("");
        }

        public void setGameOpponent(String opponent)
        {
            this.opponent.setText(opponent);
        }

        public void setGameResult()
        {
            gameResult.setText("");
        }

    }
}
