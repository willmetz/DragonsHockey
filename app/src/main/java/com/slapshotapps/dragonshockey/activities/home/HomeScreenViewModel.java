package com.slapshotapps.dragonshockey.activities.home;

import android.content.Context;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.View;
import com.slapshotapps.dragonshockey.BR;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.FormattingUtils;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.HomeContents;
import java.util.Calendar;
import java.util.Date;

@Keep
public class HomeScreenViewModel extends BaseObservable {
    private HomeContents homeContents;
    private boolean isDataReady;

    public HomeScreenViewModel(@Nullable HomeContents homeContents) {
        if (homeContents == null) {
            this.homeContents = new HomeContents();
            setDataReady(true);
        } else {
            this.homeContents = homeContents;
            setDataReady(false);
        }
    }

    @Bindable
    public boolean isDataReady() {
        return isDataReady;
    }

    public void setDataReady(boolean dataReady) {
        isDataReady = true;
        notifyPropertyChanged(BR.dataReady);
    }

    public String getNextGameTime(Context context) {
        if (homeContents.nextGame == null) {
            return context.getString(R.string.no_more_games);
        }

        Date date = homeContents.nextGame.gameTimeToDate();

        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (DateUtils.isToday(date.getTime())) {

                return context.getString(R.string.today_gameday, DateFormaters.getGameTime(date),
                    homeContents.nextGame.home ? "Home" : "Guest");
            } else {
                return context.getString(R.string.gameday, DateFormaters.getGameDateTime(date),
                    homeContents.nextGame.home ? "Home" : "Guest");
            }
        }

        return context.getString(R.string.no_more_games);
    }

    public String getLastGameResult(Context context) {
        if (homeContents.lastGame != null && homeContents.lastGame.gameResult != null) {

            final Game lastGame = homeContents.lastGame;
            String gameResultString =
                FormattingUtils.getGameResultAsString(homeContents.lastGame.gameResult);
            return String.format(context.getString(R.string.last_game_score),
                lastGame.gameResult.dragonsScore, lastGame.opponent,
                lastGame.gameResult.opponentScore, gameResultString);
        } else if (homeContents.lastGame != null) {
            return context.getString(R.string.update_pending);
        } else {
            return "";
        }
    }

    public int showLastGameInfo() {
        return homeContents.lastGame == null ? View.INVISIBLE : View.VISIBLE;
    }

    public String getWins() {
        if (homeContents.seasonRecord == null) {
            return "";
        } else {
            return String.valueOf(homeContents.seasonRecord.wins);
        }
    }

    public String getLosses() {
        if (homeContents.seasonRecord == null) {
            return "";
        } else {
            return String.valueOf(homeContents.seasonRecord.losses);
        }
    }

    public String getOvertimeLosses() {
        if (homeContents.seasonRecord == null) {
            return "";
        } else {
            return String.valueOf(homeContents.seasonRecord.overtimeLosses);
        }
    }

    public String getTies() {
        if (homeContents.seasonRecord == null) {
            return "";
        } else {
            return String.valueOf(homeContents.seasonRecord.ties);
        }
    }
}
