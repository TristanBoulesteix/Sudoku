package com.tboul.sudoku.views.activities

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.tboul.sudoku.views.activities.templates.TemplateActivity


/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
class GoogleSignInActivity : TemplateActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
    }
}