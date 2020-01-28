package com.lambdaschool.hackathon_portal.util

import android.view.View
import android.widget.TextView

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
