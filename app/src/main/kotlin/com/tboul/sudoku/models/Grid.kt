package com.tboul.sudoku.models

import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor
import kotlin.math.sqrt

class Grid(difficulty: Int) {
    private var grid = Array(SUDOKU_SIZE) {
        Array(SUDOKU_SIZE) { Cell.emptyCell }
    }

    private val squareRoot = sqrt(SUDOKU_SIZE.toDouble()).toInt()

    val isValid: Boolean
        get() {
            for (cells in grid) {
                for (cell in cells) {
                    if (!cell.valid && !cell.visible) return false
                }
            }
            return true
        }

    var x = -1
    var y = -1

    init {
        fun unUsedInRow(i: Int, num: Int): Boolean {
            for (j in 0 until SUDOKU_SIZE)
                if (this[i][j].value == num)
                    return false
            return true
        }

        fun unUsedInCol(j: Int, num: Int): Boolean {
            for (i in 0 until SUDOKU_SIZE)
                if (this[i][j].value == num)
                    return false
            return true
        }

        fun unUsedInBox(
            rowStart: Int,
            colStart: Int,
            num: Int
        ): Boolean {
            for (i in 0 until squareRoot) {
                for (j in 0 until squareRoot) {
                    if (this[rowStart + i][colStart + j].value == num) return false
                }
            }
            return true
        }

        fun fillRemaining(x: Int, y: Int): Boolean {
            var i = x
            var j = y

            if (j >= SUDOKU_SIZE && i < SUDOKU_SIZE - 1) {
                i += 1
                j = 0
            }
            if (i >= SUDOKU_SIZE && j >= SUDOKU_SIZE)
                return true

            if (i < squareRoot) {
                if (j < squareRoot)
                    j = squareRoot
            } else if (i < SUDOKU_SIZE - squareRoot) {
                if (j == i / squareRoot * squareRoot)
                    j += squareRoot
            } else {
                if (j == SUDOKU_SIZE - squareRoot) {
                    i += 1
                    j = 0
                    if (i >= SUDOKU_SIZE)
                        return true
                }
            }

            for (num in 1..SUDOKU_SIZE) {
                if (unUsedInRow(i, num) &&
                    unUsedInCol(j, num) &&
                    unUsedInBox(
                        i - i % squareRoot,
                        j - j % squareRoot,
                        num
                    )
                ) {
                    this[i][j] = Cell(num)
                    if (fillRemaining(i, j + 1))
                        return true

                    this[i][j] = Cell.emptyCell
                }
            }
            return false
        }

        // Fill diagonals
        var index = 0
        while (index < SUDOKU_SIZE) {
            var num: Int
            for (i in 0 until squareRoot) {
                for (j in 0 until squareRoot) {
                    do {
                        num = floor(Math.random() * SUDOKU_SIZE + 1).toInt()
                    } while (!unUsedInBox(index, index, num))

                    this[index + i][index + j] = Cell(num)
                }
            }

            index += squareRoot
        }

        // Fill remaining
        fillRemaining(0, squareRoot)

        for (i in 0 until SUDOKU_SIZE) {
            for (j in 0 until SUDOKU_SIZE)
                print(this[i][j].toString() + " ")
            println()
        }
        println()

        var count = difficulty

        while (count != 0) {
            val cellId = floor((Math.random() * (SUDOKU_SIZE * SUDOKU_SIZE) + 1)).toInt()

            val i = cellId / SUDOKU_SIZE
            var j = cellId % 9

            if (i == 9 || j == 9) continue
            if (j != 0) j--

            if (this.grid[i][j].visible) {
                count--
                this.grid[i][j].visible = false
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

    operator fun get(index: Int) = grid[index]

    fun solve() {
        forEachCells {
            if (!this.visible) {
                for (k in 0..SUDOKU_SIZE) {
                    this.currentValue = k

                }
            }
        }
    }

    private fun forEachCells(action: Cell.() -> Unit) {
        for (cells in grid) {
            for (cell in cells) {
                cell.action()
            }
        }
    }
}