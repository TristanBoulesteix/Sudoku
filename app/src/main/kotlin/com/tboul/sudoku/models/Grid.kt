package com.tboul.sudoku.models

import com.tboul.sudoku.models.factories.GridFactory
import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor

class Grid(difficulty: Int) {
    private var sudokuGrid = arrayOf<Array<Cell>>()

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

    init {
        val grid = GridFactory.grid

        for (cells in grid) {
            var line = arrayOf<Cell>()

            for (num in cells) {
                line += Cell(num)
            }

            sudokuGrid += line
        }

        val randNum = { x: Int -> floor((Math.random() * x + 1)).toInt() }

        var count = difficulty

        while (count != 0) {
            val cellId = randNum(SUDOKU_SIZE * SUDOKU_SIZE)

            val i = cellId / SUDOKU_SIZE
            var j = cellId % 9

            if (i == 9 || j == 9) continue
            if (j != 0) j--

            if (sudokuGrid[i][j].visible) {
                count--
                sudokuGrid[i][j].visible = false
            }
        }
    }

    fun updateValue(value: Int) {
        this[x][y].currentValue = value
    }

    fun clearCells() = forEachCells {
        if (!this.visible) this.currentValue = 0
    }

    fun resetPosition() {
        x = -1
        y = -1
    }

    operator fun get(index: Int) = sudokuGrid[index]

    fun solve() {
        forEachCells {
            if(!this.visible) {
                for(k in 0..SUDOKU_SIZE) {
                    this.currentValue = k
                    
                }
            }
        }
    }

    private fun forEachCells(action: Cell.() -> Unit) {
        for (cells in sudokuGrid) {
            for (cell in cells) {
                cell.action()
            }
        }
    }
}