package com.slapshotapps.dragonshockey.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val navController = findNavController(R.id.fragment_container)
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
    bottomNavigationView.setupWithNavController(navController)
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

}
