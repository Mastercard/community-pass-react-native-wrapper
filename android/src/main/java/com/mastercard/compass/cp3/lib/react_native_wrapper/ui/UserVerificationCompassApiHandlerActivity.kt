package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.populateModalityList
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key

class UserVerificationCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val modalities: ArrayList<String> = intent.getStringArrayListExtra(Key.MODALITIES)!!

    val requestJwt = helper.generateJWT(reliantGUID, programGUID, populateModalityList(modalities))

    val verificationIntent = compassKernelServiceInstance.getUserVerificationActivityIntent(requestJwt, reliantGUID)

    compassApiActivityResult.launch(verificationIntent)
  }
}
