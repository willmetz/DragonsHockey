package com.slapshotapps.dragonshockey.activities.admin

import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.activities.admin.listeners.EditGameClickListener
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminGameViewModel
import com.slapshotapps.dragonshockey.databinding.ActivityEditGameAuthBinding
import com.slapshotapps.dragonshockey.models.Game
import com.slapshotapps.dragonshockey.models.GameUpdateKeys
import com.slapshotapps.dragonshockey.observables.AdminObserver
import com.slapshotapps.dragonshockey.utils.DragonsHockeyIntents
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.Date

class EditGameActivity : AppCompatActivity(){
    private var binding: ActivityEditGameAuthBinding? = null
    private var database: FirebaseDatabase? = null
    private var subscription: Subscription? = null
    private var keys: GameUpdateKeys? = null
    private var originalGame: Game? = null
    private var refreshData = false
    private var gameViewModel : AdminGameViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            originalGame = intent.getParcelableExtra(DragonsHockeyIntents.EXTRA_GAME)
            refreshData = false
        } else {
            originalGame = null
            keys = null
            refreshData = true
        }
        ActivityEditGameAuthBinding.inflate(layoutInflater).let {
            setContentView(it.root)
            setSupportActionBar(it.toolbar)
            binding = it
        }
        database = FirebaseDatabase.getInstance()
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        if (!refreshData) {
            originalGame?.let {
                getDataKeys(it.gameID)
                setInitialData(it)
            }
        } else {
            //get the game ID
            val game = intent.getParcelableExtra<Game>(DragonsHockeyIntents.EXTRA_GAME)
            subscription = AdminObserver.getGameUpdateInfo(database, game!!.gameID)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io())
                    .subscribe({ game ->
                        originalGame = game
                        setInitialData(game)
                        getDataKeys(game.gameID)
                    }) {
                        Toast.makeText(this@EditGameActivity, "Unable to get game data",
                                Toast.LENGTH_SHORT).show()
                    }
        }
    }

    private fun setInitialData(gameData: Game){
        val model = AdminGameViewModel(gameData)

        binding?.gameContent?.apply {
            gameId.text = model.getGameID(this@EditGameActivity)
            gameDate.text = model.gameDateAsString
            gameTime.text = model.gameTimeAsString
            gameOpponent.setText(model.opponentName.orEmpty())
            dragonsScore.setText(model.dragonsScore.orEmpty())
            opponentScore.setText(model.opponentScore.orEmpty())
            otl.isChecked = model.oTL
        }

        gameViewModel = model
    }

    private fun setClickListeners(){
        binding?.gameContent?.clearGameResultButton?.setOnClickListener {
            deleteGameAndStats()
        }

        binding?.gameContent?.statsButton?.setOnClickListener {
            originalGame?.let {
                startActivity(DragonsHockeyIntents.createEditGameStatsIntent(this, it.gameID))
            }
        }

        binding?.gameContent?.dragonsScore?.addTextChangedListener {
            gameViewModel?.dragonsScore = it?.toString()
        }

        binding?.gameContent?.opponentScore?.addTextChangedListener {
            gameViewModel?.opponentScore = it?.toString()
        }

        binding?.gameContent?.otl?.setOnCheckedChangeListener { _, _ ->
            gameViewModel?.oTL = binding?.gameContent?.otl?.isChecked == true
        }
    }

    override fun onPause() {
        super.onPause()
        subscription?.unsubscribe()
        subscription = null

        gameViewModel?.let {
            if(it.hasChanged()){
                saveUpdates(it)
                refreshData = true
            }
        }
    }

    private fun saveUpdates(model: AdminGameViewModel) {
        if (keys != null) {
            if (keys!!.gameKeyValid()) {
                database!!.reference
                        .child(Config.GAMES)
                        .child(keys!!.getGameKey())
                        .setValue(model.game)
            }
            if (keys!!.gameResultKeyValid()) {
                database!!.reference
                        .child(Config.GAME_RESULTS)
                        .child(keys!!.getGameResultKey())
                        .setValue(model.game.gameResult)
            } else {
                val newGameResultRef = database!!.reference.child(Config.GAME_RESULTS).push()
                newGameResultRef.setValue(model.game.gameResult)
            }
            super.onBackPressed()
        } else {
            showKeysNotAvailableAlert()
        }
    }

    private fun deleteGameAndStats() {
        if (keys != null) {
            if (keys!!.gameResultKeyValid()) {
                database!!.reference
                        .child(Config.GAME_RESULTS)
                        .child(keys!!.getGameResultKey())
                        .removeValue()
            }
            if (keys!!.gameStatsKeyValid()) {
                database!!.reference
                        .child(Config.GAME_STATS)
                        .child(keys!!.getGameStatsKey())
                        .removeValue()
            }
            Toast.makeText(this, "Removed game", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Unable to remove game, keys not available", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun getDataKeys(gameID: Int) {
        subscription = AdminObserver.getGameKeys(database, gameID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { gameUpdateKeys -> keys = gameUpdateKeys }
    }

    private fun showKeysNotAvailableAlert() {
        AlertDialog.Builder(this).setPositiveButton("OK"
        ) { dialogInterface, i -> finish() }.setNegativeButton("Cancel") { dialogInterface, i ->
            //no-op
        }.setMessage("Keys for data not available yet, exit anyways?").show()
    }
}