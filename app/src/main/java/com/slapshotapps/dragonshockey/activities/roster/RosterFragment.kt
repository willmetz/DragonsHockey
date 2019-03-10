package com.slapshotapps.dragonshockey.activities.roster

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.ViewUtils.itemdecoration.StaticHeaderDecoration
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.activities.roster.adapters.RosterAdapter
import com.slapshotapps.dragonshockey.observables.RosterObserver
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by willmetz on 7/25/16.
 */
class RosterFragment : HockeyFragment() {

    private var rosterSubscription: Subscription? = null
    private var recyclerView: RecyclerView? = null
    private var rosterUnavailable: TextView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val listener = actionBarListener
        listener?.setTitle(getString(R.string.roster_title))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticEventListener?.logContentSelectedEvent("Roster", "901")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_roster, container, false)

        rosterUnavailable = view.findViewById(R.id.roster_unavailable)

        recyclerView = view.findViewById(R.id.roster_recycler_view)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView!!.layoutManager = layoutManager

        return view
    }

    override fun onResume() {
        super.onResume()

        rosterSubscription = RosterObserver.GetRoster(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Timber.e("Unable to get the roster...")
                    rosterUnavailable!!.animate().alpha(1f)
                }
                .subscribe { players ->
                    rosterUnavailable!!.alpha = 0f
                    val adapter = RosterAdapter(this@RosterFragment.context!!, players)
                    recyclerView!!.adapter = adapter

                    recyclerView!!.addItemDecoration(
                            StaticHeaderDecoration(adapter, recyclerView))
                }
    }

    override fun onPause() {
        super.onPause()

        if (rosterSubscription != null) {
            rosterSubscription!!.unsubscribe()
            rosterSubscription = null
        }
    }
}
