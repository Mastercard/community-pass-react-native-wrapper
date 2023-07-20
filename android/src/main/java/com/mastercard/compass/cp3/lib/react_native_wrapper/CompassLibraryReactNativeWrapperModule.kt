package com.mastercard.compass.cp3.lib.react_native_wrapper

import android.app.Activity
import android.content.Intent
import com.facebook.react.BuildConfig
import com.facebook.react.bridge.*
import com.mastercard.compass.cp3.lib.react_native_wrapper.route.*
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultCryptoService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.DefaultTokenService
import com.mastercard.compass.cp3.lib.react_native_wrapper.ui.util.SharedSpaceApi
import com.mastercard.compass.kernel.client.service.KernelServiceConsumer
import timber.log.Timber

class CompassLibraryReactNativeWrapperModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext),
  ActivityEventListener {
  private lateinit var promise: Promise;
  private var helperObject = CompassKernelUIController.CompassHelper(reactApplicationContext);
  private var defaultCryptoService = DefaultCryptoService(helperObject)

  private val consumerDeviceApiRoute: ConsumerDeviceAPIRoute by lazy {
    ConsumerDeviceAPIRoute(reactContext, currentActivity)
  }
  private val registerUserWithBiometricsAPIRoute: RegisterUserWithBiometricsAPIRoute by lazy {
    RegisterUserWithBiometricsAPIRoute(reactContext, currentActivity, helperObject)
  }
  private val registerBasicUserAPIRoute: RegisterBasicUserAPIRoute by lazy {
    RegisterBasicUserAPIRoute(reactContext, currentActivity)
  }
  private val consumerDevicePasscodeAPIRoute: ConsumerDevicePasscodeAPIRoute by lazy {
    ConsumerDevicePasscodeAPIRoute(reactContext, currentActivity)
  }
  private val biometricConsentAPIRoute: BiometricConsentAPIRoute by lazy {
    BiometricConsentAPIRoute(reactContext, currentActivity)
  }
  private val readProgramSpaceAPIRoute: ReadProgramSpaceAPIRoute by lazy {
    ReadProgramSpaceAPIRoute(reactContext, currentActivity, helperObject, defaultCryptoService)
  }
  private val writeProgramSpaceAPIRoute: WriteProgramSpaceAPIRoute by lazy {
    WriteProgramSpaceAPIRoute(reactContext, currentActivity)
  }
  private val retrieveRegistrationDataAPIRoute by lazy {
    RetrieveRegistrationDataAPIRoute(reactContext, currentActivity)
  }
  private val getVerifyPasscodeAPIRoute by lazy {
    GetVerifyPasscodeAPIRoute(reactContext, currentActivity)
  }
  private val userVerificationAPIRoute by lazy {
    UserVerificationAPIRoute(reactContext, currentActivity, helperObject)
  }
  private val createSvaAPIRoute by lazy {
    CreateSvaAPIRoute(reactContext, currentActivity)
  }
  private val readSvaAPIRoute by lazy {
    ReadSvaAPIRoute(reactContext, currentActivity)
  }
  private val svaOperationAPIRoute by lazy {
    SVAOperationAPIRoute(reactContext, currentActivity, helperObject)
  }
  private val blacklistFormFactorAPIRoute by lazy {
    BlacklistFormFactorAPIRoute(reactContext, currentActivity)
  }
  override fun getName(): String {
      return "CompassLibraryReactNativeWrapper"
  }

  init {
    super.initialize()
    if(BuildConfig.DEBUG){
        Timber.plant(Timber.DebugTree())
    }
    reactApplicationContext.addActivityEventListener(this)
  }

  @ReactMethod
  fun saveBiometricConsent(saveBiometricConsentParams: ReadableMap, promise: Promise){
    this.promise = promise

    biometricConsentAPIRoute.startBiometricConsentIntent(saveBiometricConsentParams)
  }

  @ReactMethod
  fun getWritePasscode(writePasscodeParams: ReadableMap, promise: Promise){
    this.promise = promise

    consumerDevicePasscodeAPIRoute.startWritePasscodeIntent(writePasscodeParams)
  }

  @ReactMethod
  fun getWriteProfile(writeProfileParams: ReadableMap, promise: Promise){
    this.promise = promise

    consumerDeviceApiRoute.startWriteProfileIntent(writeProfileParams)
  }

  @ReactMethod
  fun getRegisterBasicUser(registerBasicUserParams: ReadableMap, promise: Promise){
    this.promise = promise

  registerBasicUserAPIRoute.startRegisterBasicUserIntent(registerBasicUserParams);
  }

  @ReactMethod
  fun getRegisterUserWithBiometrics(registerUserWithBiometricsParams: ReadableMap, promise: Promise){
    this.promise = promise

    registerUserWithBiometricsAPIRoute.startRegisterUserWithBiometricsIntent(
      registerUserWithBiometricsParams
    );
  }

  @ReactMethod
  fun getReadProgramSpace(readProgramSpaceParams: ReadableMap, promise: Promise) {
    this.promise = promise

    readProgramSpaceAPIRoute.startReadProgramSpaceIntent(readProgramSpaceParams)
  }

  @ReactMethod
  fun getWriteProgramSpace(writeProgramSpaceParams: ReadableMap, promise:Promise){
    this.promise = promise

    writeProgramSpaceAPIRoute.startWriteProgramSpaceIntent(writeProgramSpaceParams)
  }

  @ReactMethod
  fun getRegistrationData(registrationDataParams: ReadableMap, promise: Promise){
    this.promise = promise
    retrieveRegistrationDataAPIRoute.startGetRegistrationIntent(registrationDataParams)
  }

  @ReactMethod
  fun getVerifyPasscode(verifyPasscodeParams: ReadableMap, promise: Promise){
    this.promise = promise
    getVerifyPasscodeAPIRoute.startGetVerifyPasscodeIntent(verifyPasscodeParams)
  }

  @ReactMethod
  fun getUserVerification(userVerificationParams: ReadableMap, promise: Promise){
    this.promise = promise
    userVerificationAPIRoute.startGetUserVerificationIntent(userVerificationParams)
  }

  @ReactMethod
  fun getCreateSVA(createSvaParams: ReadableMap, promise: Promise){
    this.promise = promise
    createSvaAPIRoute.startCreateSvaIntent(createSvaParams)
  }

  @ReactMethod
  fun getReadSVA(readSvaParams: ReadableMap, promise: Promise){
    this.promise = promise
    readSvaAPIRoute.startReadSvaIntent(readSvaParams)
  }

  @ReactMethod
  fun getSVAOperation(svaOperationParams: ReadableMap, promise: Promise){
    this.promise = promise
    svaOperationAPIRoute.startSVAOperationIntent(svaOperationParams)
  }

  @ReactMethod
  fun getBlacklistFormFactor(blackListFormFactorParams: ReadableMap, promise: Promise){
    this.promise = promise
    blacklistFormFactorAPIRoute.startBlacklistFormFactorIntent(blackListFormFactorParams)
  }

  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    when(requestCode){
      in BiometricConsentAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in ConsumerDeviceAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in ConsumerDevicePasscodeAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in RegisterUserWithBiometricsAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in RegisterBasicUserAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in ReadProgramSpaceAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in WriteProgramSpaceAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in RetrieveRegistrationDataAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in GetVerifyPasscodeAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in UserVerificationAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in CreateSvaAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in ReadSvaAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in SVAOperationAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
      in BlacklistFormFactorAPIRoute.REQUEST_CODE_RANGE -> handleApiRouteResponse(requestCode, resultCode, data)
    }
  }

  override fun onNewIntent(p0: Intent?) {
      //to be implemented
  }

  private fun handleApiRouteResponse(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    when (requestCode) {
      BiometricConsentAPIRoute.BIOMETRIC_CONSENT_REQUEST_CODE -> biometricConsentAPIRoute.handleBiometricConsentIntentResponse(resultCode, data, this.promise)
      ConsumerDeviceAPIRoute.WRITE_PROFILE_REQUEST_CODE -> consumerDeviceApiRoute.handleWriteProfileIntentResponse(resultCode, data, this.promise)
      ConsumerDevicePasscodeAPIRoute.WRITE_PASSCODE_REQUEST_CODE -> consumerDevicePasscodeAPIRoute.handleWritePasscodeIntentResponse(resultCode, data, this.promise)
      RegisterUserWithBiometricsAPIRoute.REGISTER_BIOMETRICS_REQUEST_CODE -> registerUserWithBiometricsAPIRoute.handleRegisterUserWithBiometricsIntentResponse(resultCode, data, this.promise)
      RegisterBasicUserAPIRoute.REGISTER_BASIC_USER_REQUEST_CODE -> registerBasicUserAPIRoute.handleRegisterBasicUserIntentResponse(resultCode, data, this.promise)
      ReadProgramSpaceAPIRoute.READ_PROGRAM_SPACE_REQUEST_CODE -> readProgramSpaceAPIRoute.handleReadProgramSPaceIntentResponse(resultCode, data, this.promise)
      WriteProgramSpaceAPIRoute.WRITE_PROGRAM_SPACE_REQUEST_CODE -> writeProgramSpaceAPIRoute.handleWriteProgramSpaceIntentResponse(resultCode, data, this.promise)
      RetrieveRegistrationDataAPIRoute.GET_REGISTRATION_DATA_REQUEST_CODE -> retrieveRegistrationDataAPIRoute.handleGetRegistrationDataIntentResponse(resultCode, data, this.promise)
      GetVerifyPasscodeAPIRoute.GET_VERIFY_PASSCODE_REQUEST_CODE -> getVerifyPasscodeAPIRoute.handleGetVerifyPasscodeIntentResponse(resultCode, data, this.promise)
      UserVerificationAPIRoute.GET_USER_VERIFICATION_REQUEST_CODE -> userVerificationAPIRoute.handleGetUserVerificationIntentResponse(resultCode, data, promise)
      CreateSvaAPIRoute.GET_CREATE_SVA_REQUEST_CODE -> createSvaAPIRoute.handleGetCreateSvaIntentResponse(resultCode, data, promise)
      ReadSvaAPIRoute.GET_READ_SVA_REQUEST_CODE -> readSvaAPIRoute.handleGetReadSvaIntentResponse(resultCode, data, promise)
      SVAOperationAPIRoute.GET_SVA_OPERATION_REQUEST_CODE -> svaOperationAPIRoute.handleSvaOperationIntentResponse(resultCode, data, promise)
      BlacklistFormFactorAPIRoute.GET_BLACKLIST_FORMFACTOR_REQUEST_CODE -> blacklistFormFactorAPIRoute.handleBlacklistFormFactorIntentResponse(resultCode, data, promise)
    }
  }
}
