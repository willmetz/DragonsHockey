package com.slapshotapps.dragonshockey.activities.careerStats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
    private lateinit var binding: FragmentCareerStatsBinding

    private var careerStatsSubscription: Subscription? = null
    private var careerStatsAdapter: CareerStatsAdapter? = null

    private var currentSeasonStats: PlayerStats? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        actionBarListener!!.setTitle("Career Stats")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_career_stats, container, false)

        currentSeasonStats = CareerStatsFragmentArgs.fromBundle(arguments!!).currentSeasonStats

        careerStatsAdapter = CareerStatsAdapter()
        binding.careerStats.adapter = careerStatsAdapter
        binding.careerStats.layoutManager = LinearLayoutManager(context)
        binding.careerStats.addItemDecoration(
                RecyclerViewDivider(context, R.drawable.schedule_divider))
        binding.careerStats.addItemDecoration(
                StaticHeaderDecoration(careerStatsAdapter, binding.careerStats))

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        actionBarListener?.showProgressBar()

        careerStatsSubscription = CareerStatsObserver.getCareerStatsData(firebaseDatabase,
                currentSeasonStats!!.playerID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ careerStatsData ->

                    careerStatsVM = CareerStatsVM(careerStatsData.player, currentSeasonStats,
                            careerStatsData.seasonStats)
                    binding.stats = careerStatsVM
                    careerStatsAdapter?.updateStats(careerStatsVM!!.stats,
                            careerStatsData.player.getPlayerPosition())

                    actionBarListener?.hideProgressBar()
                }, { _ -> actionBarListener?.hideProgressBar() })
    }

    override fun onPause() {
        super.onPause()

        careerStatsSubscription?.unsubscribe()

        actionBarListener!!.hideProgressBar()
    }
}
