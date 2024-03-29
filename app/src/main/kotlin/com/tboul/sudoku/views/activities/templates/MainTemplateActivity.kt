package com.tboul.sudoku.views.activities.templates

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import com.tboul.sudoku.R
import com.tboul.sudoku.views.activities.HelpActivity

abstract class MainTemplateActivity : TemplateActivity() {
    abstract val confirmExitMessage: String
    abstract val confirmExitTitle: String

    abstract fun actionOnBackConfirmed()

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(confirmExitTitle)
            .setMessage(confirmExitMessage)
            .setPositiveButton(R.string.yes) { _, _ -> actionOnBackConfirmed() }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    fun openHelpActivity(@Suppress("UNUSED_PARAMETER") view: View) =
        startNewActivity(Intent(this, HelpActivity::class.java))

    protected fun startNewActivity(gameActivity: Intent) {
        val transition = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.transition_start,
            R.anim.trantion_end
        ).toBundle()
        startActivity(gameActivity, transition)
    }
}