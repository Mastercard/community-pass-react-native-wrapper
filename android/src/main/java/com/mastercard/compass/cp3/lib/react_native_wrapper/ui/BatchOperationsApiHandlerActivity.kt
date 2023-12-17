package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.batchV1.BatchOperationRequestV1
import com.mastercard.compass.model.batchV1.OperationsV1

class BatchOperationsApiHandlerActivity: CompassApiHandlerActivity<String>() {
  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override suspend fun callCompassApi() {
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val listOfOperations: ArrayList<OperationsV1>  = intent.extras!!.get(Key.LIST_OF_OPERATIONS)  as ArrayList<OperationsV1>
    val shouldContinueOnError: Boolean =  intent.getBooleanExtra(Key.SHOULD_CONTINUE_ON_ERROR, false)

    val intent = compassKernelServiceInstance.getBatchOperationActivityIntentV1(
      BatchOperationRequestV1(
        reliantAppGuid = reliantGUID,
        operations = listOfOperations,
        shouldContinueOnError = shouldContinueOnError,
        reliantAppInstanceId = helper.getInstanceId()!!
      )
    )

    compassApiActivityResult.launch(intent)
  }
}
