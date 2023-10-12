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
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.jwt.JwtConstants
import com.mastercard.compass.kernel.client.service.KernelServiceConsumer
import com.mastercard.compass.model.programspace.ReadProgramSpaceDataResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import timber.log.Timber
import java.security.PublicKey
import java.security.SignatureException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

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

    Timber.d("reliantGUID: $reliantGUID")
    Timber.d("programGUID: $programGUID")
    Timber.d("rID: $rID")
    Timber.d("decryptData: $decryptData")

    val intent = Intent(context, ReadProgramSpaceCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RID, rID)
    }

    integrityService = DefaultTokenService(helperObject.getKernelGuid()!!,kernelPublicKey!!,  helperObject.getReliantAppJWTKeyPair().private, reliantGUID)
    sharedSpaceApi = SharedSpaceApi(KernelServiceConsumer.INSTANCE_V2!!, reliantGUID, programGUID, cryptoService, integrityService)

    currentActivity?.startActivityForResult(intent, READ_PROGRAM_SPACE_REQUEST_CODE)
  }

  private fun parseJWT(jwt: String): String? {
    try {
      val data =
        Jwts.parserBuilder().setSigningKey(kernelPublicKey).build().parseClaimsJws(jwt).body
      return data[JwtConstants.JWT_PAYLOAD].toString()
    } catch (e: SignatureException) {
      Timber.tag(TAG).e(e, "parseJWT: Failed to validate JWT")
    } catch (e: ExpiredJwtException) {
      Timber.tag(TAG).e(e, "parseJWT: JWT expired")
    } catch (e: Exception) {
      Timber.tag(TAG).e(e, "parseJWT: Claims passed from Kernel are empty, null or invalid")
    }
    return  ""
  }

  fun handleReadProgramSPaceIntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val response: ReadProgramSpaceDataResponse = data?.extras?.get(Key.DATA) as ReadProgramSpaceDataResponse

        var extractedData: String = parseJWT(response.jwt).toString()

        if(decryptData){
            extractedData = String(cryptoService!!.decrypt(extractedData))
        }

        resultMap.putString("programSpaceData", extractedData)
        validateDecryption(response, promise, resultMap)

//        Timber.tag(TAG).d(extractedData)

//        resultMap.putString("programSpaceData", extractedData)
//        promise.resolve(resultMap);
      }
      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message = data?.getStringExtra(Key.ERROR_MESSAGE)!!

        Timber.e("Error $code Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }

private fun validateDecryption(data: ReadProgramSpaceDataResponse, promise: Promise, resultMap: ReadableMap) {
    var res: SharedSpaceValidationDecryptionResponse? = null
    val scope = CustomScope()

    try {
        scope.launch {
            Timber.tag("ReadProgramSpaceAPIRoutesssss").d("Called")
            res = sharedSpaceApi.validateDecryptData(data)
        }
    } catch (error: Throwable) {
      scope.coroutineContext.ensureActive()
      Timber.tag("ReadProgramSpaceAPIRoutesssss").d("Error")
      Timber.tag(TAG).d(error)
      promise.reject("0", Throwable("Validation Failed"))
    } finally {
      scope.coroutineContext.ensureActive()
      scope.onStop()
      Timber.tag("ReadProgramSpaceAPIRoutesssss").d("Result: $res")
      promise.resolve(resultMap);
    }
  }
}

class CustomScope : CoroutineScope {
  private var parentJob = Job()

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO + parentJob

  fun onStart() {
    parentJob = Job()
  }

  fun onStop() {
    parentJob.cancel()
  }
}
