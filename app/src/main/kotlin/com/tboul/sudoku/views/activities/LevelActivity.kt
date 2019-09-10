package com.tboul.sudoku.views.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tboul.sudoku.R
import com.tboul.sudoku.views.activities.templates.TemplateActivity

class LevelActivity : TemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
    }

    private fun startGame(difficulty: DIFFICULTY) {
        val gameActivity = Intent(this, GameActivity::class.java)
        val animation = ActivityOptions.makeCustomAnimation(
            this@LevelActivity,
            R.anim.transition_start,
            R.anim.trantion_end
        ).toBundle()
        gameActivity.putExtra("difficulty", difficulty.value)
        startActivity(gameActivity, animation)
    }

    fun startEasyGame(@Suppress("UNUSED_PARAMETER") view: View) {
        startGame(DIFFICULTY.EASY)
    }

    fun startMediumGame(@Suppress("UNUSED_PARAMETER") view: View) {
        startGame(DIFFICULTY.MEDIUM)
    }

    fun startHardGame(@Suppress("UNUSED_PARAMETER") view: View) {
        startGame(DIFFICULTY.HARD)
    }

    enum class DIFFICULTY(val value: Int) {
        EASY(12), MEDIUM(16), HARD(22)
    }
}

