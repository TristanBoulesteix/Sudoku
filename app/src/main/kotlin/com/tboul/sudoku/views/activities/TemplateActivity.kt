package com.tboul.sudoku.views.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.tboul.sudoku.R

abstract class TemplateActivity : AppCompatActivity() {
    abstract fun actionOnBackConfirmed()

    abstract val confirmExitMessage: String
    abstract val confirmExitTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(confirmExitTitle)
            .setMessage(confirmExitMessage)
            .setPositiveButton(R.string.yes) { _, _ -> actionOnBackConfirmed() }
            .setNegativeButton(R.string.no, null)
            .show()
    }
}