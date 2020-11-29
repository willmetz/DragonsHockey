package com.slapshotapps.dragonshockey.activities.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.activities.schedule.adapters.ScheduleAdapter
import com.slapshotapps.dragonshockey.models.SeasonSchedule
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * An activity to display the season schedule
 */
class ScheduleFragment : HockeyFragment() {

    private var hockeyScheduleSubscription: Subscription? = null

    private var recyclerView: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        actionBarListener?.setTitle(getString(R.string.schedule_title))
    }

    override fun onResumeWithCredentials() {
        hockeyScheduleSubscription = ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { schedule: SeasonSchedule ->
                    ScheduleObserver.getScheduleWithResults(firebaseDatabase, schedule)
                }
                .subscribe { schedule -> recyclerView!!.adapter = ScheduleAdapter(schedule) }
    }

    override fun noCredentialsOnResume() {
        Toast.makeText(this@ScheduleFragment.context, R.string.error_loading, Toast.LENGTH_LONG)
                .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.SCHEDULE_SELECTED)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_schedule, container, false)

        recyclerView = view.findViewById(R.id.schedule_recycler_view)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.addItemDecoration(
                RecyclerViewDivider(context, R.drawable.schedule_divider))

        return view
    }

    override fun onPause() {
        super.onPause()

        if (hockeyScheduleSubscription != null) {
            hockeyScheduleSubscription!!.unsubscribe()
            hockeyScheduleSubscription = null
        }
    }
}
