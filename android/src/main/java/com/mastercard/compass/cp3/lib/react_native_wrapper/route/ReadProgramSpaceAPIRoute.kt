package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.ReadProgramSpaceCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultCryptoService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultTokenService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpaceApi
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpaceValidationDecryptionResponse
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.performSharedSpaceKeyExchange
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.kernel.client.service.KernelServiceConsumer
import com.mastercard.compass.model.programspace.ReadProgramSpaceDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.security.PublicKey
import kotlin.coroutines.CoroutineContext

class ReadProgramSpaceAPIRoute(
  private val context: ReactApplicationContext,
  private val currentActivity: Activity?,
  private val helperObject: CompassKernelUIController.CompassHelper,
  private val cryptoService: DefaultCryptoService?,
) {
  private lateinit var sharedSpaceApi: SharedSpaceApi
  private lateinit var integrityService: DefaultTokenService

  private var decryptData: Boolean = false
  private val kernelPublicKey: PublicKey? = helperObject.getKernelJWTPublicKey()

  companion object {
    val REQUEST_CODE_RANGE = 900 until 1000

    const val READ_PROGRAM_SPACE_REQUEST_CODE = 900
    const val TAG = "ReadProgramSpaceAPIRoute"
  }

  fun startReadProgramSpaceIntent(
    readProgramSpaceParams: ReadableMap
  ){
    val reliantGUID: String = readProgramSpaceParams.getString("reliantGUID")!!
    val programGUID: String = readProgramSpaceParams.getString("programGUID")!!
    val rID: String = readProgramSpaceParams.getString("rID")!!
    decryptData = readProgramSpaceParams.getBoolean("decryptData")

    val intent = Intent(context, ReadProgramSpaceCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RID, rID)
    }

    integrityService = DefaultTokenService(helperObject.getKernelGuid()!!,kernelPublicKey!!,  helperObject.getReliantAppJWTKeyPair().private, reliantGUID)
    sharedSpaceApi = SharedSpaceApi(KernelServiceConsumer.INSTANCE_V2!!, reliantGUID, programGUID, cryptoService, integrityService)

    currentActivity?.startActivityForResult(intent, READ_PROGRAM_SPACE_REQUEST_CODE)
  }


  fun handleReadProgramSPaceIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    val scope = CustomScope()
    scope.launch {
      when (resultCode) {
        Activity.RESULT_OK -> {
          val resultMap = Arguments.createMap()
          val response: ReadProgramSpaceDataResponse =
            data?.extras?.get(Key.DATA) as ReadProgramSpaceDataResponse

          if (decryptData && helperObject.getKernelSharedSpaceKey() == null) {
            performSharedSpaceKeyExchange(
              sharedSpaceApi = sharedSpaceApi,
              helper = helperObject
            )
          }

          val extractedData = sharedSpaceApi.validateDecryptData(
            response = response,
            decryptData = decryptData
          ) as SharedSpaceValidationDecryptionResponse.Success




          resultMap.putString("programSpaceData", extractedData.data)
          promise.resolve(resultMap)
          scope.onStop()
        }

        Activity.RESULT_CANCELED -> {
          val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
          val message = data?.getStringExtra(Key.ERROR_MESSAGE)!!

          Timber.e("Error $code Message $message")
          promise.reject(code, Throwable(message))
          scope.onStop()
        }
      }
    }
  }
}

class CustomScope : CoroutineScope {
  private var parentJob = Job()

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + parentJob

  fun onStop() {
    parentJob.cancel()
  }
}
