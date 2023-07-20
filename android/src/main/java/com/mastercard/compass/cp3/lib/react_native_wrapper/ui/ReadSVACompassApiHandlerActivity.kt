package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key

class ReadSVACompassApiHandlerActivity: CompassApiHandlerActivity<String>() {

  override suspend fun callCompassApi() {
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val rId: String = intent.getStringExtra(Key.RID)!!
    val svaUnit: String = intent.getStringExtra(Key.SVA_UNIT)!!
    val intent = compassKernelServiceInstance.getReadSVAActivityIntent(programGUID = programGUID, reliantAppGuid = reliantGUID, rID = rId, svaUnit = svaUnit)
    compassApiActivityResult.launch(intent)
  }
}
