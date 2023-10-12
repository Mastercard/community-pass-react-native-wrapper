package com.mastercard.compass.cp3.lib.react_native_wrapper.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.lifecycleScope
import com.mastercard.compass.model.consent.ConsentResponse
import com.mastercard.compass.cp3.lib.react_native_wrapper.CompassKernelUIController
import com.mastercard.compass.cp3.lib.react_native_wrapper.R
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.CompassIntentResponse
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.CompassResultContract
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.ErrorCode.UNKNOWN
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key.DATA
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key.ERROR_CODE
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key.ERROR_MESSAGE
import com.mastercard.compass.cp3.lib.react_native_wrapper.util.Key.RELIANT_APP_GUID
import com.mastercard.compass.kernel.client.service.KernelServiceConsumer
import com.mastercard.compass.model.programspace.ReadProgramSpaceDataResponse
import timber.log.Timber

abstract class CompassApiHandlerActivity<T : Any> : CompassKernelUIController.CompassKernelActivity() {

    companion object {
        protected const val TAG = "CompassApiIntentHandlerActivity"
    }

    protected val compassApiActivityResult = registerForActivityResult(CompassResultContract<T>()){
        when(it){
            is CompassIntentResponse.Success -> successFinishActivity(it.data)
            is CompassIntentResponse.Error -> errorFoundFinishActivity(it.code, it.message)
        }
    }

    protected fun getNonIntentCompassApiResults(value: T?) {
        when(value){
            is ConsentResponse -> successFinishActivity(value)
            is ReadProgramSpaceDataResponse -> successFinishActivity(value)
            is String -> successFinishActivity(value)
            else -> errorFoundFinishActivity(0, "Unknown error")
        }
    }

    lateinit var reliantGUID: String

    abstract suspend fun callCompassApi()

    private fun successFinishActivity(data: T){
        val intent = Intent().apply {
            when (data) {
              is ConsentResponse -> putExtra(DATA, data)
              is ReadProgramSpaceDataResponse -> putExtra(DATA, data)
              is String -> putExtra(DATA, data)
              is Parcelable -> putExtra(DATA, data)
            }
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun errorFoundFinishActivity(errorCode: Int?, errorMessage: String?) {
        val intent = Intent().apply {
            putExtra(ERROR_CODE, errorCode ?: UNKNOWN)
            putExtra(ERROR_MESSAGE, errorMessage ?: "unknown error")
        }
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Transparent)

        reliantGUID = intent.getStringExtra(RELIANT_APP_GUID)!!
        connectKernelService()
    }

    private fun connectKernelService() {
        connectKernelService(reliantGUID) { isSuccess, errorCode, errorMessage ->
            when (isSuccess) {
                true -> {
                    Timber.d("Connected to Kernel successfully")
                    startCompassCoroutine()
                }
                false -> {
                    Timber.e("Could not connect to Kernel. Code: $errorCode. Message: $errorMessage")
                    errorFoundFinishActivity(errorCode, errorMessage)
                }
            }
        }
    }

    private fun startCompassCoroutine() = lifecycleScope.launchWhenCreated {
        callCompassApi()
    }

}
