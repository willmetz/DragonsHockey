package com.slapshotapps.dragonshockey.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import timber.log.Timber;

public class AdminAuthActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener {

    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private boolean defaultAccountCleared;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (!Config.isRelease) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("CERT Admin Auth CERT");
            }
        }

        firebaseAuth = FirebaseAuth.getInstance();

        //ensure the user is not anonymous
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null && user.isAnonymous()){
            firebaseAuth.signOut();
        }

        if (doesUserHaveAdminAccess(firebaseAuth)) {
            launchAuthenticatedActivity();
        } else {
            configureUnauthenticatedUser();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        showProgressBar(false);
    }

    @Override
    protected void onStop() {
        super.onStop();

        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            validateSignin(result);

            showProgressBar(true);
        }
    }

    private void configureUnauthenticatedUser() {

        showProgressBar(true);

        final SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setEnabled(false);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                        getString(R.string.request_token_id)).requestEmail().build();

        //we need to make sure that we only clear the default account once, otherwise we will end up in a loop
        defaultAccountCleared = false;

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (!defaultAccountCleared) {
                            googleApiClient.clearDefaultAccountAndReconnect();
                        } else {
                            signInButton.setEnabled(true);
                            showProgressBar(false);
                        }
                        defaultAccountCleared = true;
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        showProgressBar(false);
                        Toast.makeText(AdminAuthActivity.this, "Unable to connect to google API",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    protected void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void validateSignin(GoogleSignInResult result) {
        if (result.isSuccess() && result.getSignInAccount() != null) {

            //get ID from google signin account
            AuthCredential credential =
                    GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null);

            //attempt to sign into firebase with this authentication
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            showProgressBar(false);

                            if (!task.isSuccessful()) {
                                showUnauthorizedDialog("Your account");
                            }
                        }
                    });
        } else {
            showInvalidLoginDialog();
            showProgressBar(false);
        }
    }

    protected void showUnauthorizedDialog(String userEmail) {
        new AlertDialog.Builder(this).setTitle(R.string.unauthorized)
                .setMessage(String.format(Locale.US,
                        "%s is not authorized to use the admin features of this app", userEmail))
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminAuthActivity.this.finish();
                    }
                })
                .show();
    }

    protected void showInvalidLoginDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.invalid_login)
                .setMessage(R.string.try_again)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    protected void launchAuthenticatedActivity() {
        startActivity(DragonsHockeyIntents.createAdminIntent(this));
        finish();
    }

    protected boolean doesUserHaveAdminAccess(FirebaseAuth auth) {
        if (auth.getCurrentUser() != null) {
            String email =
                    auth.getCurrentUser().getEmail() == null ? "" : auth.getCurrentUser().getEmail();

            if (email.equals(getString(R.string.admin_access_email))) {
                return true;
            } else {//user is signed in but doesn't have write access, sign them out
                Timber.d("%s is unauthorized for admin access, signing out", email);
                auth.signOut();
            }
        }

        return false;
    }

    protected void showProgressBar(boolean show) {
        if (show) {
            progressBar.animate().alpha(1);
        } else {
            progressBar.animate().alpha(0);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        new AlertDialog.Builder(this).setTitle(R.string.connection_failed)
                .setMessage(R.string.google_services_unavailable)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if (firebaseAuth.getCurrentUser() != null) {
            if (doesUserHaveAdminAccess(firebaseAuth)) {
                launchAuthenticatedActivity();
            } else {
                showUnauthorizedDialog("Your account");
            }
        }
    }
}
