package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.WriteProgramSpaceCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.programspace.WriteProgramSpaceDataResponse
import timber.log.Timber

class WriteProgramSpaceAPIRoute(
  private val context: ReactApplicationContext,
  private val currentActivity: Activity?
) {
  companion object {
    val REQUEST_CODE_RANGE = 1000 until 1100

    const val WRITE_PROGRAM_SPACE_REQUEST_CODE = 901
    const val TAG = "WriteProgramSpaceAPIRoute"
  }

  fun startWriteProgramSpaceIntent(
    writeProgramSpaceParams: ReadableMap
  ) {
    val reliantGUID: String = writeProgramSpaceParams.getString("reliantGUID")!!
    val programGUID: String = writeProgramSpaceParams.getString("programGUID")!!
    val rID: String = writeProgramSpaceParams.getString("rID")!!
    val jsonData: String = writeProgramSpaceParams.getString("programSpaceData")!!
    val encryptData: Boolean = writeProgramSpaceParams.getBoolean("encryptData")

    Timber.d("reliantGUID: $reliantGUID")
    Timber.d("programGUID: $programGUID")
    Timber.d("rID: $rID")
    Timber.tag(TAG).d(encryptData.toString())

    val intent = Intent(context, WriteProgramSpaceCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RID, rID)
      putExtra(Key.PROGRAM_SPACE_DATA, jsonData)
      putExtra(Key.ENCRYPT_DATA, encryptData)
    }

    currentActivity?.startActivityForResult(intent, WRITE_PROGRAM_SPACE_REQUEST_CODE)
  }

  fun handleWriteProgramSpaceIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val response: WriteProgramSpaceDataResponse = data?.extras?.get(Key.DATA) as WriteProgramSpaceDataResponse

        Timber.tag(TAG).d(response.toString())
        if(response.isSuccess){
          resultMap.putBoolean("isSuccess", true)
          promise.resolve(resultMap);
        } else {
          promise.reject(ErrorCode.UNKNOWN.toString(), Throwable("Unknown error" ));
        }
      }
      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message = data?.getStringExtra(Key.ERROR_MESSAGE) ?: "Unknown error"

        Timber.e("Error $code Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }
}
