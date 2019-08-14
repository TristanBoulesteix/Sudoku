package com.tboul.sudoku

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_game)
    }
}
