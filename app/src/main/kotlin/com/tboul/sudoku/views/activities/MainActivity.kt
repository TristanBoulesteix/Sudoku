package com.tboul.sudoku.views.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tboul.sudoku.R
import com.tboul.sudoku.views.activities.templates.MainTemplateActivity




class MainActivity : MainTemplateActivity() {
    override fun actionOnBackConfirmed() = finish()
    override val confirmExitMessage by lazy { getString(R.string.confirm_exit_message) }
    override val confirmExitTitle by lazy { getString(R.string.confirm_exit_title) }

    val button by lazy { findViewById<FrameLayout>(R.id.play_game_signin) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this, "ca-app-pub-3910814368891137~8945215440")

        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun openGameActivity(@Suppress("UNUSED_PARAMETER") view: View) {
        val gameActivity = Intent(this, LevelActivity::class.java)
        val transition = ActivityOptions.makeCustomAnimation(
            this@MainActivity,
            R.anim.transition_start,
            R.anim.trantion_end
        ).toBundle()
        startActivity(gameActivity, transition)
    }
}
