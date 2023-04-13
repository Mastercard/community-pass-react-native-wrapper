package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.GetRegistrationDataCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.card.RegistrationStatusData


class GetRegistrationDataAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
  companion object {
    val REQUEST_CODE_RANGE = 700 until 800

    const val GET_REGISTRATION_DATA_REQUEST_CODE = 700
    private const val TAG = "GetRegistrationDataAPIRoute"
  }

  fun startGetRegistrationIntent(getRegistrationDataParams: ReadableMap){
    val reliantGUID: String = getRegistrationDataParams.getString("reliantGUID")!!
    val programGUID: String = getRegistrationDataParams.getString("programGUID")!!

    val intent = Intent(context, GetRegistrationDataCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID )
    }

    currentActivity?.startActivityForResult(intent, GET_REGISTRATION_DATA_REQUEST_CODE)
  }

  fun handleGetRegistrationDataIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val response = data?.extras?.get(Key.DATA) as RegistrationStatusData
        val methods = response.authMethods.authType.map { it.name }
        resultMap.apply {
          putBoolean("isRegisteredInProgram", response.isRegisteredInProgram)
          putArray("authMethods", Arguments.fromList(methods))
        }
        promise.resolve(resultMap);
      }
      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message = data?.getStringExtra(Key.ERROR_MESSAGE) ?: context.getString(R.string.error_unknown)
        Log.e(TAG, "Error $code Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }
}
