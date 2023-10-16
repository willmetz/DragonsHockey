package com.slapshotapps.dragonshockey.activities.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.slapshotapps.dragonshockey.Config
import com.slapshotapps.dragonshockey.databinding.ActivityAuthAdminBinding
import com.slapshotapps.dragonshockey.utils.DragonsHockeyIntents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

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

        lifecycleScope.launch {
            if (doesUserHaveAdminAccess()) {
                launchAuthenticatedActivity()
            } else {
                configureUnauthenticatedUser()
            }
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
                        lifecycleScope.launch {
                            if(doesUserHaveAdminAccess()){
                                launchAuthenticatedActivity()
                            }
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun doesUserHaveAdminAccess(): Boolean = suspendCancellableCoroutine { cont ->
        FirebaseDatabase.getInstance().reference.run {
            child("test").setValue("test").addOnCompleteListener { canWrite ->
                if(canWrite.isSuccessful){
                    cont.isActive.takeIf { it }?.run { cont.resume(true) }
                }else{
                    cont.isActive.takeIf { it }?.run { cont.resume(false) }
                }
            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            binding?.progressBar?.animate()?.alpha(1f)
        } else {
            binding?.progressBar?.animate()?.alpha(0f)
        }
    }
}