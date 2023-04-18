package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.WriteProfileCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import timber.log.Timber

class ConsumerDeviceAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
    companion object {
        val REQUEST_CODE_RANGE = 200 until 300

        const val WRITE_PROFILE_REQUEST_CODE = 200
    }

    fun startWriteProfileIntent(WriteProfileParams: ReadableMap){
      val reliantGUID: String = WriteProfileParams.getString("reliantGUID")!!
      val programGUID: String = WriteProfileParams.getString("programGUID")!!
      val rID: String = WriteProfileParams.getString("rID")!!
      val overwriteCard = WriteProfileParams.getBoolean("overwriteCard")

      // Log
      Timber.d("reliantGUID: {$reliantGUID}")
      Timber.d("programGUID: {$programGUID}")
      Timber.d("rID: {$rID}")
      Timber.d("overwriteCard: {$overwriteCard}")
      val intent = Intent(context, WriteProfileCompassApiHandlerActivity::class.java).apply {
          putExtra(Key.RELIANT_APP_GUID, reliantGUID)
          putExtra(Key.PROGRAM_GUID, programGUID)
          putExtra(Key.RID, rID)
          putExtra(Key.OVERWRITE_CARD, overwriteCard)
      }

      currentActivity?.startActivityForResult(intent, WRITE_PROFILE_REQUEST_CODE)
    }

    fun handleWriteProfileIntentResponse(
        resultCode: Int,
        data: Intent?,
        promise: Promise
    ) {

      when (resultCode) {
        Activity.RESULT_OK -> {
          val resultMap = Arguments.createMap()
          resultMap.putString("consumerDeviceNumber", data?.extras?.get(Key.DATA).toString())

          // Log
          Timber.d("consumerDeviceNumber: ${data?.extras?.get(Key.DATA).toString()}")
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
