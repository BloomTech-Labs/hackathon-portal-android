package com.lambdaschool.hackathon_portal.util

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * Open the keyboard and focus on a specified view.
 *
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
 * Make a toast and display for LENGTH_SORT.
 *
 * @param message : String
 *
 * NOTE: **Activity** context must be used.
 * */
fun Context._toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Make a toast and display for LENGTH_LONG.
 *
 * @param message : String
 *
 * NOTE: **Activity** context must be used.
 * */
fun Context._toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * Builds NavOptions to popUpTo and navigate to specified fragment.
 *
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

/**
 * Locks the Navigation Drawer and, if an ActionBarDrawerToggle is passed,
 * it will disable the toggle.
 *
 * @param toggle: ActionBarDrawerToggle?
 *
 * NOTE: `null` can be passed to simply lock the Navigation Drawer
 * */
fun DrawerLayout._lockDrawer(toggle: ActionBarDrawerToggle?) {
    this.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    if (toggle != null) {
        toggle.isDrawerIndicatorEnabled = false
    }
}

/**
 * Unlocks the Navigation Drawer and, if an ActionBarDrawerToggle is passed,
 * it will enable the toggle.
 *
 * @param toggle: ActionBarDrawerToggle?
 *
 * NOTE: `null` can be passed to simply unlock the Navigation Drawer
 * */
fun DrawerLayout._unlockDrawer(toggle: ActionBarDrawerToggle?) {
    this.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    if (toggle != null) {
        toggle.isDrawerIndicatorEnabled = true
    }
}
