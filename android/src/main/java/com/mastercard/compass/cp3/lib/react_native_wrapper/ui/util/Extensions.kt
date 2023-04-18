package com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util

import com.mastercard.compass.model.biometrictoken.Modality
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


fun populateModalityList (face: Boolean, leftPalm: Boolean, rightPalm: Boolean) : MutableList<Modality> {
  val listOfModalities = mutableListOf<Modality>()

  if(face) listOfModalities.add(Modality.FACE)
  if(leftPalm) listOfModalities.add(Modality.LEFT_PALM)
  if(rightPalm) listOfModalities.add(Modality.RIGHT_PALM)

  return listOfModalities
}
