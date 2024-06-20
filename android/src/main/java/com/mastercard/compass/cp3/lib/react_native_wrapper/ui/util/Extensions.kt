package com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util

import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.mastercard.compass.cp3.lib.react_native_wrapper.BuildConfig
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.route.UserVerificationAPIRoute
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.WriteProgramSpaceCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.biometric.BiometricMatchResult
import com.mastercard.compass.model.biometrictoken.FormFactor
import com.mastercard.compass.model.biometrictoken.Modality
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber
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


fun populateModalityList(modalities: ArrayList<String>): MutableList<Modality> {
    val listOfModalities = mutableListOf<Modality>()

    modalities.forEach {
        when (it) {
            Key.FACE_MODALITY -> listOfModalities.add(Modality.FACE)
            Key.LEFT_PALM_MODALITY -> listOfModalities.add(Modality.LEFT_PALM)
            Key.RIGHT_PALM_MODALITY -> listOfModalities.add(Modality.RIGHT_PALM)
        }
    }

    return listOfModalities
}

fun getFormFactor(value: String): FormFactor {
  return when(value){
    FormFactor.CARD.name -> FormFactor.CARD
    FormFactor.QR.name -> FormFactor.QR
    else -> FormFactor.NONE
  }
}

fun getMatchListArray(list: List<BiometricMatchResult>): ReadableArray {
  val matchArray = Arguments.createArray()
  list.forEach {
    val matchMap = Arguments.createMap()
    matchMap.putString("modality", it.modality)
    matchMap.putDouble("distance", it.distance.toDouble())
    matchMap.putDouble("normalizedScore", it.normalizedScore.toDouble())
    matchArray.pushMap(matchMap)
  }

  return matchArray
}
suspend fun performSharedSpaceKeyExchange(sharedSpaceApi: SharedSpaceApi, helper: CompassKernelUIController.CompassHelper) = coroutineScope {
  withContext(Dispatchers.Default) {
    val keyPair = helper.getBuildConfigRSAKeyPair(BuildConfig.CRYPTO_PUBLIC_KEY, BuildConfig.CRYPTO_PRIVATE_KEY)
    val keyExchangeResponse = sharedSpaceApi.performKeyExchange(helper.getInstanceId()!!, keyPair.public)
    when (keyExchangeResponse.kernelEncPublicKey == null) {
      true -> Timber.tag(WriteProgramSpaceCompassApiHandlerActivity.TAG).e(keyExchangeResponse.errorCode.toString())
      false -> helper.saveKernelSharedSpaceKey(keyExchangeResponse.kernelEncPublicKey!!)
    }
  }
}
