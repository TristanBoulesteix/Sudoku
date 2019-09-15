package com.tboul.sudoku.models

import com.tboul.sudoku.utils.DIFFICULTY

class Grid(private val sudokuGrid: Array<Array<Cell>>, val difficulty: DIFFICULTY) {
    val valid: Boolean
        get() {
            for (cells in sudokuGrid) {
                for (cell in cells) {
                    if (!cell.valid && !cell.visible) return false
                }
            }
            return true
        }

    var x = -1
    var y = -1

    fun updateValue(value: Int) {
        this[x][y].currentValue = value
    }

    fun clearCells() {
        for (cells in sudokuGrid) {
            for (cell in cells) {
                if (!cell.visible) cell.currentValue = 0
            }
        }
    }

    fun resetPosition() {
        x = -1
        y = -1
    }

    operator fun get(index: Int): Array<Cell> {
        return sudokuGrid[index]
    }
}