package com.navent.entertainmentcompse.util

import android.text.Html

@Suppress("DEPRECATION")
fun characterDecode(text: String): String {
    return Html.fromHtml(text).toString()
}

