package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.batchV1.BatchOperationRequestV1
import com.mastercard.compass.model.batchV1.OperationsV1

class BatchOperationsApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val shouldContinueOnError: Boolean = intent.getBooleanExtra(Key.SHOULD_CONTINUE_ON_ERROR, false)

    val intent = compassKernelServiceInstance.getBatchOperationActivityIntentV1(
      BatchOperationRequestV1(
        reliantAppGuid = reliantGUID,
        operations = mutableListOf<OperationsV1>(),//prepareOperationsList(),
        shouldContinueOnError = shouldContinueOnError,
        reliantAppInstanceId = helper.getInstanceId()!!
      )
    )

    compassApiActivityResult.launch(intent)
  }

//  private fun prepareOperationsList(listOfOperations: ArrayList<Map<String, String>>): List<OperationsV1>{
//    val operationList = mutableListOf<OperationsV1>()
//    listOfOperations.forEach {
//      if(it.containsKey("registrationData")){
//        val requestData = Json.decodeFromString<RegistrationDataBatchOperation>(it.getValue("registrationData"))
//        operationList.add(OperationsV1.RegistrationData(programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("basicRegistrationDifferentProgram")){
//        val requestData = Json.decodeFromString<BasicRegistrationDifferentProgramBatchOperation>(it.getValue("basicRegistrationDifferentProgram"))
//        operationList.add(OperationsV1.BasicRegistrationDifferentProgram(programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("consumerDeviceNumber")){
//        val requestData = Json.decodeFromString<ConsumerDeviceNumberBatchOperation>(it.getValue("consumerDeviceNumber"))
//        operationList.add(OperationsV1.ConsumerDeviceNumber(programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("updateCardProfile")){
//        val requestData = Json.decodeFromString<UpdateCardProfileBatchOperation>(it.getValue("updateCardProfile"))
//        operationList.add(OperationsV1.UpdateCardProfile(rid = requestData.rID, programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("writePasscode")) {
//        val requestData = Json.decodeFromString<WritePasscodeBatchOperation>(it.getValue("writePasscode"))
//        operationList.add(OperationsV1.WritePasscode(passcode = requestData.passcode, rid = requestData.rID, programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("verifyPasscode")) {
//        val requestData = Json.decodeFromString<VerifyPasscodeBatchOperation>(it.getValue("verifyPasscode"))
//        operationList.add(OperationsV1.VerifyPasscode(passcode = requestData.passcode, programGUID = requestData.programGUID, shouldContinueOnVerificationFail = requestData.shouldContinueOnVerificationFail))
//      } else if(it.containsKey("writeProgramSpace")) {
//        val requestData = Json.decodeFromString<WriteProgramSpaceBatchOperation>(it.getValue("writeProgramSpace"))
//        operationList.add(OperationsV1.WriteProgramSpace(rId = requestData.rID, data = requestData.data, programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("svaList")){
//        val requestData = Json.decodeFromString<SvaListBatchOperation>(it.getValue("svaList"))
//        operationList.add(OperationsV1.SvaList(programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("svaOperation")){
//        val requestData = Json.decodeFromString<SvaOperationBatchOperation>(it.getValue("svaOperation"))
//        operationList.add(OperationsV1.SvaOperation(svaOperation = getSVAOperation(requestData.svaOperation), programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("readSVA")){
//        val requestData = Json.decodeFromString<ReadSVABatchOperation>(it.getValue("readSVA"))
//        operationList.add(OperationsV1.ReadSVA(svaUnit = requestData.svaUnit, programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("createFinancialSva")){
//        val requestData = Json.decodeFromString<CreateFinancialSvaBatchOperation>(it.getValue("createFinancialSva"))
//        operationList.add(OperationsV1.CreateFinancialSva(sva = getSVA(requestData.sva) as FinancialSVA, programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("createEVoucherSva")){
//        val requestData = Json.decodeFromString<CreateEVoucherSvaBatchOperation>(it.getValue("createEVoucherSva"))
//        operationList.add(OperationsV1.CreateEVoucherSva(sva = getSVA(requestData.sva) as EVoucherSVA, programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("writeApplicationDataRecord")){
//        val requestData = Json.decodeFromString<WriteApplicationDataRecordBatchOperation>(it.getValue("writeApplicationDataRecord"))
//        operationList.add(OperationsV1.WriteApplicationDataRecord(appDataRecord = getApplicationRecords(requestData.appDataRecord), programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("readApplicationDataBlob")){
//        val requestData = Json.decodeFromString<ReadApplicationDataBlobBatchOperation>(it.getValue("readApplicationDataBlob"))
//        operationList.add(OperationsV1.ReadApplicationDataBlob(programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("writeApplicationDataBlob")) {
//        val requestData = Json.decodeFromString<WriteApplicationDataBlobBatchOperation>(it.getValue("writeApplicationDataBlob"))
//        operationList.add(OperationsV1.WriteApplicationDataBlob(dataBlob = Base64.getDecoder().decode(requestData.dataBlob), programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("readAppDataChunk")) {
//        val requestData = Json.decodeFromString<ReadAppDataChunkBatchOperation>(it.getValue("readAppDataChunk"))
//        operationList.add(OperationsV1.ReadAppDataChunk(indexes = requestData.indexes.toIntArray().toParcelableIntArray(), programGUID = requestData.programGUID, name = requestData.name))
//      } else if(it.containsKey("readProgramSpace")) {
//        val requestData = Json.decodeFromString<ReadProgramSpaceBatchOperation>(it.getValue("readProgramSpace"))
//        operationList.add(OperationsV1.ReadProgramSpace(programGUID = requestData.programGUID, name = requestData.name))
//      }
//    }
//    return operationList
//  }
}


