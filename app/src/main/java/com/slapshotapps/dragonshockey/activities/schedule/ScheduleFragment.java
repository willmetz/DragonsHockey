package com.slapshotapps.dragonshockey.activities.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.FirebaseDatabase;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider;
import com.slapshotapps.dragonshockey.activities.ActionBarListener;
import com.slapshotapps.dragonshockey.activities.HockeyFragment;
import com.slapshotapps.dragonshockey.activities.schedule.adapters.ScheduleAdapter;
import com.slapshotapps.dragonshockey.models.SeasonSchedule;
import com.slapshotapps.dragonshockey.observables.ScheduleObserver;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * An activity to display the season schedule
 */
public class ScheduleFragment extends HockeyFragment {

    private Subscription hockeyScheduleSubscription;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule, container, false);

        recyclerView = view.findViewById(R.id.schedule_recycler_view);

        LinearLayoutManager layoutManager =
            new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
            new RecyclerViewDivider(getContext(), R.drawable.schedule_divider));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ActionBarListener listener = getActionBarListener();
        if(listener != null){
            listener.setTitle(getString(R.string.schedule_title));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(
                (Func1<SeasonSchedule, Observable<SeasonSchedule>>) schedule -> ScheduleObserver.getScheduleWithResults(firebaseDatabase, schedule))
            .subscribe(schedule -> recyclerView.setAdapter(new ScheduleAdapter(schedule)));
    }

    @Override
    public void onPause() {
        super.onPause();

        if (hockeyScheduleSubscription != null) {
            hockeyScheduleSubscription.unsubscribe();
            hockeyScheduleSubscription = null;
        }
    }
}
