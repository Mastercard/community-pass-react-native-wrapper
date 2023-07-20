package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.BlacklistFormFactorApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.blacklist.BlacklistFormFactorResponse
import timber.log.Timber


class BlacklistFormFactorAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
  companion object {
    val REQUEST_CODE_RANGE = 1500 until 1600
    const val GET_BLACKLIST_FORMFACTOR_REQUEST_CODE = 1500
    private const val TAG = "BlacklistFormFactorAPIRoute"
  }

  fun startBlacklistFormFactorIntent(blackListFormFactorParams: ReadableMap){

    val programGUID: String = blackListFormFactorParams.getString("programGUID")!!
    val reliantGUID: String = blackListFormFactorParams.getString("reliantGUID")!!
    val rId: String = blackListFormFactorParams.getString("rID")!!
    val consumerDeviceNumber: String = blackListFormFactorParams.getString("consumerDeviceNumber")!!
    val formFactor: String = blackListFormFactorParams.getString("formFactor")!!

    val intent = Intent(context, BlacklistFormFactorApiHandlerActivity::class.java).apply {
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RID, rId)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.CONSUMER_DEVICE_NUMBER, consumerDeviceNumber)
      putExtra(Key.FORMFACTOR, formFactor)
    }

    currentActivity?.startActivityForResult(intent, GET_BLACKLIST_FORMFACTOR_REQUEST_CODE)
  }

  fun handleBlacklistFormFactorIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val response = data?.extras?.get(Key.DATA) as BlacklistFormFactorResponse
        val resultMap = Arguments.createMap()
        resultMap.apply {
          putString("type", response.type.name)
          putString("status", response.status.name)
          putString("consumerDeviceNumber", response.consumerDeviceNumber)
        }
        promise.resolve(resultMap);
      }
      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message =
          data?.getStringExtra(Key.ERROR_MESSAGE) ?: context.getString(R.string.error_unknown)
        Timber.tag(TAG).e("Error  $code  Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }
}