//Batch Operations
//@Serializable
//data class ReadAppDataChunkBatchOperation(val indexes: ArrayList<Int>, val programGUID: String, val name: String)
//@Serializable
//data class ReadApplicationDataBlobBatchOperation(val programGUID: String, val name: String)
//@Serializable
//data class BasicRegistrationDifferentProgramBatchOperation(val programGUID: String, val name: String)
//@Serializable
//data class RegistrationDataBatchOperation(val programGUID: String, val name: String)
//@Serializable
//data class UpdateCardProfileBatchOperation(val rID: String, val programGUID: String, val name: String)
//@Serializable
//data class WritePasscodeBatchOperation(val passcode: String, val rID: String, val programGUID: String, val name: String)
//@Serializable
//data class VerifyPasscodeBatchOperation(val passcode: String, val programGUID: String, val shouldContinueOnVerificationFail: Boolean)
//@Serializable
//data class WriteProgramSpaceBatchOperation(val rID: String, val programGUID: String, val data: String, val name: String)
//@Serializable
//data class SvaListBatchOperation(val programGUID: String, val name: String)
//@Serializable
//data class SvaOperationBatchOperation(val svaOperation: Map<String, String>, val programGUID: String, val name: String)
//@Serializable
//data class ConsumerDeviceNumberBatchOperation(val programGUID: String, val name: String)
//@Serializable
//data class ReadSVABatchOperation(val svaUnit: String, val programGUID: String, val name: String)
//@Serializable
//data class ReadProgramSpaceBatchOperation(val programGUID: String, val name: String)
//@Serializable
//data class CreateFinancialSvaBatchOperation(val sva: Map<String, Map<String, String>>, val programGUID: String, val name: String)
//@Serializable
//data class CreateEVoucherSvaBatchOperation(val sva: Map<String, Map<String, String>>, val programGUID: String, val name: String)
//@Serializable
//data class WriteApplicationDataRecordBatchOperation(val appDataRecord: ArrayList<Map<String, String>>, val programGUID: String, val name: String)
//
//@Serializable
//data class WriteApplicationDataBlobBatchOperation(val dataBlob: String, val programGUID: String, val name: String)
