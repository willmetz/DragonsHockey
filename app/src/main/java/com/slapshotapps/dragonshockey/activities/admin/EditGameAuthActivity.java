package com.slapshotapps.dragonshockey.activities.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DateFormaters;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.activities.admin.listeners.AdminClickListener;
import com.slapshotapps.dragonshockey.activities.admin.listeners.EditGameClickListener;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.EditGameViewModel;
import com.slapshotapps.dragonshockey.databinding.ActivityEditGameAuthBinding;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.observables.AdminObserver;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class EditGameAuthActivity extends AppCompatActivity implements
        EditGameClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private ActivityEditGameAuthBinding binding;
    private FirebaseDatabase database;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game originalGame = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_game_auth);
        binding.setData(new EditGameViewModel(originalGame));
        binding.setListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        EditGameViewModel model = binding.getData();
        if (model.hasChanged()) {
            Game updatedGame = model.getGame();
            database.getReference().child(Config.GAME_RESULTS)
                    .child(String.valueOf(updatedGame.gameID))
                    .setValue(updatedGame.gameResult);
            database.getReference().child(Config.GAMES)
                    .child(String.valueOf(updatedGame.gameID))
                    .setValue(updatedGame);
        }

    }

    @Override
    public void onDateClick(Date gameDate) {

        EditGameViewModel model = binding.getData();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getGameDate());
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, dayOfMonth);
        dialog.show();
    }

    @Override
    public void onTimeClick(Date gameDate) {
        EditGameViewModel model = binding.getData();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getGameDate());

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, this, hour, minute, false);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditGameViewModel model = binding.getData();
        Date originalGameDate = model.getGameDate();

        Calendar newGameDate = Calendar.getInstance();
        newGameDate.setTime(originalGameDate);

        newGameDate.set(Calendar.YEAR, year);
        newGameDate.set(Calendar.MONTH, month);
        newGameDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        model.setGameDate(newGameDate.getTime());

        binding.setData(model);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditGameViewModel model = binding.getData();
        Date originalGameDate = model.getGameDate();

        Calendar newGameDate = Calendar.getInstance();
        newGameDate.setTime(originalGameDate);

        newGameDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        newGameDate.set(Calendar.MINUTE, minute);

        model.setGameDate(newGameDate.getTime());

        binding.setData(model);
    }

    @Override
    public void onEditStatsClick() {
        Toast.makeText(this, "Edit Stats!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearGameClick() {
        EditGameViewModel model = binding.getData();

        subscription = AdminObserver.getPlayerStatsKey(database, model.getGame().gameID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer gameStatsID) {
                        EditGameViewModel model = binding.getData();
                        deleteGameAndStats(gameStatsID, model.getGame().gameID);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(EditGameAuthActivity.this, "Unable to delete game", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void deleteGameAndStats(int statsID, int gameID) {
        database.getReference().child(Config.GAME_RESULTS)
                .child(String.valueOf(gameID))
                .removeValue();

        if (gameID != AdminObserver.NO_STATS_FOUND) {
            database.getReference().child(Config.GAME_STATS)
                    .child(String.valueOf(statsID))
                    .removeValue();
        }

        Toast.makeText(this, "Removed game", Toast.LENGTH_SHORT).show();
        finish();
    }
}
