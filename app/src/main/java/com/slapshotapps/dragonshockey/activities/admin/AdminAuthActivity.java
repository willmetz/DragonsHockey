package com.slapshotapps.dragonshockey.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.Utils.DragonsHockeyIntents;
import com.slapshotapps.dragonshockey.Utils.SharedPrefsUtils;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;


public class AdminAuthActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!Config.isRelease) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("CERT Admin Auth CERT");
        }

        if(SharedPrefsUtils.isUserLoggedIn(this)){
            launchAuthenticatedActivity();
        }else{
            configureUnauthenticatedUser();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            validateSignin(result);
        }
    }

    private void configureUnauthenticatedUser(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        SignInButton signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    protected void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void validateSignin(GoogleSignInResult result){
        if(result.isSuccess()){

            String userEmail = result.getSignInAccount().getEmail();
            if(validateEmail(userEmail)){
                SharedPrefsUtils.setUserLoggedIn(this);
                launchAuthenticatedActivity();
            }else{
                showUnauthorizedDialog(userEmail);
            }
        }else{
            showInvalidLoginDialog();
        }
    }

    protected void showUnauthorizedDialog(String userEmail){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Unauthorized")
                .setMessage(String.format(Locale.US, "%s is not authorized to use the admin features of this app", userEmail))
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminAuthActivity.this.finish();
                    }
                }).show();
    }

    protected void showInvalidLoginDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Invalid login")
                .setMessage("Your login was incorrect, please try again")
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    protected boolean validateEmail(String email){
        return email.contentEquals("willmetz@gmail.com");
    }

    protected void launchAuthenticatedActivity(){
        startActivity(DragonsHockeyIntents.createAdminIntent(this));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO - need to figure out what to do here
    }
}
