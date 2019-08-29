package com.tboul.sudoku.views.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.tboul.sudoku.R


class MainActivity : TemplateActivity() {
    override fun actionOnBackConfirmed() = finish()
    override val confirmExitMessage: String by lazy { getString(R.string.confirm_exit_message) }
    override val confirmExitTitle: String by lazy { getString(R.string.confirm_exit_title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_button).setOnClickListener {
            val gameActivity = Intent(this@MainActivity, GameActivity::class.java)
            val transition = ActivityOptions.makeCustomAnimation(
                this@MainActivity,
                R.anim.transition_start,
                R.anim.trantion_end
            ).toBundle()
            startActivity(gameActivity, transition)
        }
    }
}
