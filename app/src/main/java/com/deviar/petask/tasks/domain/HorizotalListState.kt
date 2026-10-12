package com.deviar.petask.tasks.domain

import java.util.Date

data class HorizotalListState (
    val datesUpcoming: List<DateState> = arrayListOf(),
    var selectedDate: DateState =
        DateState(
            date = Date(),
            showMonthName = "Oct",
            showDayName = "13",
            showDayNumber = "Mar",
        ),
)