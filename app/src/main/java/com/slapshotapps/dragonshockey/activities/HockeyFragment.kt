package com.slapshotapps.dragonshockey.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.AnalyticEventListener
import com.slapshotapps.dragonshockey.R
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


abstract class HockeyFragment : Fragment(), CoroutineScope {

    protected var actionBarListener: ActionBarListener? = null
    protected var analyticEventListener: AnalyticEventListener? = null
    protected var firebaseDatabase: FirebaseDatabase? = null

    protected lateinit var masterJob: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + masterJob

    abstract fun onResumeWithCredentials();
    abstract fun noCredentialsOnResume();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        masterJob = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        masterJob.cancel()
    }

    override fun onResume() {
        super.onResume()

        if(FirebaseAuth.getInstance().currentUser == null){
            lifecycleScope.launch { checkUserAuth() }
        }else{
            onResumeWithCredentials()
        }
    }

    private suspend fun checkUserAuth() = coroutineScope {
        val signInResult = FirebaseAuth.getInstance().signInAnonymously().await();

        withContext(Dispatchers.Main) {
            if (signInResult.user == null) {
                noCredentialsOnResume()
            } else {
                onResumeWithCredentials()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val hostActivity = activity
        if (hostActivity is ActionBarListener) {
            actionBarListener = hostActivity
        }

        if (hostActivity is AnalyticEventListener) {
            analyticEventListener = hostActivity
        }

        firebaseDatabase = FirebaseDatabase.getInstance()

//        try {
//            firebaseDatabase!!.setPersistenceEnabled(true)
//        } catch (exception: DatabaseException) {
//            Timber.e("Unable to set persistance for Firebase")
//        }
    }
}