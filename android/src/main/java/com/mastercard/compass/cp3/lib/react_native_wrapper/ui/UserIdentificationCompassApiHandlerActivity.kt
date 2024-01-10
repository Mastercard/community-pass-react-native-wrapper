package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.getFormFactor
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.populateModalityList
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key

class UserIdentificationCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  override suspend fun callCompassApi() {
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val modalities: ArrayList<String> = intent.getStringArrayListExtra(Key.MODALITIES)!!
    val cacheHashesIfIdentified: Boolean = intent.getBooleanExtra(Key.CACHE_HASHES_IF_IDENTIFIED, true)
    val formFactor: String = intent.getStringExtra(Key.FORMFACTOR)!!

    val jwt = helper.generateJWT(
      reliantAppGUID = reliantGUID,
      programGuid = programGUID,
      modalities = populateModalityList(modalities),
      formFactor = getFormFactor(formFactor)
    )


    val intent = compassKernelServiceInstance.getUserIdentificationActivityIntent(
      reliantAppGuid = reliantGUID,
      jwt = jwt,
      cacheHashesIfIdentified = cacheHashesIfIdentified
    )

    compassApiActivityResult.launch(intent)
  }
}
