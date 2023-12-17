package com.mastercard.compass.cp3.lib.react_native_wrapper.route

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.BasicRegistrationDifferentProgramBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.ConsumerDeviceNumberBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.CreateEVoucherSvaBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.CreateFinancialSvaBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.ReadAppDataChunkBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.ReadApplicationDataBlobBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.ReadProgramSpaceBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.ReadSVABatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.RegistrationDataBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.SvaListBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.SvaOperationBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.UpdateCardProfileBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.VerifyPasscodeBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.WriteApplicationDataBlobBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.WriteApplicationDataRecordBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.WritePasscodeBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.data.WriteProgramSpaceBatchOperation
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.BatchOperationsApiHandlerActivity
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultCryptoService
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.jwt.JwtConstants
import com.mastercard.compass.model.batch.response.BatchBaseResponse
import com.mastercard.compass.model.batch.response.ExecutionStatus
import com.mastercard.compass.model.batch.response.ReadApplicationBlobResponse
import com.mastercard.compass.model.batchV1.OperationsV1
import com.mastercard.compass.model.batchV1.response.BatchOperationResponseV1
import com.mastercard.compass.model.batchV1.response.BatchOperationResultV1
import com.mastercard.compass.model.card.ApplicationRecord
import com.mastercard.compass.model.card.RegistrationStatusData
import com.mastercard.compass.model.card.VerifyPasscodeResponse
import com.mastercard.compass.model.programspace.ReadProgramSpaceDataResponse
import com.mastercard.compass.model.programspace.WriteProgramSpaceDataResponse
import com.mastercard.compass.model.sva.EVoucherSVA
import com.mastercard.compass.model.sva.EVoucherType
import com.mastercard.compass.model.sva.FinancialSVA
import com.mastercard.compass.model.sva.SVA
import com.mastercard.compass.model.sva.SVAOperation
import com.mastercard.compass.model.sva.SVAOperationResult
import com.mastercard.compass.model.sva.SVAOperationType
import com.mastercard.compass.model.sva.SVARecord
import com.mastercard.compass.utils.GsonUtils
import com.mastercard.compass.utils.toParcelableIntArray
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import timber.log.Timber
import java.security.PublicKey
import java.security.SignatureException
import java.util.Base64

