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
| + CPK 2.0.1 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| getRegistrationDataRequest | GetRegistrationDataParamType | An object that contains a reliantGUID and programGUID |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| getRegistrationDataResponse | Promise<`GetRegistrationDataResultType`> | A promise that resolves to an object containing either an authMethods array with a boolean field indicating whether the user is registered in the current program, or an error field. |

**Type Aliases**

```ts
// GetRegistrationDataParamType
interface GetRegistrationDataParamType {
  reliantGUID: string;
  programGUID: string;
}

// GetRegistrationDataResultType
interface GetRegistrationDataResultType {
  isRegisteredInProgram: boolean;
  authMethods: string[];
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| **Error Code**                                | **Description**                                         |
| --------------------------------------------- | ------------------------------------------------------- |
| ERROR_CODE_INVALID_ARGUMENT	                | Arguments passed in request parameters are either blank or incorrect. Refer to the error message            |
| ERROR_CODE_CARD_NOT_ACTIVE	                | The card is not in ACTIVE state |
| ERROR_CODE_CARD_BLACKLISTED	                | Card is blacklisted |