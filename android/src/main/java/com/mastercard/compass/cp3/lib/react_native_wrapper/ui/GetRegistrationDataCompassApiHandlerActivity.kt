package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key

class GetRegistrationDataCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!

    val intent = compassKernelServiceInstance.getRegistrationDataActivityIntent(
      programGuid = programGUID, reliantAppGuid = reliantGUID
    )
    compassApiActivityResult.launch(intent)
  }
}
