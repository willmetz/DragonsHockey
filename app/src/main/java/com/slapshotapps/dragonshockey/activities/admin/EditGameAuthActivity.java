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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game game = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME);

        ActivityEditGameAuthBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_game_auth);
        binding.setData(new EditGameViewModel(game));
        binding.setListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onDateClick(Date gameDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gameDate);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, dayOfMonth);
        dialog.show();
    }

    @Override
    public void onTimeClick(Date gameDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gameDate);

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, this, hour, minute, false);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
