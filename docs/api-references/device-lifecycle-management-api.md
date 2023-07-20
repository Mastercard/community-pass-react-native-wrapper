# Device Lifecycle Management API

## 1 Card Status

### 1.1 getRegistrationData

This API is used to check the registration status of the card, i.e. if the card user is registered with CP on behalf of the program. For that, this API will fetch the registration data from the card which include the R-ID and the authentication method.

```
NOTE: It is the responsibility of the Reliant Application to show and capture the user’s consent.
Then, the Reliant Application must store it with CPK.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.4.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| getRegistrationDataRequest | RegistrationDataParamType | An object that contains a reliantGUID and programGUID |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| getRegistrationDataResponse | Promise<`RegistrationDataResultType`> | A promise that resolves to an object containing a list of authMethods, a list of modalityTypes, rID and an isRegisteredInProgram fields, or an error field. |

**Type Aliases**

```ts
// RegistrationDataParamType
interface RegistrationDataParamType {
  reliantGUID: string;
  programGUID: string;
}

// RegistrationDataResultType
interface RegistrationDataResultType {
  isRegisteredInProgram: boolean;
  authMethods: AuthType[];
  modalityType: Modality[];
  rID: string;
}

enum AuthType {
  NONE = 'NONE',
  CARD_PRESENT = 'CARD_PRESENT',
  PASSCODE = 'PASSCODE',
  BIO = 'BIO',
}

enum Modality {
  FACE = 'FACE',
  LEFT_PALM = 'LEFT_PALM',
  RIGHT_PALM = 'RIGHT_PALM',
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**              | **Description**                                                                                  |
| --------------------------- | ------------------------------------------------------------------------------------------------ |
| ERROR_CODE_INVALID_ARGUMENT | Arguments passed in request parameters are either blank or incorrect. Refer to the error message |
| ERROR_CODE_CARD_NOT_ACTIVE  | The card is not in ACTIVE state                                                                  |
| ERROR_CODE_CARD_BLACKLISTED | Card is blacklisted                                                                              |

## 2. Blacklist Form Factor

### 2.1. getBlacklistFormFactorActivityIntent

This API manages blacklisting of formfactor. Card or CP User Profile to be added to the Blacklist when the user reports a specified form factor lost or stolen to the RA or simply no longer contains the latest data and has been replaced with a newer issuance of a fresh CP Consumer Device.

```
Pre-requisite
- RA is aware of the ProgramGUID, R-ID, RA GUID and consumer device id
- Cards/CP Consumer Device has been issued by the Program
- RA performs the reading of the Program data prior to calling the Blacklist in case of CP User Profile Form Factor.
- RA reads the application data and stores it on their end in case of QR Form Factor.
- RA does verification/ identification of the user (Biometrical/ Passcode), to lookup Consumer device number prior to invoking the blacklist API.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.5.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| getBlacklistFormFactorRequest | BlacklistFormFactorParamType | An object that contains a reliantGUID, programGUID, rID, consumerDeviceNumber and FormFactor |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| getBlacklistFormFactorResponse | Promise<`BlacklistFormFactorResultType`> | A promise that resolves to an object containnt the blacklisted Formfactor, e.g CARD, and the consumerDeviceNumber, or an error field. |

**Type Aliases**

```ts
// BlacklistFormFactorResultType
interface BlacklistFormFactorResultType {
  type: string;
  status: FormFactorStatus;
  consumerDeviceNumber: string;
}

// BlacklistFormFactorParamType
interface BlacklistFormFactorParamType {
  programGUID: string;
  rID: string;
  reliantGUID: string;
  consumerDeviceNumber: string;
  formFactor: FormFactor;
}

// FormFactor
export enum FormFactor {
  CARD = 'CARD',
  QR = 'QR', 
  NONE = 'NONE'
}

//FormFactorStatus
export enum FormFactorStatus {
  ACTIVE = 'ACTIVE',
  BLACKLISTED = 'BLACKLISTED', 
  UNKNOWN = 'UNKNOWN',
  BLOCKED = 'BLOCKED'
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**              | **Description**                                                                                  |
| --------------------------- | ------------------------------------------------------------------------------------------------ |
| ERROR_CODE_FORM_FACTOR_BLACKLISTED	 | The Form Factor is Already Blacklisted
| ERROR_CODE_FORM_FACTOR_NOT_ASSOCIATED_TO_USER	  | This form factor does not belong to the RID provided                                                                  |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_QR_FORM_FACTOR	 | Specified Program does not CP User Profile form factor support
         


[Return to API reference](README.md)
