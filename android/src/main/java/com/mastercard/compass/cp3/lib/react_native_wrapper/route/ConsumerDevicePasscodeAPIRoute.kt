package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.WritePasscodeCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import timber.log.Timber
import java.util.*

class ConsumerDevicePasscodeAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
    companion object {
        val REQUEST_CODE_RANGE = 500 until 600

        const val WRITE_PASSCODE_REQUEST_CODE = 500
    }

    fun startWritePasscodeIntent(WritePasscodeParams: ReadableMap){
      val reliantGUID: String = WritePasscodeParams.getString("reliantGUID")!!
      val programGUID: String = WritePasscodeParams.getString("programGUID")!!
      val rID: String = WritePasscodeParams.getString("rID")!!
      val passcode: String = WritePasscodeParams.getString("passcode")!!

      // Log
      Timber.d("reliantGUID: {$reliantGUID}")
      Timber.d("programGUID: {$programGUID}")
      Timber.d("rID: {$rID}")
      Timber.d("passcode: {$passcode}")
      val intent = Intent(context, WritePasscodeCompassApiHandlerActivity::class.java).apply {
          putExtra(Key.RELIANT_APP_GUID, reliantGUID)
          putExtra(Key.PROGRAM_GUID, programGUID)
          putExtra(Key.RID, rID)
          putExtra(Key.PASSCODE, passcode)
      }

      currentActivity?.startActivityForResult(intent, WRITE_PASSCODE_REQUEST_CODE)
    }

    fun handleWritePasscodeIntentResponse(
        resultCode: Int,
        data: Intent?,
        promise: Promise
    ) {

      when (resultCode) {
        Activity.RESULT_OK -> {
          val resultMap = Arguments.createMap()
          resultMap.putString("responseStatus", data?.extras?.get(Key.DATA).toString().uppercase())

          // Log
          Timber.d("responseStatus: ${data?.extras?.get(Key.DATA).toString()}")
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
