package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.biometrictoken.Modality

class UserVerificationCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {

    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!

    val requestJwt = helper.generateJWT(reliantAppGUID = reliantGUID, programGuid = programGUID, listOf(
      Modality.FACE,
      Modality.LEFT_PALM,
      Modality.RIGHT_PALM
    ))

    val verificationIntent = compassKernelServiceInstance?.getUserVerificationActivityIntent(requestJwt, reliantGUID)

    compassApiActivityResult.launch(verificationIntent)
  }
}
