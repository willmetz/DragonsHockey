package com.slapshotapps.dragonshockey.activities.admin

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.activities.admin.adapter.AdminEditsStatsAdapter
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminStatsViewModel
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminStatsViewModel.AdminStatsVMBuilder
import com.slapshotapps.dragonshockey.databinding.ActivityEditStatsAuthBinding
import com.slapshotapps.dragonshockey.models.GameStats
import com.slapshotapps.dragonshockey.models.PlayerGameStats
import com.slapshotapps.dragonshockey.observables.AdminObserver
import com.slapshotapps.dragonshockey.utils.DragonsHockeyIntents
import com.slapshotapps.dragonshockey.utils.RosterUtils
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class EditStatsActivity : AppCompatActivity() {
    private var binding: ActivityEditStatsAuthBinding? = null
    private var subscription: Subscription? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var playerGameStats: PlayerGameStats? = null
    private var adminEditsStatsAdapter: AdminEditsStatsAdapter? = null
    private var gameID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val localBinding = ActivityEditStatsAuthBinding.inflate(layoutInflater)
        binding = localBinding
        setContentView(localBinding.root)
        setSupportActionBar(localBinding.toolbar)

        localBinding.editStatsContainer.adminStatsRecyclerView.layoutManager = LinearLayoutManager(this)
        firebaseDatabase = FirebaseDatabase.getInstance()
        gameID = intent.getIntExtra(DragonsHockeyIntents.EXTRA_GAME_ID, 0)
    }

    override fun onResume() {
        super.onResume()
        if (adminEditsStatsAdapter == null) {
            subscription = AdminObserver.getPlayerStatsForGame(firebaseDatabase, gameID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ stats: PlayerGameStats? ->
                        playerGameStats = stats
                        adminEditsStatsAdapter = AdminEditsStatsAdapter(viewModel)
                        binding?.editStatsContainer?.adminStatsRecyclerView?.adapter = adminEditsStatsAdapter
                    }) { _: Throwable? ->
                        AlertDialog.Builder(this@EditStatsActivity).setMessage(
                                "Unable to retrieve stats information")
                                .setPositiveButton("Ok") { _: DialogInterface?, _: Int -> finish() }
                                .show()
                    }
        }
    }

    override fun onPause() {
        super.onPause()
        if (subscription != null) {
            subscription!!.unsubscribe()
            subscription = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (adminEditsStatsAdapter!!.statsChanged()) {
            val viewModel = adminEditsStatsAdapter!!.getPlayerStats()
            if (playerGameStats!!.isKeyValid) {
                firebaseDatabase!!.reference
                        .child(Config.GAME_STATS)
                        .child(playerGameStats!!.playerStatsKey!!)
                        .setValue(getGameStats(viewModel))
            } else {
                val newGameResultRef = firebaseDatabase!!.reference.child(Config.GAME_STATS).push()
                newGameResultRef.setValue(getGameStats(viewModel))
            }
        }
    }

    private val viewModel: ArrayList<AdminStatsViewModel>
        get() {
            val statsViewModel = ArrayList<AdminStatsViewModel>()
            playerGameStats?.let {
                it.players?.forEach { player ->
                    it.playerGameStats?.getPlayerStats(player.playerID)?.let { statsForPlayer ->
                        val viewModel = AdminStatsVMBuilder().playerName(
                                RosterUtils.getFullName(player))
                                .playerID(player.playerID)
                                .playerNumber(player.number)
                                .goals(statsForPlayer.goals)
                                .assists(statsForPlayer.assists)
                                .present(statsForPlayer.present)
                                .penaltyMinutes(statsForPlayer.penaltyMinutes)
                                .position(player.getPlayerPosition())
                                .goalsAgainst(statsForPlayer.goalsAgainst)
                                .build()
                        statsViewModel.add(viewModel)
                    }
                }
            }

            return statsViewModel
        }

    private fun getGameStats(adminStatsViewModelList: List<AdminStatsViewModel>): GameStats {
        val gameStats = GameStats()
        gameStats.gameID = gameID
        gameStats.gameStats = ArrayList()
        for (adminStatsViewModel in adminStatsViewModelList) {
            val stats = GameStats.Stats()
            stats.playerID = adminStatsViewModel.playerID
            stats.assists = Integer.valueOf(adminStatsViewModel.assists)
            stats.goals = Integer.valueOf(adminStatsViewModel.goals)
            stats.present = adminStatsViewModel.presence
            stats.penaltyMinutes = Integer.valueOf(adminStatsViewModel.penaltyMinutes)
            stats.goalsAgainst = Integer.valueOf(adminStatsViewModel.goalsAgainst)
            gameStats.gameStats?.add(stats)
        }
        return gameStats
    }
}