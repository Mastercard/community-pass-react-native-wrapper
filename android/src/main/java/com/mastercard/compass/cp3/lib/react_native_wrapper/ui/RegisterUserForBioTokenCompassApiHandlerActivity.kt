package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.base.OperationMode
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.populateModalityList
import com.mastercard.compass.model.biometrictoken.Modality
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key

class RegisterUserForBioTokenCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
    override suspend fun callCompassApi() {
        val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
        val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
        val consentID: String = intent.getStringExtra(Key.CONSENT_ID)!!
        val operationMode: String = intent.getStringExtra(Key.OPERATION_MODE)!!
        val modalities: ArrayList<String> = intent.getStringArrayListExtra(Key.MODALITIES)!!

        val jwt = helper.generateBioTokenJWT(
            reliantGUID, programGUID, consentID, populateModalityList(modalities)
        )

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
}

