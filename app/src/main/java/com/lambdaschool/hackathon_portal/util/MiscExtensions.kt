package com.lambdaschool.hackathon_portal.util

import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import java.util.*

fun Calendar.formatCalendarToString(): String {
    return "${this.month + 1}/${this.dayOfMonth}/${this.year}"
}

fun <T> MutableList<T>.clearAndAddAll(newList: MutableList<T>) {
    this.clear()
    this.addAll(newList)
}