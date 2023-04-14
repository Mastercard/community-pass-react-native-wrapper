package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.UserVerificationCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import timber.log.Timber


class UserVerificationAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?, private val helperObject: CompassKernelUIController.CompassHelper) {
  companion object {
    val REQUEST_CODE_RANGE = 1100 until 1200

    const val GET_USER_VERIFICATION_REQUEST_CODE = 1100
    private const val TAG = "UserVerificationAPIRoute"
  }

  fun startGetUserVerificationIntent(UserVerificationParams: ReadableMap){

    val programGUID: String = UserVerificationParams.getString("programGUID")!!
    val reliantGUID: String = UserVerificationParams.getString("reliantGUID")!!

    val intent = Intent(context, UserVerificationCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
    }

    currentActivity?.startActivityForResult(intent, GET_USER_VERIFICATION_REQUEST_CODE)
  }

  fun handleGetUserVerificationIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val jwt = data?.extras?.get(Key.DATA).toString()
        val response: CompassKernelUIController.CompassHelper.CompassJWTResponse.Success =
          helperObject.parseJWT(jwt) as CompassKernelUIController.CompassHelper.CompassJWTResponse.Success

        resultMap.apply {
          putString("rID", response.rId)
          putBoolean("isMatchFound", response.isMatchFound)
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
