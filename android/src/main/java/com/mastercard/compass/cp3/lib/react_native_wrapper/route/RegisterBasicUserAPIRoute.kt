package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.RegisterBasicUserCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import timber.log.Timber

class RegisterBasicUserAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
    companion object {
        val REQUEST_CODE_RANGE = 400 until 500

        const val REGISTER_BASIC_USER_REQUEST_CODE = 400
    }

    fun startRegisterBasicUserIntent(
      registerBasicUserParams: ReadableMap
    ){
      val reliantGUID: String = registerBasicUserParams.getString("reliantGUID")!!
      val programGUID: String = registerBasicUserParams.getString("programGUID")!!

      Timber.d("reliantGUID: {$reliantGUID}")
      Timber.d("programGUID: {$programGUID}")

      val intent = Intent(context, RegisterBasicUserCompassApiHandlerActivity::class.java).apply {
          putExtra(Key.RELIANT_APP_GUID, reliantGUID)
          putExtra(Key.PROGRAM_GUID, programGUID)
      }

      currentActivity?.startActivityForResult(intent, REGISTER_BASIC_USER_REQUEST_CODE)
    }

    fun handleRegisterBasicUserIntentResponse(
        resultCode: Int,
        data: Intent?,
        promise: Promise
    ) {

      when (resultCode) {
        Activity.RESULT_OK -> {
          val resultMap = Arguments.createMap()
          resultMap.putString("rID", data?.extras?.get(Key.DATA).toString())

          Timber.d("rID: ${data?.extras?.get(Key.DATA).toString()}")
          promise.resolve(resultMap);
        }
        Activity.RESULT_CANCELED -> {
          val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
          val message = data?.getStringExtra(Key.ERROR_MESSAGE)!!
          Timber.e("Error $code Message $message")
          promise.reject(code, Throwable(message))
        }
      }
    }
}
