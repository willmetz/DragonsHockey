package com.slapshotapps.dragonshockey.activities.stats

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.ProgressBarUtils
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.activities.stats.adapters.PlayerStatsVM
import com.slapshotapps.dragonshockey.activities.stats.adapters.StatsAdapter
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection
import com.slapshotapps.dragonshockey.dialogs.StatsSortDialogFragment
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.models.PlayerStats
import com.slapshotapps.dragonshockey.observables.RosterObserver
import com.slapshotapps.dragonshockey.observables.StatsObserver
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StatsFragment : HockeyFragment(), PlayerStatsVM.PlayerStatsVMListener, StatsSortDialogFragment.StatsSortSelectionListener {

    private var recyclerView: RecyclerView? = null
    private var errorLoading: TextView? = null

    private var statsSubscription: Subscription? = null

    private var statsSortDialogFragment: StatsSortDialogFragment? = null

    private var adapter: StatsAdapter? = null

    private lateinit var prefsManager: UserPrefsManager

    private var progressBar: ProgressBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val listener = actionBarListener
        listener?.setTitle(getString(R.string.stats_title))
    }

    override fun onResumeWithCredentials() {
        statsSubscription = RosterObserver.GetRoster(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { players -> StatsObserver.getPlayerStats(firebaseDatabase, players) }
                .subscribe({ this.showStats(it) }, { _ ->
                    errorLoading!!.animate().alpha(1f)
                    ProgressBarUtils.hideProgressBar(progressBar!!)
                })
    }

    override fun noCredentialsOnResume() {
        errorLoading!!.animate().alpha(1f)
        ProgressBarUtils.hideProgressBar(progressBar!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.STATS_SELECTED)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_stats, container, false)

        prefsManager = UserPrefsManager(context!!)

        recyclerView = view.findViewById(R.id.stats_recycler_view)
        errorLoading = view.findViewById(R.id.unable_to_load)
        progressBar = view.findViewById(R.id.progress_bar)

        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView!!.layoutManager = manager

        setHasOptionsMenu(true)

        return view
    }

    override fun onResume() {
        ProgressBarUtils.displayProgressBar(progressBar!!)
        super.onResume()
    }

    private fun showStats(playerStats: List<PlayerStats>) {
        errorLoading!!.alpha = 0f
        ProgressBarUtils.hideProgressBar(progressBar!!)
        adapter = StatsAdapter(playerStats, this@StatsFragment)

        adapter!!.updateSortOrder(prefsManager.statSortPreference)
        recyclerView!!.adapter = adapter
    }

    override fun onPause() {
        super.onPause()

        statsSubscription?.unsubscribe()
        statsSubscription = null

        statsSortDialogFragment?.dismiss()
        statsSortDialogFragment = null

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_stats, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_sort) {
            showSortOptionsDialog()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewPLayerStats(playerStats: PlayerStats) {
        val action = StatsFragmentDirections.actionStatsFragmentToCareerStatsFragment(playerStats)
        findNavController(this).navigate(action)
    }

    private fun showSortOptionsDialog() {
        val fragmentManager = fragmentManager
        statsSortDialogFragment = StatsSortDialogFragment.newInstance(prefsManager.statSortPreference)
        statsSortDialogFragment!!.setListener(this)

        statsSortDialogFragment!!.show(fragmentManager!!, "tag")
    }

    override fun onSortOptionSelected(sortSelection: StatSortSelection) {
        Log.d("Sort Option", String.format("Received sort option back: %s", sortSelection.name))

        prefsManager.statSortPreference = sortSelection

        if (adapter != null) {
            adapter!!.updateSortOrder(sortSelection)
        }
    }
}
