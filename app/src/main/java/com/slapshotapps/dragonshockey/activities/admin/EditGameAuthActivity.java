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

import java.util.Calendar;
import java.util.Date;

import timber.log.Timber;

public class EditGameAuthActivity extends AppCompatActivity implements
        EditGameClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener
{

    private ActivityEditGameAuthBinding binding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game originalGame = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_game_auth);
        binding.setData(new EditGameViewModel(originalGame));
        binding.setListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        EditGameViewModel model = binding.getData();
        if(model.hasChanged()){
            Game updatedGame = model.getGame();
            databaseReference.child(Config.GAME_RESULTS)
                    .child(String.valueOf(updatedGame.gameID))
                    .setValue(updatedGame.gameResult);
            databaseReference.child(Config.GAMES)
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
}
