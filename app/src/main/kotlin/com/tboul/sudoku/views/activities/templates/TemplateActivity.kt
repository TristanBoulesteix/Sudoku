package com.tboul.sudoku.views.activities.templates

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.tboul.sudoku.BuildConfig
import com.tboul.sudoku.R
import com.tboul.sudoku.utils.PREF_AUTO_LOGIN
import com.tboul.sudoku.utils.PREF_LOGIN_FILE


abstract class TemplateActivity : AppCompatActivity() {
    private val pref: SharedPreferences by lazy {
        getSharedPreferences(
            PREF_LOGIN_FILE,
            Context.MODE_PRIVATE
        )
    }

    private val signInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestProfile()
            .requestServerAuthCode(getString(R.string.default_web_client_id))
            .build()
    }

    protected open var signedInAccount: GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
    }

    fun logIn() {
            startSignIn()
            pref.edit().putBoolean(PREF_AUTO_LOGIN, true).apply()
    }

    private fun signInSilently() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (GoogleSignIn.hasPermissions(account, *signInOptions.scopeArray)) {
            // Already signed in.
            // The signed in account is stored in the 'account' variable.
            signedInAccount = account
            signedInAccount.let {
                Toast.makeText(
                    this,
                    signedInAccount!!.displayName,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            val signInClient = GoogleSignIn.getClient(this, signInOptions)
            signInClient
                .silentSignIn()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // The signed in account is stored in the task's result.
                        signedInAccount = task.result
                    } else {
                        startSignIn()
                    }
                }
        }
    }

    private fun startSignIn() {
        val signInClient = GoogleSignIn.getClient(
            this,
            signInOptions
        )
        val intent = signInClient.signInIntent
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // The signed in account is stored in the result.
                signedInAccount = result.signInAccount
            } else {
                if (BuildConfig.DEBUG) {
                    var message = result.status.statusMessage
                    if (message == null || message.isEmpty()) {
                        message = "FAIL"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }

                pref.edit().putBoolean(PREF_AUTO_LOGIN, false).apply()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (pref.getBoolean(PREF_AUTO_LOGIN, true)) signInSilently()
    }
}