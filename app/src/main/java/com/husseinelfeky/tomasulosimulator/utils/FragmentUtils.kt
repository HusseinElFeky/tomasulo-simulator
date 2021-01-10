package com.husseinelfeky.tomasulosimulator.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(text: CharSequence) {
    Snackbar.make(
        requireActivity().findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.showSnackbar(@StringRes resId: Int) {
    Snackbar.make(
        requireActivity().findViewById(android.R.id.content),
        resId,
        Snackbar.LENGTH_SHORT
    ).show()
}
