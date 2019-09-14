package com.tboul.sudoku.views.play.services

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.games.Games

fun showAchievements(context: Activity, signInAccount: GoogleSignInAccount) =
    Games.getAchievementsClient(context, signInAccount).achievementsIntent.addOnSuccessListener {
        context.startActivityForResult(it, 9003)
    }