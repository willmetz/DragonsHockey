package com.slapshotapps.dragonshockey.activities.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.databinding.ActivityAuthAdminBinding
import com.slapshotapps.dragonshockey.utils.DragonsHockeyIntents

class AdminAuthActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var binding: ActivityAuthAdminBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityAuthAdminBinding.inflate(layoutInflater).let {
            binding = it
            setContentView(it.root)
            setSupportActionBar(it.toolbar)
        }

        if (!Config.isRelease) {
            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.title = "CERT Admin Auth CERT"
            }
        }
        firebaseAuth = FirebaseAuth.getInstance()

        //ensure the user is not anonymous
        firebaseAuth?.currentUser?.takeIf { it.isAnonymous }?.run {
            firebaseAuth?.signOut()
        }

        if (doesUserHaveAdminAccess()) {
            launchAuthenticatedActivity()
        } else {
            configureUnauthenticatedUser()
        }
    }

    override fun onPause() {
        super.onPause()
        showProgressBar(false)
    }

    private fun configureUnauthenticatedUser() {
        binding?.apply {
            signInButton.isEnabled = true
            signInButton.setOnClickListener {
                val email = emailInput.text?.toString().orEmpty()
                val password = passwordInput.text?.toString().orEmpty()
                signIn(email, password)
            }
        }


    }

    private fun signIn(email: String, password: String) {

        showProgressBar(true)
        firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if(it.isSuccessful){
                        if(doesUserHaveAdminAccess()){
                            launchAuthenticatedActivity()
                        }
                        showProgressBar(false)
                    }else{
                        Toast.makeText(this, "You do not have admin access", Toast.LENGTH_SHORT).show()
                        showProgressBar(false)
                    }
                }

    }
    private fun launchAuthenticatedActivity() {
        startActivity(DragonsHockeyIntents.createAdminIntent(this))
        finish()
    }

    private fun doesUserHaveAdminAccess(): Boolean {
        firebaseAuth?.currentUser?.apply {
            this.email?.takeIf { it == getString(R.string.admin_access_email) }?.run { return true }
                    ?: firebaseAuth?.signOut()
        }
        return false
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            binding?.progressBar?.animate()?.alpha(1f)
        } else {
            binding?.progressBar?.animate()?.alpha(0f)
        }
    }
}