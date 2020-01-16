package com.lambdaschool.hackathon_portal.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Open the keyboard and focus on a specified view
 * @param view : View
 *
 * NOTE: `null` can be passed to simply open the keyboard.
 * */
fun Context._openSoftKeyboardAndFocus(view: View?) {
    view?.requestFocus()
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Make a toast
 * @param message : String
 *
 * NOTE: Activity context must be used.
 * */
fun Context._toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}