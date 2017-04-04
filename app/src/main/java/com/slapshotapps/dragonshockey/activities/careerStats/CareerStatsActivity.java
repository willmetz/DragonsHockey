package com.slapshotapps.dragonshockey.activities.careerStats;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.databinding.ActivityCareerStatsBinding;
import com.slapshotapps.dragonshockey.models.Player;

public class CareerStatsActivity extends AppCompatActivity {

    CareerStatsVM careerStatsVM;
    ActivityCareerStatsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dummy data
        Player player = new Player("F");
        player.firstName = "Bob";
        player.lastName = "Builder";
        player.playerID = 2;
        player.number = 99;

        careerStatsVM = new CareerStatsVM(player, null, null);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_career_stats);
        binding.setStats(careerStatsVM);
    }
}
