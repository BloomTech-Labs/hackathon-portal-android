package com.lambdaschool.hackathon_portal.util

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker

fun View.visGone() {
    this.visibility = View.GONE
}

fun View.visInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.visVisible() {
    this.visibility = View.VISIBLE
}

fun TextView.text(string: String) {
    this.text = string
}

/**
 * Sets click listener on edit text that method is called on. Click listener opens a Material
 * Dialog DatePicker, and sets the text of the edit text to the selected date when the positive
 * button is clicked
 */
fun EditText.setClickListenerToOpenDatePickerAndSetTextToDate(context: Context) {
    this.setOnClickListener {
        MaterialDialog(context).show {
            datePicker { _, datetime ->
                this@setClickListenerToOpenDatePickerAndSetTextToDate
                    .setText(datetime.formatCalendarToString())
            }
        }
    }
}
