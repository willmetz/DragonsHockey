package com.slapshotapps.dragonshockey.activities.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.activities.admin.listeners.EditGameClickListener;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminGameViewModel;
import com.slapshotapps.dragonshockey.databinding.ActivityEditGameAuthBinding;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameUpdateKeys;
import com.slapshotapps.dragonshockey.observables.AdminObserver;
import java.util.Calendar;
import java.util.Date;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EditGameActivity extends AppCompatActivity
    implements EditGameClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private ActivityEditGameAuthBinding binding;
    private FirebaseDatabase database;
    private Subscription subscription;
    private GameUpdateKeys keys;
    private Game originalGame;
    private boolean refreshData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            originalGame = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME);
            refreshData = false;
        } else {
            originalGame = null;
            keys = null;
            refreshData = true;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_game_auth);
        binding.setListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!refreshData) {
            getDataKeys(originalGame.gameID);
            binding.setData(new AdminGameViewModel(originalGame));
        } else {
            //get the game ID
            Game game = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME);

            subscription = AdminObserver.getGameUpdateInfo(database, game.gameID)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Game>() {
                    @Override
                    public void call(Game game) {
                        originalGame = game;
                        binding.setData(new AdminGameViewModel(originalGame));

                        getDataKeys(game.gameID);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(EditGameActivity.this, "Unable to get game data",
                            Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }

        AdminGameViewModel model = binding.getData();
        if (model.hasChanged()) {
            saveUpdates(model);
            refreshData = true;
        }
    }

    @Override
    public void onDateClick(Date gameDate) {

        AdminGameViewModel model = binding.getData();
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
        AdminGameViewModel model = binding.getData();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getGameDate());

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, this, hour, minute, false);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        AdminGameViewModel model = binding.getData();
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
        AdminGameViewModel model = binding.getData();
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
        startActivity(DragonsHockeyIntents.createEditGameStatsIntent(this, originalGame.gameID));
    }

    @Override
    public void onClearGameClick() {
        deleteGameAndStats();
    }

    private void saveUpdates(AdminGameViewModel model) {
        if (keys != null) {

            if (keys.gameKeyValid()) {
                database.getReference()
                    .child(Config.GAMES)
                    .child(keys.getGameKey())
                    .setValue(model.getGame());
            }

            if (keys.gameResultKeyValid()) {
                database.getReference()
                    .child(Config.GAME_RESULTS)
                    .child(keys.getGameResultKey())
                    .setValue(model.getGame().gameResult);
            } else {
                DatabaseReference newGameResultRef =
                    database.getReference().child(Config.GAME_RESULTS).push();
                newGameResultRef.setValue(model.getGame().gameResult);
            }

            super.onBackPressed();
        } else {
            showKeysNotAvailableAlert();
        }
    }

    private void deleteGameAndStats() {

        if (keys != null) {

            if (keys.gameResultKeyValid()) {
                database.getReference()
                    .child(Config.GAME_RESULTS)
                    .child(keys.getGameResultKey())
                    .removeValue();
            }

            if (keys.gameStatsKeyValid()) {
                database.getReference()
                    .child(Config.GAME_STATS)
                    .child(keys.getGameStatsKey())
                    .removeValue();
            }

            Toast.makeText(this, "Removed game", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Unable to remove game, keys not available", Toast.LENGTH_SHORT)
                .show();
        }
    }

    private void getDataKeys(int gameID) {

        subscription = AdminObserver.getGameKeys(database, gameID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<GameUpdateKeys>() {
                @Override
                public void call(GameUpdateKeys gameUpdateKeys) {
                    keys = gameUpdateKeys;
                }
            });
    }

    private void showKeysNotAvailableAlert() {
        new AlertDialog.Builder(this).setPositiveButton("OK",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //no-op
            }
        }).setMessage("Keys for data not available yet, exit anyways?").show();
    }
}
