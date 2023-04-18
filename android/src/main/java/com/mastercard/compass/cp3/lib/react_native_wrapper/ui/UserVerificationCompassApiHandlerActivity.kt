package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.populateModalityList
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.biometrictoken.Modality

class UserVerificationCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val face: Boolean = intent.getBooleanExtra(Key.FACE_MODALITY, true)
    val leftPalm: Boolean = intent.getBooleanExtra(Key.LEFT_PALM_MODALITY, true)
    val rightPalm: Boolean = intent.getBooleanExtra(Key.RIGHT_PALM_MODALITY, true)

    val requestJwt = helper.generateJWT(reliantGUID, programGUID, populateModalityList(face, leftPalm, rightPalm))

    val verificationIntent = compassKernelServiceInstance.getUserVerificationActivityIntent(requestJwt, reliantGUID)

    compassApiActivityResult.launch(verificationIntent)
  }
}
