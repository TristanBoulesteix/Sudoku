package com.tboul.sudoku.utils

import java.io.Serializable

enum class DIFFICULTY(val value: Int): Serializable {
    EASY(12), MEDIUM(16), HARD(22)
}