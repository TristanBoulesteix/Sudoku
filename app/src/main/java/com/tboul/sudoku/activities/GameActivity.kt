package com.tboul.sudoku.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.tboul.sudoku.R

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)
    }
}
