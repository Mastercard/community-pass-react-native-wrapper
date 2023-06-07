package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.ReadSVACompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.sva.SVARecord
import timber.log.Timber


class ReadSvaAPIRoute(private val context: ReactApplicationContext, private val currentActivity: Activity?) {
  companion object {
    val REQUEST_CODE_RANGE = 1300 until 1400
    const val GET_READ_SVA_REQUEST_CODE = 1300
    private const val TAG = "ReadSvaAPIRoute"
  }

  fun startReadSvaIntent(readSvaParams: ReadableMap){

    val programGUID: String = readSvaParams.getString("programGUID")!!
    val reliantGUID: String = readSvaParams.getString("reliantGUID")!!
    val rId: String = readSvaParams.getString("rID")!!
    val svaUnit: String = readSvaParams.getString("svaUnit")!!

    val intent = Intent(context, ReadSVACompassApiHandlerActivity::class.java).apply {
      putExtra(Key.SVA_UNIT, svaUnit)
      putExtra(Key.RID, rId)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
    }

    currentActivity?.startActivityForResult(intent, GET_READ_SVA_REQUEST_CODE)
  }

  fun handleGetReadSvaIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val svaResponse: SVARecord = data?.extras?.get(Key.DATA) as SVARecord
        val resultMap = Arguments.createMap()
        resultMap.apply {
          putString("unit", svaResponse.unit)
          putInt("currentBalance", svaResponse.currentBalance)
          putInt("transactionCount", svaResponse.transactionCount)
          putString("purseType", svaResponse.purseType.name)
          val lastTransaction = Arguments.createMap().apply {
            putInt("amount", svaResponse.lastTransaction?.amount ?: 0)
            putInt("balance", svaResponse.lastTransaction?.balance ?: 0)
          }
          putMap("lastTransaction", lastTransaction)
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
