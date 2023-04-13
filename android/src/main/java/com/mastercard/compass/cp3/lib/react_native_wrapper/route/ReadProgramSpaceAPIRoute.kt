package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.google.gson.Gson
import com.mastercard.compass.base.Constants
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.ReadProgramSpaceCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.RegisterBasicUserCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultCryptoService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpace
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.programspace.ReadProgramSpaceDataResponse
import timber.log.Timber
import java.security.InvalidKeyException
import java.security.PrivateKey
import java.util.*

class ReadProgramSpaceAPIRoute(
  private val context: ReactApplicationContext,
  private val currentActivity: Activity?,
  private val helperObject: CompassKernelUIController.CompassHelper,
  private val cryptoService: DefaultCryptoService?
) {
  private var decryptData: Boolean = false

  companion object {
    val REQUEST_CODE_RANGE = 900 until 1000

    const val READ_PROGRAM_SPACE_REQUEST_CODE = 900
  }

  fun startReadProgramSpaceIntent(
    ReadProgramSpaceParams: ReadableMap
  ){
    val reliantGUID: String = ReadProgramSpaceParams.getString("reliantGUID")!!
    val programGUID: String = ReadProgramSpaceParams.getString("programGUID")!!
    val rID: String = ReadProgramSpaceParams.getString("rID")!!
    decryptData = ReadProgramSpaceParams.getBoolean("decryptData")

    Timber.d("reliantGUID: {$reliantGUID}")
    Timber.d("programGUID: {$programGUID}")
    Timber.d("rID: {$rID}")

    val intent = Intent(context, ReadProgramSpaceCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RID, rID)
    }

    currentActivity?.startActivityForResult(intent, READ_PROGRAM_SPACE_REQUEST_CODE)
  }

  fun handleReadProgramSPaceIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val response = data?.extras?.get(Constants.EXTRA_DATA) as ReadProgramSpaceDataResponse
        Timber.d("programSpaceData: $response")

        var extractedData: String = helperObject.parseJWT(response.jwt).toString()

        if(decryptData){
            extractedData = String(cryptoService!!.decrypt(extractedData))
        }

        resultMap.putString("programSpaceData", extractedData)
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
