package com.tboul.sudoku.views.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.github.clans.fab.FloatingActionButton
import com.tboul.sudoku.R
import com.tboul.sudoku.models.Grid
import com.tboul.sudoku.views.GridView


class GameActivity : TemplateActivity() {
    private val gridView by lazy { GridView(Grid(), findViewById(R.id.fab_menu), this) }

    override fun actionOnBackConfirmed() {
        finish()
    }

    override val confirmExitMessage: String by lazy { getString(R.string.confirm_exit_game_message) }
    override val confirmExitTitle: String by lazy { getString(R.string.confirm_exit_game_title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        findViewById<FrameLayout>(R.id.sudoku).addView(gridView)

        // fab action
/*        findViewById<FloatingActionButton>(R.id.fab_settings).setOnClickListener {
            val gameActivity = Intent(this@GameActivity, SettingsActivity::class.java)
            val transition = ActivityOptions.makeCustomAnimation(
                this@GameActivity,
                R.anim.transition_start,
                R.anim.trantion_end
            ).toBundle()
            startActivity(gameActivity, transition)
        }*/
        findViewById<FloatingActionButton>(R.id.fab_home).setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        findViewById<FloatingActionButton>(R.id.fab_new_game).setOnClickListener(gridView.restartClick)

        // buttons actions
        findViewById<Button>(R.id.button_restart).setOnClickListener(gridView.resetClick)
        findViewById<Button>(R.id.button_validate).setOnClickListener(gridView.validateClick)
    }
}
