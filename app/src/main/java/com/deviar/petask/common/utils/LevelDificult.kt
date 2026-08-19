package com.deviar.petask.common.utils

enum class LevelDificult(exp: Int, coinValue: Int) {
    HARD(exp = 50, coinValue = 100),
    NORMAL(exp = 25, coinValue = 50),
    EASY(exp = 15, coinValue = 25),
}