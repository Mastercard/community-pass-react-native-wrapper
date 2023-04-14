# User Lifecycle Management API

## 1 User Registration

### 1.1 saveBiometricConsent

This API is used to save the user consent prior to collecting the biometric(s) and generating a unique digital identity. This is a blocking call, therefore it is advised to perform on a non-UI thread.

```
NOTE: It is the responsibility of the Reliant Application to show and capture the user’s consent.
Then, the Reliant Application must store it with CPK.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.0.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| consentRequest | SaveBiometricConsentParamType | An object that contains a reliantGUID, programGUID and consumerConsentValue |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| consentResponse | Promise<`SaveBiometricConsentResultType`> | A promise that resolves to an object containing either a consentID and responseStatus fields or an error field. |

**Type Aliases**

```ts
// SaveBiometricConsentParamType
interface SaveBiometricConsentParamType {
  reliantGUID: string;
  programGUID: string;
  consumerConsentValue: boolean;
}

// SaveBiometricConsentResultType
interface SaveBiometricConsentResultType {
  consentID: string;
  responseStatus: string;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**                                | **Description**                                         |
| --------------------------------------------- | ------------------------------------------------------- |
| ERROR_CODE_PROGRAM_NOT_SUPPORTED              | Specified Program ID is not supported by CPK            |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_BIOMETRIC | Specified Program ID does not support biometric capture |

### 1.2 getRegisterBasicUser

This API is used to register an existing user with their card/CP Consumer Device present.

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|-----------------------------------|------------------------------------|
| + CPK 2.0.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| registerBasicUserRequest | RegisterBasicUserParamType | An object that contains a reliantGUID and programGUID |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| registerBasicUserResponse | Promise<`RegisterBasicUserResultType`> | A promise that resolves to an object containing either a rID field or an error field. |

**Type Aliases**

```ts
// RegisterBasicUserParamType
interface RegisterBasicUserParamType {
  reliantGUID: string;
  programGUID: string;
}

// RegisterBasicUserResultType
interface RegisterBasicUserResultType {
  rID: string;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

### 1.3 getRegisterUserWithBiometrics

This API is used by the Reliant Application to initiate the user registration flow. It returns the Intent object which can be used by the Reliant Application to start the user registration using the user’s biometric data. Following a successful user registration, a user profile is created and associated with a CP Program. If the user’s profile already exists, e.g., the user is already registered in another program, the user’s profile is updated with the new association. Moreover, it enables you to select a formfactor during the registration process i.e. Card, QR or None.

```
Warning: Reliant Application must obtain the consentID first using the saveBiometricConsent API before invoking the user registration flow.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|-----------------------------------|------------------------------------|
| + CPK 2.0.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| registerUserWithBiometricsRequest | RegisterUserWithBiometricsParamType | An object that contains a reliantGUID, programGUID and consentID |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| registerUserWithBiometricsResponse | Promise<`RegisterUserWithBiometricsResultType`> | A promise that resolves to an object containing either a rID, bioToken, enrolmentStatus and programGUID fields or an error field. |

**Type Aliases**

```ts
// RegisterUserWithBiometricsParamType
interface RegisterUserWithBiometricsParamType {
  reliantGUID: string;
  programGUID: string;
  consentID: string;
}

// RegisterUserWithBiometricsResultType
interface RegisterUserWithBiometricsResultType {
  programGUID: string;
  rID: string;
  bioToken: string;
  enrolmentStatus: string;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**                                     | **Description**                                              |
| -------------------------------------------------- | ------------------------------------------------------------ |
| ERROR_CODE_CONSENT_NOT_FOUND                       | Specified Consent Id is not found by CPK                     |
| ERROR_CODE_DIFFERENT_CONSENT_TYPE                  | Specified Consent Id was issued for a different consent type |
| ERROR_CODE_CONSENT_DOES_NOT_MATCH                  | Specified Consent Id does match                              |
| ERROR_CODE_INVALID_CONSENT                         | Specified Consent Id is not valid                            |
| ERROR_CODE_PROGRAM_NOT_SUPPORTED                   | Specified Program GUID is not supported by CPK               |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_BIOMETRIC      | Specified Program GUID does not support Biometric Capture    |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_QR_FORM_FACTOR | Specified Program does not support QR form factor            |

## 2 Card Issuance

### 2.1 getWritePasscode

This API is used to write the Passcode to the card. This is initiated by the Reliant Application to CPK after a successful user registration.

```
WARNING: The Passcode that will get stored on the card must be of Integer Datatype, and composed of 6 digits.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|-----------------------------------|------------------------------------|
| + CPK 2.0.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| writePasscodeRequest | WritePasscodeParamType | An object that contains a reliantGUID, programGUID, rID and passcode |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| writePasscodeResponse | Promise<`WritePasscodeResultType`> | A promise that resolves to an object containing either a responseStatus field or an error field. |

**Type Aliases**

```ts
// WritePasscodeParamType
interface WritePasscodeParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  passcode: string;
}

// WritePasscodeResultType
interface WritePasscodeResultType {
  responseStatus: string;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**                    | **Description**                                                                   |
| --------------------------------- | --------------------------------------------------------------------------------- |
| ERROR_CODE_CARD_NOT_ACTIVE        | The card is not in ACTIVE state                                                   |
| ERROR_CODE_CARD_BLACKLISTED       | Card is Blacklisted                                                               |
| ERROR_CODE_PROGRAM_GUID_NOT_MATCH | Program GUID does not match on the card                                           |
| ERROR_CODE_CARD_INVALID_PASSCODE  | Card passcode length is incorrect. Should be 6 digit long.                        |
| ERROR_CODE_CARD_CONNECTION_ERROR  | Card was moved or removed during read/write operation                             |
| ERROR_CODE_CARD_OPERATION_ABORTED | Card operation terminated before card transaction started by pressing Back button |

### 2.2 getWriteProfile

This API is used for card issuance to write the user’s basic profile data to the card once the user has been successfully registered, either by biometric flow or passcode flow. This operation is initiated by the Reliant Application after a successful user registration, and the Reliant Application receives the R-ID.

```
WARNING: The Passcode that will get stored on the card must be of Integer Datatype, and composed of 6 digits.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|-----------------------------------|------------------------------------|
| + CPK 2.0.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| writeProfileRequest | WriteProfileParamType | An object that contains a reliantGUID, programGUID, rID and overwriteCard |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| writeProfileResponse | Promise<`WriteProfileResultType`> | A promise that resolves to an object containing either a consumerDeviceNumber field or an error field. |

**Type Aliases**

```ts
// WriteProfileParamType
interface WriteProfileParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  overwriteCard: boolean;
}

// WriteProfileResultType
interface WriteProfileResultType {
  consumerDeviceNumber: string;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**                                  | **Description**                                                                                                                     |
| ----------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| ERROR_CODE_CARD_ALREADY_IN_USE                  | The card is already in ACTIVE state                                                                                                 |
| ERROR_CODE_CARD_BLACKLISTED                     | Card is Blacklisted                                                                                                                 |
| ERROR_CODE_PROGRAM_GUID_NOT_MATCH               | Program GUID does not match on the card                                                                                             |
| ERROR_CODE_CARD_CONNECTION_ERROR                | Card was moved or removed during read/write operation                                                                               |
| ERROR_CODE_INSUFFICIENT_DATA_WITH_CPK           | CPK does not have sufficient data to write/update the profile on the card                                                           |
| ERROR_CODE_AUTH_METHOD_BIOMETRIC_BUT_NO_HASHES  | User found, insufficient data cannot write profile on the card – missing modalities (if any LP, RP, Face configured to the program) |
| ERROR_CODE_INSUFFICIENT_HASHES_TO_WRITE_ON_CARD | Insufficient data cannot write hashes on the card– missing modalities (if any LP, RP, Face configured to the program)               |

## 3 User Authentication

### 3.1 getVerifyPasscode

This API is used to verify that the Passcode provided by the user is the same as the one present on the user’s card/CP Consumer Device. in this flow, the Reliant Application takes an input from the user, which consists of 6 digits, and passes it to CPK for passcode verification.

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|-----------------------------------|------------------------------------|
| + CPK 2.4.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| getVerfiyPasscodeRequest | GetVerifyPasscodeParamType | An object that contains the passcode, programGUID and reliantGUID |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| getVerifyPasscodeResponse | Promise<`GetVerifyPasscodeResultType`> | A promise that resolves to an object containing either the rId, counter value indicating the remaining passcode verification attempts and a boolean field (status) indicating whether the user is authenticated, or an error field. |

**Type Aliases**

```ts
// GetVerifyPasscodeParamType
interface GetVerifyPasscodeParamType {
  passcode: string,
  programGUID: string,
  reliantGUID: string
}

// GetVerifyPasscodeResultType
export interface GetVerifyPasscodeResultType {
  status: boolean,
  rId: string,
  counter: number
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**                                | **Description**                                         |
| --------------------------------------------- | ------------------------------------------------------- |
| ERROR_CODE_CARD_NOT_ACTIVE	                | The card is not in ACTIVE state |
| ERROR_CODE_CARD_BLACKLISTED	                | Card is blacklisted |
| ERROR_CODE_PROGRAM_GUID_NOT_MATCH	                | Program GUID does not match on the card            |
| ERROR_CODE_CARD_CONNECTION_ERROR	                | Card was moved or removed during read/write operation |
| ERROR_CODE_PIN_BLOCKED		                | Card has a blocked PIN |
| ERROR_CODE_APPLICATION_DATA_NOT_PRESENT			                | Application data not written on the card for this application
 | ERROR_CODE_CARD_OPERATION_ABORTED				                | Card operation terminated before card 
 | ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_QR_FORM_FACTOR	 | Specified Program does not support QR form factor | ERROR_CODE_FORM_FACTOR_BLACKLISTED	 | Specified FormFactor is blacklisted
| ERROR_CODE_INVALID_CP_USER_PROFILE | Invalid Cp User Profile
| ERROR_CODE_QR_PASSCODE_VERIFICATION_BLOCKED	 | This device has reached the maximum failed passcode attempts. Kindly reset your Passcode



[Return to API reference](README.md)
