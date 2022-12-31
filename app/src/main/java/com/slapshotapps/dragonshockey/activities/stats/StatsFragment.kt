package com.slapshotapps.dragonshockey.activities.stats

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.utils.ProgressBarUtils
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.activities.stats.adapters.PlayerStatsVM
import com.slapshotapps.dragonshockey.activities.stats.adapters.StatsAdapter
import com.slapshotapps.dragonshockey.databinding.ActivityStatsBinding
import com.slapshotapps.dragonshockey.dialogs.StatSortSelection
import com.slapshotapps.dragonshockey.dialogs.StatsSortDialogFragment
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.models.PlayerStats
import com.slapshotapps.dragonshockey.observables.RosterObserver
import com.slapshotapps.dragonshockey.observables.StatsObserver
import com.slapshotapps.dragonshockey.utils.DragonsHockeyIntents
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StatsFragment : HockeyFragment(), PlayerStatsVM.PlayerStatsVMListener, StatsSortDialogFragment.StatsSortSelectionListener {

    private var statsSubscription: Subscription? = null
    private var statsSortDialogFragment: StatsSortDialogFragment? = null
    private var adapter: StatsAdapter? = null
    private var prefsManager: UserPrefsManager? = null

    private var _binding : ActivityStatsBinding? = null
    private val binding get() = _binding!!

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
                    binding.progressBar.animate().alpha(1f)
                    ProgressBarUtils.hideProgressBar(binding.progressBar)
                })
    }

    override fun noCredentialsOnResume() {
        binding.progressBar.animate().alpha(1f)
        ProgressBarUtils.hideProgressBar(binding.progressBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.STATS_SELECTED)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ActivityStatsBinding.inflate(inflater, container, false)

        prefsManager = UserPrefsManager(requireContext())

        val manager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.statsRecyclerView.layoutManager = manager

        setupMenu()

        return binding.root
    }

    private fun setupMenu(){
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_stats, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sort -> {
                        showSortOptionsDialog()
                        return true
                    }
                    else -> false
                }

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        ProgressBarUtils.displayProgressBar(binding.progressBar)
        super.onResume()
    }

    private fun showStats(playerStats: List<PlayerStats>) {
        binding.progressBar.alpha = 0f
        ProgressBarUtils.hideProgressBar(binding.progressBar)
        adapter = StatsAdapter(playerStats, this@StatsFragment)

        adapter?.updateSortOrder(prefsManager?.statSortPreference ?: StatSortSelection.Points)
        binding.statsRecyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()

        statsSubscription?.unsubscribe()
        statsSubscription = null

        statsSortDialogFragment?.dismiss()
        statsSortDialogFragment = null

    }

    override fun onViewPLayerStats(playerStats: PlayerStats) {
        val action = StatsFragmentDirections.actionStatsFragmentToCareerStatsFragment(playerStats)
        findNavController().navigate(action)
    }

    private fun showSortOptionsDialog() {
        statsSortDialogFragment = StatsSortDialogFragment.newInstance(prefsManager?.statSortPreference ?: StatSortSelection.Points)
        statsSortDialogFragment?.setListener(this)

        statsSortDialogFragment?.show(childFragmentManager, "tag")
    }

    override fun onSortOptionSelected(sortSelection: StatSortSelection) {
        Log.d("Sort Option", String.format("Received sort option back: %s", sortSelection.name))

        prefsManager?.statSortPreference = sortSelection
        adapter?.updateSortOrder(sortSelection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
