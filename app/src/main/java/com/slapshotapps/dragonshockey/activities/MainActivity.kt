package com.slapshotapps.dragonshockey.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.slapshotapps.dragonshockey.AnalyticEventListener
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ActionBarListener, AnalyticEventListener {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setSupportActionBar(binding!!.toolbar);
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as? NavHostFragment)?.let {
            NavigationUI.setupWithNavController(binding!!.navigationView, it.navController)
        }

    }

    override fun onSupportNavigateUp() =
            findNavController(this, R.id.fragment_container).navigateUp()

    override fun setTitle(title: String) {

        var enhancedTitle = title

        if (!Config.isRelease) {
            enhancedTitle = "CERT $title CERT"
        }

        binding?.toolbar?.title = enhancedTitle
    }

    override fun showProgressBar() {
        binding?.toolbarProgressBar?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding?.toolbarProgressBar?.visibility = View.GONE
    }

    override fun logContentSelectedEvent(analyticEvent: HockeyAnalyticEvent) {
        //no-op for now
    }
}
