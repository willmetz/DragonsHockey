package com.slapshotapps.dragonshockey.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents
import com.slapshotapps.dragonshockey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ActionBarListener {

  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    val navController = findNavController(R.id.fragment_container)
    binding.navigationView.setupWithNavController(navController)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {

    val inflater = menuInflater
    inflater.inflate(R.menu.menu_home, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {

    when (item.itemId) {
      R.id.action_admin -> {
        startActivity(DragonsHockeyIntents.createAdminAuthIntent(this))
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
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
}
