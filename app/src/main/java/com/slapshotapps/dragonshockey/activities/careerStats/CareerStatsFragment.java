package com.slapshotapps.dragonshockey.activities.careerStats;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration;
import com.slapshotapps.dragonshockey.activities.HockeyFragment;
import com.slapshotapps.dragonshockey.databinding.FragmentCareerStatsBinding;
import com.slapshotapps.dragonshockey.models.PlayerStats;
import com.slapshotapps.dragonshockey.observables.CareerStatsObserver;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CareerStatsFragment extends HockeyFragment {

    private CareerStatsVM careerStatsVM;
    private FragmentCareerStatsBinding binding;

    private Subscription careerStatsSubscription;
    private CareerStatsAdapter careerStatsAdapter;

    private PlayerStats currentSeasonStats;

    public static CareerStatsFragment getInstance(PlayerStats currentSeasonStats) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(DragonsHockeyIntents.EXTRA_PLAYER_STATS, currentSeasonStats);

        CareerStatsFragment fragment = new CareerStatsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        getActionBarListener().setTitle("Career Stats");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_career_stats, container, false);

        //TODO
        currentSeasonStats = CareerStatsFragmentArgs.fromBundle(getArguments()).getCurrentSeasonStats();

        careerStatsAdapter = new CareerStatsAdapter();
        binding.careerStats.setAdapter(careerStatsAdapter);
        binding.careerStats.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.careerStats.addItemDecoration(
            new RecyclerViewDivider(getContext(), R.drawable.schedule_divider));
        binding.careerStats.addItemDecoration(
            new StaticHeaderDecoration(careerStatsAdapter, binding.careerStats));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        getActionBarListener().showProgressBar();

        careerStatsSubscription = CareerStatsObserver.getCareerStatsData(getFirebaseDatabase(),
            currentSeasonStats.playerID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(careerStatsData -> {
                careerStatsVM = new CareerStatsVM(careerStatsData.player, currentSeasonStats,
                    careerStatsData.seasonStats);
                binding.setStats(careerStatsVM);
                careerStatsAdapter.updateStats(careerStatsVM.getStats(),
                    careerStatsData.player.getPosition());
                getActionBarListener().hideProgressBar();
            }, error -> {
                getActionBarListener().hideProgressBar();
            });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (careerStatsSubscription != null) {
            careerStatsSubscription.unsubscribe();
            careerStatsSubscription = null;
        }

        getActionBarListener().hideProgressBar();
    }
}
