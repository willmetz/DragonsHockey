package com.slapshotapps.dragonshockey.activities.careerStats;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration;
import com.slapshotapps.dragonshockey.databinding.ActivityCareerStatsBinding;
import com.slapshotapps.dragonshockey.models.Player;

import java.util.ArrayList;

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


        //dummy data
        ArrayList<PlayerSeasonStatsVM> dummyStats = new ArrayList<>();
        PlayerSeasonStatsVM data = new PlayerSeasonStatsVM("Fall '16");
        data.assists = 2;
        data.gamesPlayed = 9;
        data.goals = 8;
        dummyStats.add(data);
        data = new PlayerSeasonStatsVM("Fall '16");
        data.assists = 9;
        data.gamesPlayed = 9;
        data.goals = 5;
        dummyStats.add(data);
        data = new PlayerSeasonStatsVM("Current");
        data.assists = 0;
        data.gamesPlayed = 3;
        data.goals = 1;
        dummyStats.add(data);


        CareerStatsAdapter adapter = new CareerStatsAdapter(dummyStats);
        binding.careerStats.setAdapter(adapter);
        binding.careerStats.setLayoutManager( new LinearLayoutManager(this));
        binding.careerStats.addItemDecoration(new RecyclerViewDivider(this, R.drawable.schedule_divider));
        binding.careerStats.addItemDecoration(new StaticHeaderDecoration(adapter, binding.careerStats));
    }
}
