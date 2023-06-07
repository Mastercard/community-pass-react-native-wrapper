package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.RegistrationDataCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.biometrictoken.AuthenticationType
import com.mastercard.compass.model.card.RegistrationStatusData
import timber.log.Timber

class RetrieveRegistrationDataAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
  companion object {
    val REQUEST_CODE_RANGE = 700 until 800

    const val GET_REGISTRATION_DATA_REQUEST_CODE = 700
    private const val TAG = "RetrieveRegistrationDataAPIRoute"
  }

  fun startGetRegistrationIntent(getRegistrationDataParams: ReadableMap){
    val reliantGUID: String = getRegistrationDataParams.getString("reliantGUID")!!
    val programGUID: String = getRegistrationDataParams.getString("programGUID")!!

    val intent = Intent(context, RegistrationDataCompassApiHandlerActivity::class.java).apply {
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
        val authMethods = response.authMethods.authType.map { it.name }
        val modalityType = response.authMethods.modalityType?.map { it.name }

        resultMap.apply {
          putBoolean("isRegisteredInProgram", response.isRegisteredInProgram)
          putString("rID", response.rId)
          putArray("authMethods", Arguments.fromList(authMethods))
          if(response.authMethods.authType.contains(AuthenticationType.BIO)) putArray("modalityType", Arguments.fromList(modalityType))
        }
          promise.resolve(resultMap);
      }
      Activity.RESULT_CANCELED -> {
          val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
          val message = data?.getStringExtra(Key.ERROR_MESSAGE) ?: context.getString(R.string.error_unknown)
          Timber.tag(TAG).e("Error $code:  $message")
          promise.reject(code, Throwable(message))
      }
    }
  }
}
