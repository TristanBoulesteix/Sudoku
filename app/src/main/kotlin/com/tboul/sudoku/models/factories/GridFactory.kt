package com.tboul.sudoku.models.factories

import com.tboul.sudoku.utils.SUDOKU_SIZE
import kotlin.math.floor
import kotlin.math.sqrt

object GridFactory {
    private const val numColumnsAndRows = SUDOKU_SIZE
    private val squareRoot = sqrt(numColumnsAndRows.toDouble()).toInt()

    val grid: Array<IntArray>
        get() {
            val gridToBuild = Array(numColumnsAndRows) { IntArray(numColumnsAndRows) }

            fillDiagonal(gridToBuild)
            fillRemaining(gridToBuild, 0, squareRoot)

            for (i in 0 until numColumnsAndRows) {
                for (j in 0 until numColumnsAndRows)
                    print(gridToBuild[i][j].toString() + " ")
                println()
            }
            println()

            return gridToBuild
        }


    private fun fillDiagonal(gridToBuild: Array<IntArray>) {
        var i = 0
        while (i < numColumnsAndRows) {
            fillBox(gridToBuild, i, i)
            i += squareRoot
        }
    }

    private fun fillRemaining(gridToBuild: Array<IntArray>, x: Int, y: Int): Boolean {
        var i = x
        var j = y

        if (j >= numColumnsAndRows && i < numColumnsAndRows - 1) {
            i += 1
            j = 0
        }
        if (i >= numColumnsAndRows && j >= numColumnsAndRows)
            return true

        if (i < squareRoot) {
            if (j < squareRoot)
                j = squareRoot
        } else if (i < numColumnsAndRows - squareRoot) {
            if (j == i / squareRoot * squareRoot)
                j += squareRoot
        } else {
            if (j == numColumnsAndRows - squareRoot) {
                i += 1
                j = 0
                if (i >= numColumnsAndRows)
                    return true
            }
        }

        for (num in 1..numColumnsAndRows) {
            if (checkIfSafe(gridToBuild, i, j, num)) {
                gridToBuild[i][j] = num
                if (fillRemaining(gridToBuild, i, j + 1))
                    return true

                gridToBuild[i][j] = 0
            }
        }
        return false
    }

    private fun checkIfSafe(gridToBuild: Array<IntArray>, i: Int, j: Int, num: Int): Boolean {
        return unUsedInRow(gridToBuild, i, num) &&
                unUsedInCol(gridToBuild, j, num) &&
                unUsedInBox(gridToBuild, i - i % squareRoot, j - j % squareRoot, num)
    }

    private fun unUsedInRow(gridToBuild: Array<IntArray>, i: Int, num: Int): Boolean {
        for (j in 0 until numColumnsAndRows)
            if (gridToBuild[i][j] == num)
                return false
        return true
    }

    private fun unUsedInCol(gridToBuild: Array<IntArray>, j: Int, num: Int): Boolean {
        for (i in 0 until numColumnsAndRows)
            if (gridToBuild[i][j] == num)
                return false
        return true
    }

    private fun fillBox(gridToBuild: Array<IntArray>, row: Int, col: Int) {
        var num: Int
        for (i in 0 until squareRoot) {
            for (j in 0 until squareRoot) {
                do {
                    num = randomGenerator(numColumnsAndRows)
                } while (!unUsedInBox(gridToBuild, row, col, num))

                gridToBuild[row + i][col + j] = num
            }
        }
    }

    private fun randomGenerator(num: Int): Int {
        return floor(Math.random() * num + 1).toInt()
    }

    private fun unUsedInBox(
        gridToBuild: Array<IntArray>,
        rowStart: Int,
        colStart: Int,
        num: Int
    ): Boolean {
        for (i in 0 until squareRoot) {
            for (j in 0 until squareRoot) {
                if (gridToBuild[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }
}