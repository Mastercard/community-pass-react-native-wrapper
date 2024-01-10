package com.mastercard.compass.cp3.lib.react_native_wrapper.data



data class ReadAppDataChunkBatchOperation(val indexes: ArrayList<Int>, val programGUID: String, val name: String)
data class ReadApplicationDataBlobBatchOperation(val programGUID: String, val name: String)

data class BasicRegistrationDifferentProgramBatchOperation(val programGUID: String, val name: String)

data class RegistrationDataBatchOperation(val programGUID: String, val name: String)

data class UpdateCardProfileBatchOperation(val rID: String, val programGUID: String, val name: String)

data class WritePasscodeBatchOperation(val passcode: String, val rID: String, val programGUID: String, val name: String)

data class VerifyPasscodeBatchOperation(val passcode: String, val programGUID: String, val shouldContinueOnVerificationFail: Boolean)

data class WriteProgramSpaceBatchOperation(val rID: String, val programGUID: String, val data: String, val name: String)

data class SvaListBatchOperation(val programGUID: String, val name: String)

data class SvaOperationBatchOperation(val svaOperation: Map<String, String>, val programGUID: String, val name: String)

data class ConsumerDeviceNumberBatchOperation(val programGUID: String, val name: String)

data class ReadSVABatchOperation(val svaUnit: String, val programGUID: String, val name: String)

data class ReadProgramSpaceBatchOperation(val programGUID: String, val name: String, val decryptData: Boolean)

data class CreateFinancialSvaBatchOperation(val sva: Map<String, Map<String, String>>, val programGUID: String, val name: String)

data class CreateEVoucherSvaBatchOperation(val sva: Map<String, Map<String, String>>, val programGUID: String, val name: String)

data class WriteApplicationDataRecordBatchOperation(val appDataRecord: ArrayList<Map<String, String>>, val programGUID: String, val name: String)

data class WriteApplicationDataBlobBatchOperation(val dataBlob: String, val programGUID: String, val name: String)