class BatchOperationsAPIRoute(
  private val context: ReactApplicationContext,
  private val currentActivity: Activity?,
  private val helperObject: CompassKernelUIController.CompassHelper,
  private val cryptoService: DefaultCryptoService?
  ) {
  companion object {
    val REQUEST_CODE_RANGE = 1600 until 1700
    const val BATCH_OPERATIONS_API_REQUEST_CODE = 1600
    private const val TAG = "BatchOperationsAPIRoute"
  }

  private var decryptProgramSpaceData: Boolean? = null

  fun startBatchOperationsV1Intent(batchOperationsV1Params: ReadableMap) {
    val reliantGUID: String = batchOperationsV1Params.getString("reliantGUID")!!
    val programGUID: String = batchOperationsV1Params.getString("programGUID")!!
    val listOfOperations: ReadableMap? = batchOperationsV1Params.getMap("listOfOperations")
    val shouldContinueOnError: Boolean = batchOperationsV1Params.getBoolean("shouldContinueOnError")

    val intent = Intent(context, BatchOperationsApiHandlerActivity::class.java).apply {
      putExtra(Key.PROGRAM_GUID, programGUID)
      putExtra(Key.RELIANT_APP_GUID, reliantGUID)
      putParcelableArrayListExtra(Key.LIST_OF_OPERATIONS, prepareOperationsList(listOfOperations))
      putExtra(Key.SHOULD_CONTINUE_ON_ERROR, shouldContinueOnError)
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
        val response: BatchOperationResponseV1 =
          data?.extras?.get(Key.DATA) as BatchOperationResponseV1
        val executedList = Arguments.createArray()
        val skippedList = Arguments.createArray()
        val abortedList = Arguments.createArray()

        response.data.forEach { batchOperationResultV1 ->
          if (batchOperationResultV1.executionStatus.name  == ExecutionStatus.EXECUTED.name) {
            executedList.pushMap(extractedResult(batchOperationResultV1))
          }
          else if (batchOperationResultV1.executionStatus == ExecutionStatus.SKIPPED) {
            skippedList.pushMap(extractedResult(batchOperationResultV1))
          } else {
            abortedList.pushMap(extractedResult(batchOperationResultV1))
          }
        }

        resultMap.putArray("executedList", executedList)
        resultMap.putArray("skippedList", skippedList)
        resultMap.putArray("abortedList", abortedList)

        // Resolve
        promise.resolve(resultMap);
      }

      Activity.RESULT_CANCELED -> {
        val code = data?.getIntExtra(Key.ERROR_CODE, ErrorCode.UNKNOWN).toString()
        val message =
          data?.getStringExtra(Key.ERROR_MESSAGE) ?: context.getString(R.string.error_unknown)
        Timber.e("Error $code Message $message")
        promise.reject(code, Throwable(message))
      }
    }
  }

  private fun extractedResult(it: BatchOperationResultV1): WritableMap {
    val result = it.result.result
    val resultMap = Arguments.createMap()

    if(result == null){
      return resultMap
    } else {
      when (it.operation) {
        is OperationsV1.RegistrationData -> { resultMap.putString("registrationData", GsonUtils.toJson(result as RegistrationStatusData)) }
        is OperationsV1.BasicRegistrationDifferentProgram -> { resultMap.putString("basicRegistrationDifferentProgram", GsonUtils.toJson(result)) }
        is OperationsV1.ConsumerDeviceNumber -> { resultMap.putString("consumerDeviceNumber", result as String) }
        is OperationsV1.UpdateCardProfile -> { resultMap.putString("updateCardProfile", result as String) }
        is OperationsV1.WritePasscode -> { resultMap.putString("writePasscode", GsonUtils.toJson(result as BatchBaseResponse)) }
        is OperationsV1.VerifyPasscode -> { resultMap.putString("verifyPasscode", GsonUtils.toJson(result as VerifyPasscodeResponse)) }
        is OperationsV1.WriteProgramSpace -> { resultMap.putString("writeProgramSpace", GsonUtils.toJson(result as WriteProgramSpaceDataResponse)) }
        is OperationsV1.SvaList -> { resultMap.putString("svaList", GsonUtils.toJson(result as List<*>)) }
        is OperationsV1.SvaOperation -> { resultMap.putString("svaOperation", GsonUtils.toJson(result as SVAOperationResult)) }
        is OperationsV1.ReadSVA -> { resultMap.putString("readSVA", GsonUtils.toJson(result as SVARecord)) }
        is OperationsV1.CreateFinancialSva -> { resultMap.putString("createFinancialSva", GsonUtils.toJson(result as BatchBaseResponse)) }
        is OperationsV1.CreateEVoucherSva -> { resultMap.putString("createEVoucherSva", GsonUtils.toJson(result as BatchBaseResponse)) }
        is OperationsV1.WriteApplicationDataRecord -> { resultMap.putString("writeApplicationDataRecord", GsonUtils.toJson(result as BatchBaseResponse)) }
        is OperationsV1.ReadApplicationDataBlob -> { resultMap.putString("readApplicationDataBlob", GsonUtils.toJson(result as ReadApplicationBlobResponse)) }
        is OperationsV1.WriteApplicationDataBlob -> { resultMap.putString("writeApplicationDataBlob", result as String) }
        is OperationsV1.ReadAppDataChunk -> { resultMap.putString("readAppDataChunk", GsonUtils.toJson(result as Array<*>)) }
        is OperationsV1.ReadProgramSpace -> { resultMap.putString("readProgramSpace",
            if (decryptProgramSpaceData == true) String(
              cryptoService!!.decrypt(
                parseJWT(
                  (result as ReadProgramSpaceDataResponse).jwt,
                  helperObject.getKernelJWTPublicKey()!!
                )
              )
            ) else parseJWT(
              (result as ReadProgramSpaceDataResponse).jwt,
              helperObject.getKernelJWTPublicKey()!!
            )
          )
        }
        else -> { resultMap.putString("result", GsonUtils.toJson(result)) }
      }
      return resultMap
    }
  }


  private fun parseJWT(jwt: String, kernelPublicKey: PublicKey): String {
    try {
      val data =
        Jwts.parserBuilder().setSigningKey(kernelPublicKey).build().parseClaimsJws(jwt).body
      return data[JwtConstants.JWT_PAYLOAD].toString()
    } catch (e: SignatureException) {
      Timber.tag(ReadProgramSpaceAPIRoute.TAG).e(e, "parseJWT: Failed to validate JWT")
    } catch (e: ExpiredJwtException) {
      Timber.tag(ReadProgramSpaceAPIRoute.TAG).e(e, "parseJWT: JWT expired")
    } catch (e: Exception) {
      Timber.tag(ReadProgramSpaceAPIRoute.TAG)
        .e(e, "parseJWT: Claims passed from Kernel are empty, null or invalid")
    }
    return ""
  }

  private fun prepareOperationsList(listOfOperations: ReadableMap?): ArrayList<OperationsV1> {
    val hashOfOperations = listOfOperations!!.toHashMap()
    val operationList = arrayListOf<OperationsV1>()

    hashOfOperations.forEach {
      if (it.key == "basicRegistrationDifferentProgram") {
        val requestData = GsonUtils.fromJson(it.value.toString(), BasicRegistrationDifferentProgramBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.BasicRegistrationDifferentProgram(
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "updateCardProfile") {
        val requestData = GsonUtils.fromJson(it.value.toString(), UpdateCardProfileBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.UpdateCardProfile(
            rid = requestData.rID,
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "writePasscode") {
        val requestData = GsonUtils.fromJson(it.value.toString(), WritePasscodeBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.WritePasscode(
            passcode = requestData.passcode,
            rid = requestData.rID,
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "verifyPasscode") {
        val requestData = GsonUtils.fromJson(it.value.toString(), VerifyPasscodeBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.VerifyPasscode(
            passcode = requestData.passcode,
            programGUID = requestData.programGUID,
            shouldContinueOnVerificationFail = requestData.shouldContinueOnVerificationFail
          )
        )
      } else if (it.key == "svaOperation") {
        val requestData = GsonUtils.fromJson(it.value.toString(), SvaOperationBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.SvaOperation(
            svaOperation = getSVAOperation(requestData.svaOperation),
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "writeApplicationDataBlob") {
        val requestData = GsonUtils.fromJson(it.value.toString(), WriteApplicationDataBlobBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.WriteApplicationDataBlob(
            dataBlob = Base64.getDecoder().decode(requestData.dataBlob),
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "writeApplicationDataRecord") {
        val requestData = GsonUtils.fromJson(it.value.toString(), WriteApplicationDataRecordBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.WriteApplicationDataRecord(
            appDataRecord = getApplicationRecords(
              requestData.appDataRecord
            ), programGUID = requestData.programGUID, name = requestData.name
          )
        )
      } else if (it.key == "createFinancialSva") {
        val requestData = GsonUtils.fromJson(it.value.toString(), CreateFinancialSvaBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.CreateFinancialSva(
            sva = getSVA(requestData.sva) as FinancialSVA,
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "createEVoucherSva") {
        val requestData = GsonUtils.fromJson(it.value.toString(), CreateEVoucherSvaBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.CreateEVoucherSva(
            sva = getSVA(requestData.sva) as EVoucherSVA,
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "writeProgramSpace") {
        val requestData = GsonUtils.fromJson(it.value.toString(), WriteProgramSpaceBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.WriteProgramSpace(
            rId = requestData.rID,
            data = requestData.data,
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "consumerDeviceNumber") {
        val requestData = GsonUtils.fromJson(it.value.toString(), ConsumerDeviceNumberBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.ConsumerDeviceNumber(
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "registrationData") {
        val requestData = GsonUtils.fromJson(it.value.toString(), RegistrationDataBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.RegistrationData(
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "readApplicationDataBlob") {
        val requestData =  GsonUtils.fromJson(it.value.toString(), ReadApplicationDataBlobBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.ReadApplicationDataBlob(
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "readAppDataChunk") {
        val requestData = GsonUtils.fromJson(it.value.toString(), ReadAppDataChunkBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.ReadAppDataChunk(
            indexes = requestData.indexes.toIntArray().toParcelableIntArray(),
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "readSVA") {
        val requestData = GsonUtils.fromJson(it.value.toString(), ReadSVABatchOperation::class.java)
        operationList.add(
          element = OperationsV1.ReadSVA(
            svaUnit = requestData.svaUnit,
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "svaList") {
        val requestData = GsonUtils.fromJson(it.value.toString(), SvaListBatchOperation::class.java)
        operationList.add(
          element = OperationsV1.SvaList(
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      } else if (it.key == "readProgramSpace") {
        val requestData = GsonUtils.fromJson(it.value.toString(), ReadProgramSpaceBatchOperation::class.java)
        decryptProgramSpaceData = requestData.decryptData
        operationList.add(
          element = OperationsV1.ReadProgramSpace(
            programGUID = requestData.programGUID,
            name = requestData.name
          )
        )
      }
    }
    operationList.sortBy { it.getPriority() }
    return operationList
  }


  private fun getSVAOperation(operation: Map<String, String>): SVAOperation {
    return SVAOperation(
      unit = operation.getValue("unit"),
      amount = operation["amount"]!!.toInt(),
      operationType = getSVAOperationType(operation["operation"]!!)!!
    )
  }

  private fun getApplicationRecords(list: ArrayList<Map<String, String>>): List<ApplicationRecord> {
    val listOfApplicationRecords = mutableListOf<ApplicationRecord>()
    list.forEach {
      listOfApplicationRecords.add(
        ApplicationRecord(
          index = it.getValue("index").toInt(),
          chunk = Base64.getDecoder().decode(it.getValue("chunk")),
          isShared = it.getValue("isShared").toBooleanStrict()
        )
      )
    }
    return listOfApplicationRecords
  }

  private fun getSVAOperationType(value: String): SVAOperationType? {
    return when (value) {
      SVAOperationType.DECREASE.name -> SVAOperationType.DECREASE
      SVAOperationType.INCREASE.name -> SVAOperationType.DECREASE
      SVAOperationType.UPDATE.name -> SVAOperationType.DECREASE
      else -> null
    }
  }

  private fun getSVA(sva: Map<String, Map<String, String>>): SVA? {
    return if (sva.keys.first() == "FinancialSVA") {
      val value: Map<String, String> = sva.getValue("FinancialSVA")
      FinancialSVA(
        unit = value.getValue(key = "unit")
      )
    } else if (sva.keys.first() == "EVoucherSVA") {
      val value: Map<String, String> = sva.getValue("EVoucherSVA")
      EVoucherSVA(
        unit = value.getValue(key = "unit"),
        eVoucherType = if (value.getValue(key = "eVoucherType") == EVoucherType.COMMODITY.name) EVoucherType.COMMODITY else EVoucherType.POINT
      )
    } else {
      null
    }
  }
}

