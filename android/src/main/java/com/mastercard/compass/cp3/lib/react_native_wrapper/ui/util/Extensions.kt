package com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun BigDecimal.toReadableString(sign: String? = null): String {
  val format = DecimalFormat("#,##0.00")
  return (sign?.let { "$it " } ?: "").plus(format.format(this))
}

fun Date.toReadableString(): String {
  val format = SimpleDateFormat("dd MMM yy | HH:mm")
  return format.format(this)
}
