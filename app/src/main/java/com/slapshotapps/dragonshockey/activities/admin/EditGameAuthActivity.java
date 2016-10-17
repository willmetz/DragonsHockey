package com.slapshotapps.dragonshockey.activities.admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.EditGameViewModel;
import com.slapshotapps.dragonshockey.databinding.ActivityEditGameAuthBinding;
import com.slapshotapps.dragonshockey.models.Game;

public class EditGameAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game game = getIntent().getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME);

        ActivityEditGameAuthBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_game_auth);
        binding.setData(new EditGameViewModel(game));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
