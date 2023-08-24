package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.BiometricConsentCompassApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.jwt.JwtConstants
import com.mastercard.compass.model.batch.response.BatchBaseResponse
import com.mastercard.compass.model.batch.response.ExecutionStatus
import com.mastercard.compass.model.batch.response.ReadApplicationBlobResponse
import com.mastercard.compass.model.batchV1.OperationsV1
import com.mastercard.compass.model.batchV1.response.BatchOperationResponseV1
import com.mastercard.compass.model.batchV1.response.BatchOperationResultV1
import com.mastercard.compass.model.card.RegistrationStatusData
import com.mastercard.compass.model.card.VerifyPasscodeResponse
import com.mastercard.compass.model.consent.ConsentResponse
import com.mastercard.compass.model.programspace.ReadProgramSpaceDataResponse
import com.mastercard.compass.model.programspace.WriteProgramSpaceDataResponse
import com.mastercard.compass.model.sva.SVAOperationResult
import com.mastercard.compass.model.sva.SVARecord
import com.mastercard.compass.utils.GsonUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import timber.log.Timber
import java.security.PublicKey
import java.security.SignatureException

class BatchOperationsAPIRoute(
  private val context: ReactApplicationContext,
  private val currentActivity: Activity?,
  private val helperObject: CompassKernelUIController.CompassHelper
  ) {
  companion object {
    val REQUEST_CODE_RANGE = 1600 until 1700
    const val BATCH_OPERATIONS_API_REQUEST_CODE = 1600
    private const val TAG = "BatchOperationsAPIRoute"
  }

  private val kernelPublicKey: PublicKey? = helperObject.getKernelJWTPublicKey()

  fun startBatchOperationsV1Intent(batchOperationsV1Params: ReadableMap){
    val reliantGUID: String = batchOperationsV1Params.getString("reliantGUID")!!
    val programGUID: String = batchOperationsV1Params.getString("programGUID")!!
    val listOfOperations: ReadableMap? = batchOperationsV1Params.getMap("listOfOperations")

    Timber.d("reliantGUID: {$reliantGUID}")
    Timber.d("programGUID: {$programGUID}")
    Timber.d("listOfOperations: {$listOfOperations}")
    val intent = Intent(context, BiometricConsentCompassApiHandlerActivity::class.java).apply {
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID )
//      putParcelableArrayListExtra(Key.LIST_OF_OPERATIONS, listOfOperations)
    }

    currentActivity?.startActivityForResult(intent, BATCH_OPERATIONS_API_REQUEST_CODE)
  }

  fun handleBatchOperationsV1IntentResponse(
    resultCode: Int,
    data: Intent?,
    promise: Promise
  ) {
    when (resultCode) {
      Activity.RESULT_OK -> {
        val resultMap = Arguments.createMap()
        val response: BatchOperationResponseV1 = data?.extras?.get(Key.DATA) as BatchOperationResponseV1
        val executedList = Arguments.createArray()
        val skippedList = Arguments.createArray()

        response.data.forEach { batchOperationResultV1 ->
          if(batchOperationResultV1.executionStatus == ExecutionStatus.EXECUTED){
            executedList.pushMap(extractedResult(batchOperationResultV1))
          } else if(batchOperationResultV1.executionStatus == ExecutionStatus.SKIPPED) {
            skippedList.pushMap(extractedResult(batchOperationResultV1))
          }
        }

        resultMap.putArray("executedList", executedList)
        resultMap.putArray("skippedList", skippedList)

        // Resolve
        promise.resolve(resultMap);
      }
      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message = data?.getStringExtra(Key.ERROR_MESSAGE) ?: context.getString(R.string.error_unknown)
        Timber.e("Error $code Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }

  private fun extractedResult(it: BatchOperationResultV1): WritableMap {
    val result = it.result.result

    return when(it.operation){
      is OperationsV1.RegistrationData -> { Arguments.createMap().apply { putString("RegistrationData", GsonUtils.toJson(result as RegistrationStatusData)) }}
      is OperationsV1.BasicRegistrationDifferentProgram -> { Arguments.createMap().apply { putString("basicRegistrationDifferentProgram", GsonUtils.toJson(result as String)) }}
      is OperationsV1.ConsumerDeviceNumber -> { Arguments.createMap().apply { putString("consumerDeviceNumber", GsonUtils.toJson(result as String)) }}
      is OperationsV1.UpdateCardProfile -> { Arguments.createMap().apply { putString("updateCardProfile", GsonUtils.toJson(result as String)) }}
      is OperationsV1.WritePasscode -> { Arguments.createMap().apply { putString("writePasscode", GsonUtils.toJson(result as BatchBaseResponse)) }}
      is OperationsV1.VerifyPasscode -> { Arguments.createMap().apply { putString("verifyPasscode", GsonUtils.toJson(result as VerifyPasscodeResponse)) }}
      is OperationsV1.WriteProgramSpace -> { Arguments.createMap().apply { putString("writeProgramSpace", GsonUtils.toJson(result as WriteProgramSpaceDataResponse)) }}
      is OperationsV1.SvaList -> { Arguments.createMap().apply { putString("svaList", GsonUtils.toJson(result as List<*>)) }}
      is OperationsV1.SvaOperation -> { Arguments.createMap().apply { putString("svaOperation", GsonUtils.toJson(result as SVAOperationResult)) }}
      is OperationsV1.ReadSVA -> { Arguments.createMap().apply { putString("readSVA", GsonUtils.toJson(result as SVARecord)) }}
      is OperationsV1.CreateFinancialSva -> { Arguments.createMap().apply { putString("createFinancialSva", GsonUtils.toJson(result as BatchBaseResponse)) }}
      is OperationsV1.CreateEVoucherSva -> { Arguments.createMap().apply { putString("createEVoucherSva", GsonUtils.toJson(result as BatchBaseResponse)) }}
      is OperationsV1.WriteApplicationDataRecord -> { Arguments.createMap().apply { putString("writeApplicationDataRecord", GsonUtils.toJson(result as BatchBaseResponse)) }}
      is OperationsV1.ReadApplicationDataBlob -> { Arguments.createMap().apply { putString("readApplicationDataBlob", GsonUtils.toJson(result as ReadApplicationBlobResponse)) }}
      is OperationsV1.WriteApplicationDataBlob -> { Arguments.createMap().apply { putString("writeApplicationDataBlob", GsonUtils.toJson(result as String)) }}
      is OperationsV1.ReadAppDataChunk -> { Arguments.createMap().apply { putString("readAppDataChunk", GsonUtils.toJson(result as Array<*>)) }}
      is OperationsV1.ReadProgramSpace -> { Arguments.createMap().apply { putString("readProgramSpace", parseJWT((result as ReadProgramSpaceDataResponse).jwt, kernelPublicKey)) }}
      else -> { Arguments.createMap().apply { putString("result", GsonUtils.toJson(result)) }}
    }
  }

  private fun parseJWT(jwt: String, kernelPublicKey: PublicKey?): String {
    try {
      val data = Jwts.parserBuilder().setSigningKey(kernelPublicKey).build().parseClaimsJws(jwt).body
      return data[JwtConstants.JWT_PAYLOAD].toString()
    } catch (e: SignatureException) {
      Timber.tag(ReadProgramSpaceAPIRoute.TAG).e(e, "parseJWT: Failed to validate JWT")
    } catch (e: ExpiredJwtException) {
      Timber.tag(ReadProgramSpaceAPIRoute.TAG).e(e, "parseJWT: JWT expired")
    } catch (e: Exception) {
      Timber.tag(ReadProgramSpaceAPIRoute.TAG).e(e, "parseJWT: Claims passed from Kernel are empty, null or invalid")
    }
    return  ""
  }
}
