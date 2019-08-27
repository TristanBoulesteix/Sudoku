package com.tboul.sudoku.views.components

import android.content.Context
import android.util.AttributeSet
import com.github.clans.fab.FloatingActionMenu
import com.tboul.sudoku.R

class FloatingActionMenuPlay(context: Context?, attrs: AttributeSet?) :
    FloatingActionMenu(context, attrs) {
    init {
        menuIconView.setImageResource(R.mipmap.ic_play)
        isIconAnimated = false
    }

    override fun toggle(animate: Boolean) {
        super.toggle(animate)

        if(isOpened) {
            menuIconView.setImageResource(R.mipmap.ic_play)
        } else {
            menuIconView.setImageResource(R.mipmap.ic_pause)
        }
    }
}