package com.tboul.sudoku.models

class Cells(val value: Int) {
    var visible = true
        set(state) {
            field = state
            currentValue = if (state) 0 else currentValue
        }

    var currentValue = 0
        set(newValue) {
            field = if (visible) 0 else newValue
        }
}