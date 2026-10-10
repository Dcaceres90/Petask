package com.deviar.petask.common.utils

enum class LevelDificult(val exp: Int, val coinValue: Int) {
    EASY(exp = 15, coinValue = 25),
    NORMAL(exp = 25, coinValue = 50),
    HARD(exp = 50, coinValue = 100),
}