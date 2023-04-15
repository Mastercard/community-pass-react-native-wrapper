package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.base.OperationMode
import com.mastercard.compass.model.biometrictoken.Modality
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key

class RegisterUserForBioTokenCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
    override suspend fun callCompassApi() {
        val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
        val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
        val consentID: String = intent.getStringExtra(Key.CONSENT_ID)!!
        val operationMode: String = intent.getStringExtra(Key.OPERATION_MODE)!!
        val face: Boolean = intent.getBooleanExtra(Key.FACE_MODALITY, true)
        val leftPalm: Boolean = intent.getBooleanExtra(Key.LEFT_PALM_MODALITY, true)
        val rightPalm: Boolean = intent.getBooleanExtra(Key.RIGHT_PALM_MODALITY, true)


        val jwt = helper.generateBioTokenJWT(
            reliantGUID, programGUID, consentID, populateModalityList(face, leftPalm, rightPalm))

        val intent = compassKernelServiceInstance.getRegisterUserForBioTokenActivityIntent(
            jwt,
            reliantGUID,
            getOperationMode(operationMode)
        )

        compassApiActivityResult.launch(intent)
    }

  private fun getOperationMode(operationMode: String): OperationMode {
    return if(operationMode == Key.BEST_AVAILABLE) OperationMode.BEST_AVAILABLE else OperationMode.FULL
  }
  private fun populateModalityList (face: Boolean, leftPalm: Boolean, rightPalm: Boolean) : MutableList<Modality> {
    val listOfModalities = mutableListOf<Modality>()

    if(face) listOfModalities.add(Modality.FACE)
    if(leftPalm) listOfModalities.add(Modality.LEFT_PALM)
    if(rightPalm) listOfModalities.add(Modality.RIGHT_PALM)

    return listOfModalities
  }
}
