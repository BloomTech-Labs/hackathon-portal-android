package com.lambdaschool.hackathon_portal.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import java.util.*

/**
 * Open the keyboard and focus on a specified view.
 *
 * @param view : View
 * */
fun Context.openSoftKeyboardAndFocus(view: View) {
    view.requestFocus()
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Make a toast and display for LENGTH_SORT.
 *
 * @param message : String
 *
 * NOTE: **Activity** context must be used.
 * */
fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Make a toast and display for LENGTH_LONG.
 *
 * @param message : String
 *
 * NOTE: **Activity** context must be used.
 * */
fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * Builds and displays an alert dialog with custom title, message, and code to be executed for both
 * positive and negative buttons
 *
 * @param title: String
 * @param message: String
 * @param onPositiveClicked: () -> Unit  - Allows you to pass in block of code to be executed
 *                                        (between {}) when the positive button is clicked,
 *                                        does not give access to dialog buttons.
 * @param onNegativeClicked: () -> Unit  - Same as onPositiveClicked, but executed when negative
 *                                         button is clicked.
 *
 * NOTE: **Activity** context must be used.
 */
fun Context.buildAlertDialog(title: String, message: String,
                             onPositiveClicked: () -> Unit,
                             onNegativeClicked: () -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Yes") { _, _ ->
            onPositiveClicked.invoke()
        }

        .setNegativeButton("No") { _, _ ->
            onNegativeClicked.invoke()
        }
        .create()
        .show()
}
