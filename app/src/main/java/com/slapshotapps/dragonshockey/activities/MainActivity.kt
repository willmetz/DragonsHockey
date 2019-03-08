package com.slapshotapps.dragonshockey.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.slapshotapps.dragonshockey.AnalyticEventListener
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ActionBarListener, AnalyticEventListener {

  lateinit var binding: ActivityMainBinding


  private var firebaseAnalytics: FirebaseAnalytics? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    setSupportActionBar(binding.toolbar);

    firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    val navController = findNavController(R.id.fragment_container)
    binding.navigationView.setupWithNavController(navController)
  }

  override fun onSupportNavigateUp() =
      findNavController(R.id.fragment_container).navigateUp()

  override fun setTitle(title: String) {

    var enhancedTitle = title

    if (!Config.isRelease) {
      enhancedTitle = "CERT $title CERT"
    }

    binding.toolbar.title = enhancedTitle
  }

  override fun showProgressBar() {
    binding.toolbarProgressBar.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    binding.toolbarProgressBar.visibility = View.GONE
  }

  override fun logContentSelectedEvent(contentType: String, itemID: String) {
    val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemID)
    firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
  }
}