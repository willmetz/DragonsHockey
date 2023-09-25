package com.slapshotapps.dragonshockey.activities.careerStats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.RecyclerViewDivider
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.databinding.FragmentCareerStatsBinding
import com.slapshotapps.dragonshockey.models.PlayerStats
import com.slapshotapps.dragonshockey.observables.CareerStatsObserver
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CareerStatsFragment : HockeyFragment() {

    private var careerStatsVM: CareerStatsVM? = null
    private var _binding: FragmentCareerStatsBinding? = null
    private val binding get() = _binding!!

    private var careerStatsSubscription: Subscription? = null
    private var careerStatsAdapter: CareerStatsAdapter? = null

    private var currentSeasonStats: PlayerStats? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        actionBarListener?.setTitle("Career Stats")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentCareerStatsBinding.inflate(inflater, container, false)

        currentSeasonStats = CareerStatsFragmentArgs.fromBundle(requireArguments()).currentSeasonStats

        val adapter = CareerStatsAdapter()
        binding.careerStats.adapter = adapter
        binding.careerStats.layoutManager = LinearLayoutManager(context)
        binding.careerStats.addItemDecoration(
                RecyclerViewDivider(context, R.drawable.schedule_divider))
        binding.careerStats.addItemDecoration(
                StaticHeaderDecoration(adapter, binding.careerStats))

        careerStatsAdapter = careerStatsAdapter
        return binding.root
    }

    override fun onResumeWithCredentials() {
        careerStatsSubscription = CareerStatsObserver.getCareerStatsData(firebaseDatabase,
                currentSeasonStats!!.playerID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ careerStatsData ->

                    careerStatsVM = CareerStatsVM(careerStatsData.player, currentSeasonStats,
                            careerStatsData.seasonStats)
                    binding.playerName.text = careerStatsVM?.playerName.orEmpty()
                    binding.playerNumber.text = careerStatsVM?.playerNumber.orEmpty()
                    binding.playerPosition.text = careerStatsVM?.playerPosition.orEmpty()

                    careerStatsAdapter?.updateStats(careerStatsVM!!.stats,
                            careerStatsData.player.getPlayerPosition())

                    actionBarListener?.hideProgressBar()
                }, { _ -> actionBarListener?.hideProgressBar() })
    }

    override fun noCredentialsOnResume() {
        Toast.makeText(this@CareerStatsFragment.context, R.string.error_loading, Toast.LENGTH_LONG)
                .show()
    }

    override fun onResume() {
        actionBarListener?.showProgressBar()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

        careerStatsSubscription?.unsubscribe()

        actionBarListener!!.hideProgressBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
