package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.card.VerifyPasscodeRequest

class GetVerifyPasscodeCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {
    val passcode: String = intent.getStringExtra(Key.PASSCODE)!!
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val intent = compassKernelServiceInstance.getVerifyPasscodeActivityIntent(
      VerifyPasscodeRequest(passcode, programGUID)
    )
    compassApiActivityResult.launch(intent)
  }
}
