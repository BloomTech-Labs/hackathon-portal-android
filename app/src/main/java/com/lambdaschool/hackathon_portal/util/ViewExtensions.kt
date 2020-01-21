package com.lambdaschool.hackathon_portal.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun View._visGone() {
    this.visibility = View.GONE
}

fun View._visInvisible() {
    this.visibility = View.INVISIBLE
}

fun View._visVisible() {
    this.visibility = View.VISIBLE
}

fun TextView._text(string: String) {
    this.text = string
}

fun View._disable() {
    this.isEnabled = false
}

fun View._enable() {
    this.isEnabled = true
}

fun ViewGroup._inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
