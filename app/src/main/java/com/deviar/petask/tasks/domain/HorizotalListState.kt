package com.deviar.petask.tasks.domain

import java.util.Date

data class HorizotalListState (
    val datesUpcoming: List<DateState> = arrayListOf(),
    val selectedDate: DateState =
        DateState(
            date = Date(),
            showDate = "23/02/1990",
        ),
)