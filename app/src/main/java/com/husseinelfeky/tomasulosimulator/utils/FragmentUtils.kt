package com.husseinelfeky.tomasulosimulator.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.husseinelfeky.tomasulosimulator.R

fun Fragment.showSnackbar(text: CharSequence) {
    Snackbar.make(
        requireActivity().findViewById(R.id.container),
        text,
        Snackbar.LENGTH_SHORT
    ).setAnchorView(R.id.fab_action).show()
}

fun Fragment.showSnackbar(@StringRes resId: Int) {
    Snackbar.make(
        requireActivity().findViewById(R.id.container),
        resId,
        Snackbar.LENGTH_SHORT
    ).setAnchorView(R.id.fab_action).show()
}
