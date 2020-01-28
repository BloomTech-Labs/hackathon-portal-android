package com.lambdaschool.hackathon_portal.util

import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import java.util.*

/**
 * Formats Calendar to String in format MM/DD/YYYY
 *
 * NOTE: Uses afollestad datepicker properties
 */
fun Calendar.formatCalendarToString(): String {
    return "${this.month + 1}/${this.dayOfMonth}/${this.year}"
}

/**
 * Clears all values from list that method is called on and adds all values from list that is passed
 * as a param
 *
 * @param newList: MutableList<T>
 */
fun <T> MutableList<T>.clearAndAddAll(newList: MutableList<T>) {
    this.clear()
    this.addAll(newList)
}