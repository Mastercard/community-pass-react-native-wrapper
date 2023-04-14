package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.VerifyPasscodeCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.card.VerifyPasscodeResponse


class GetVerifyPasscodeAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
  companion object {
    val REQUEST_CODE_RANGE = 800 until 900

    const val GET_VERIFY_PASSCODE_REQUEST_CODE = 800
    private const val TAG = "GetVerifyPasscodeAPIRoute"
  }

  fun startGetVerifyPasscodeIntent(VerifyPasscodeParams: ReadableMap){

    val passcode: String = VerifyPasscodeParams.getString("passcode")!!
    val programGUID: String = VerifyPasscodeParams.getString("programGUID")!!
    val reliantGUID: String = VerifyPasscodeParams.getString("reliantGUID")!!

    val intent = Intent(context, VerifyPasscodeCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.PASSCODE, passcode)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
    }

    currentActivity?.startActivityForResult(intent, GET_VERIFY_PASSCODE_REQUEST_CODE)
  }

  fun handleGetVerifyPasscodeIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val response = data?.extras?.getParcelable<VerifyPasscodeResponse>(Key.DATA)
        resultMap.apply {
          putBoolean("status", response?.status!!)
          putString("rId", response.rid)
          putInt("counter", response.counter!!.retryCount)
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
