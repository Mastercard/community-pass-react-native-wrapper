package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.CreateSVACompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import timber.log.Timber


class CreateSvaAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
  companion object {
    val REQUEST_CODE_RANGE = 1200 until 1300
    const val GET_CREATE_SVA_REQUEST_CODE = 1200
    private const val TAG = "CreateSvaAPIRoute"
  }

  lateinit var eVoucherType: String

  fun startCreateSvaIntent(createSvaParams: ReadableMap){

    val programGUID: String = createSvaParams.getString("programGUID")!!
    val reliantGUID: String = createSvaParams.getString("reliantGUID")!!
    val rId: String = createSvaParams.getString("rID")!!
    val unit = createSvaParams.getMap("sva")!!.getMap("value")?.getString("unit") as String
    val type = createSvaParams.getMap("sva")!!.getMap("value")?.getString("type") as String
    if(type.equals("EVoucherSVA", true)){
      eVoucherType = createSvaParams.getMap("sva")!!.getMap("value")?.getString("eVoucherType") as String
    }

    val intent = Intent(context, CreateSVACompassApiHandlerActivity::class.java).apply {
      putExtra(Key.UNIT, unit)
      putExtra(Key.RID, rId)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.TYPE, type)
      if(type.equals("EVoucherSVA", true)){
        putExtra(Key.E_VOUCHER_TYPE, eVoucherType)
      }
    }

    currentActivity?.startActivityForResult(intent, GET_CREATE_SVA_REQUEST_CODE)
  }

  fun handleGetCreateSvaIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val successResponse = data?.extras?.get(Key.DATA)
        val resultMap = Arguments.createMap()
        resultMap.apply {
          putString("response", successResponse.toString())
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
