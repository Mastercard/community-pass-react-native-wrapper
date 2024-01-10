package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.UserIdentificationCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.getMatchListArray
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import timber.log.Timber

class UserIdentificationAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?, private val helperObject: CompassKernelUIController.CompassHelper) {
  companion object {
    val REQUEST_CODE_RANGE = 1700 until 1800

    const val USER_IDENTIFICATION_REQUEST_CODE = 1700
    private const val TAG = "UserIdentificationAPIRoute"
  }

  fun startGetUserIdentificationIntent(userVerificationParams: ReadableMap) {
    val programGUID: String = userVerificationParams.getString("programGUID")!!
    val reliantGUID: String = userVerificationParams.getString("reliantGUID")!!
    val modalities: ReadableArray = userVerificationParams.getArray("modalities")!!
    val cacheHashesIfIdentified: Boolean =
      userVerificationParams.getBoolean("cacheHashesIfIdentified")
    val formFactor: String = userVerificationParams.getString("formFactor")!!


    val intent = Intent(context, UserIdentificationCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.MODALITIES, modalities.toArrayList())
      putExtra(Key.CACHE_HASHES_IF_IDENTIFIED, cacheHashesIfIdentified)
      putExtra(Key.FORMFACTOR, formFactor)
    }

    currentActivity?.startActivityForResult(intent, USER_IDENTIFICATION_REQUEST_CODE)
  }

  fun handleGetUserIdentificationIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val jwt = data?.extras?.get(Key.DATA).toString()
        val response =
          helperObject.parseJWT(jwt) as CompassKernelUIController.CompassHelper.CompassJWTResponse.Success

        resultMap.apply {
          putBoolean("isMatchFound", response.isMatchFound)
          putString("rID", response.rId)
          putArray("biometricMatchList", response.biometricMatchList?.let { getMatchListArray(it) })
        }
        promise.resolve(resultMap);
      }

      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message =
          data?.getStringExtra(Key.ERROR_MESSAGE) ?: context.getString(R.string.error_unknown)
        Timber.tag(TAG).e("Error  $code Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }
}
