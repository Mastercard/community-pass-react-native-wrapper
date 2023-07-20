package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.sva.*

class SVAOperationCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {

  private lateinit var svaOperation: SVAOperation

  override suspend fun callCompassApi() {
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val rId: String = intent.getStringExtra(Key.RID)!!
    val svaAmount: Int = intent.getIntExtra(Key.SVA_AMOUNT, 0)
    val svaOperationType: String = intent.getStringExtra(Key.SVA_OPERATION_TYPE)!!
    val svaUnit: String = intent.getStringExtra(Key.SVA_UNIT)!!
    when (svaOperationType){
      "DECREASE" ->  {
        svaOperation = SVAOperation(unit = svaUnit, amount = svaAmount, operationType = SVAOperationType.DECREASE)
      }
      "INCREASE" ->  {
        svaOperation = SVAOperation(unit = svaUnit, amount = svaAmount, operationType = SVAOperationType.INCREASE)
      }
      "UPDATE" ->  {
        svaOperation = SVAOperation(unit = svaUnit, amount = svaAmount, operationType = SVAOperationType.UPDATE)
      }
    }
    val intent = compassKernelServiceInstance.getSVAOperationActivityIntent(programGUID = programGUID, reliantAppGuid = reliantGUID, rID = rId, svaOperation = svaOperation)
    compassApiActivityResult.launch(intent)
  }
}
