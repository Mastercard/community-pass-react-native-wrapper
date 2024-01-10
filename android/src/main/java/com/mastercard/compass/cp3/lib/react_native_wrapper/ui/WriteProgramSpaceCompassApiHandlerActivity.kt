package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import android.content.Intent
import com.mastercard.compass.cp3.lib.react_native_wrapper.BuildConfig
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultCryptoService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultTokenService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpaceApi
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpaceValidationEncryptionError
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpaceValidationEncryptionResponse
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.performSharedSpaceKeyExchange
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key
import com.mastercard.compass.model.programspace.WriteProgramSpaceDataRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

class WriteProgramSpaceCompassApiHandlerActivity: CompassApiHandlerActivity<String>() {
  companion object {
    const val TAG = "WriteProgramSpaceCompassApiHandlerActivity"
  }

  override suspend fun callCompassApi() {
    val programGUID: String = intent.getStringExtra(Key.PROGRAM_GUID)!!
    val reliantGUID: String = intent.getStringExtra(Key.RELIANT_APP_GUID)!!
    val rID: String = intent.getStringExtra(Key.RID)!!
    val programSpaceData: String = intent.getStringExtra(Key.PROGRAM_SPACE_DATA)!!
    val encryptData: Boolean = intent.getBooleanExtra(Key.ENCRYPT_DATA, false)

    val sharedSpaceApi = SharedSpaceApi(
      compassKernelServiceInstance,
      reliantGUID,
      programGUID,
      DefaultCryptoService(helper),
      DefaultTokenService(
        helper.getKernelGuid()!!,
        helper.getKernelJWTPublicKey()!!,
        helper.getReliantAppJWTKeyPair().private,
        reliantGUID
      )
    )

    if (encryptData && helper.getKernelSharedSpaceKey() == null) {
      performSharedSpaceKeyExchange(sharedSpaceApi = sharedSpaceApi, helper = helper)
    }

    val validationResponse: SharedSpaceValidationEncryptionResponse =
      sharedSpaceApi.validateEncryptData(input = programSpaceData)

    if (validationResponse is SharedSpaceValidationEncryptionResponse.Success) {
      val intent = compassKernelServiceInstance.getWriteProgramSpaceActivityIntent(
        WriteProgramSpaceDataRequest(
          programGUID,
          reliantGUID,
          rID,
          validationResponse.data
        )
      )

      compassApiActivityResult.launch(intent)
    } else if (validationResponse is SharedSpaceValidationEncryptionResponse.Error) {
      val errorMessage = when (validationResponse.error) {
        SharedSpaceValidationEncryptionError.ERROR_FETCHING_SCHEMA -> "Error fetching schema"
        SharedSpaceValidationEncryptionError.EMPTY_SCHEMA_CONFIG -> "Error empty schema"
        SharedSpaceValidationEncryptionError.ENCRYPTION_SERVICE_REQUIRED -> "Error encryption service required"
        SharedSpaceValidationEncryptionError.SCHEMA_PROCESSOR_ERROR -> "Error schema processor"
      }

      val intent = Intent().apply {
        putExtra(Key.ERROR_CODE, 0)
        putExtra(Key.ERROR_MESSAGE, errorMessage)
      }

      setResult(RESULT_CANCELED, intent)
      finish()
    }
  }
}
