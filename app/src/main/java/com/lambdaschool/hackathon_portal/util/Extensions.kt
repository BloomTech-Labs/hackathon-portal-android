package com.lambdaschool.hackathon_portal.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

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

fun <T> MutableList<T>.clearAndAddAll(newList: MutableList<T>) {
    this.clear()
    this.addAll(newList)
}

fun Context.buildAlertDialog(context: Context, title: String, message: String,
                             onPositiveClicked: () -> Unit,
                             onNegativeClicked: () -> Unit) {
    AlertDialog.Builder(context)
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
