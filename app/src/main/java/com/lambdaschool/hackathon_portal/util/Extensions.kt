package com.lambdaschool.hackathon_portal.util

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions

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

/**
 * Builds NavOptions to popUpTo and then navigates to specified fragment
 * @param bundle: Bundle
 * @param destinationId : @IdRes Int
 * @param popUpToInclusive : Boolean
 * @param resId : @IdRes Int
 * */
fun NavController._navigateAndPopUpTo(bundle: Bundle,
                                      @IdRes destinationId: Int,
                                      popUpToInclusive: Boolean,
                                      @IdRes resId: Int) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(destinationId, popUpToInclusive)
        .build()

    this.navigate(resId, bundle, navOptions)
}