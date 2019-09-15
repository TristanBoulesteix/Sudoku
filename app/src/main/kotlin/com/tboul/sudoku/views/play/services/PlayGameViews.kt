package com.tboul.sudoku.views.play.services

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.games.Games

fun showAchievements(context: Activity, signInAccount: GoogleSignInAccount) =
    Games.getAchievementsClient(context, signInAccount).achievementsIntent.addOnSuccessListener {
        context.startActivityForResult(it, 9003)
    }

fun unlockAchievements(context: Activity, signInAccount: GoogleSignInAccount, achievement: String) {
    Games.getAchievementsClient(context, signInAccount).unlock(achievement)
}

fun setViewForPopups(context: Context, signInAccount: GoogleSignInAccount, view: View) = Games.getGamesClient(context, signInAccount)
    .setViewForPopups(view)