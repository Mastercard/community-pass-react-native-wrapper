# Program Data Management API

Note to developers:

- The Program must support the card related operations
- The Reliant Application must display the UI while interacting with card, during any read/write operation
- The Reliant Application must check if the user profile is already created and written on the card prior to any read operation
- The Reliant Application must check if the card is active and not blacklisted prior to any read or write operation
- The CPK checks if the R-ID provided is associated with the user profile stored on the card
- Prior to any read operation, the data must be written to the card using the respective write API, e.g., to use `getReadProgramSpace`, the data should be written using the `getWriteProgramSpace` API

## 1 Program Data Services

Program data services provide the capability to write data on the card for a specific program across their Reliant Apps. The data will be stored on the program space within the card. Program owners need to request access to the program space capabilities during the on-boarding process.

Below is the sequence of the API in order to use program data service

1. Call getWriteprogramSpace API - To write operation.
2. Call getReadprogramSpace API - To read operation.

### 1.1 getWriteProgramSpace

This API is used to by the Reliant Application to write the application data to the card’s program space.

> WARNING: Data written on card must be less than 1596 bytes in size.

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.5.0 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| writeProgramSpaceRequest | WriteProgramSpaceParamType | An object that contains a reliantGUID, programGUID, rID, programSpaceData and encryptData |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| writeProgramSpaceResponse | Promise<`WriteProgramSpaceResultType`> | A promise that resolves to an object containing either a isSuccess field or an error field. |

**Type Aliases**

```ts
// WriteProgramSpaceParamType
interface WriteProgramSpaceParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  programSpaceData: string;
  encryptData: boolean;
}

// WriteProgramSpaceResultType
interface WriteProgramSpaceResultType {
  isSuccess: boolean;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| Error Code                                        | Description                                                                                     |
| ------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ERROR_CODE_CARD_NOT_ACTIVE                        | Card is not in ACTIVE state                                                                     |
| ERROR_CODE_UNABLE_TO_READ_CARD_NUMBER             | CP Kernel is not able to read the Card Serial number                                            |
| ERROR_CODE_CARD_BLACKLISTED                       | Card is Blacklisted                                                                             |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_CARD          | Program does not support card operations                                                        |
| ERROR_CODE_CARD_INVALID                           | Card’s internal state is not ready for the operation e.g., profile or program is not created    |
| ERROR_CODE_CARD_COMMUNICATION_ERROR               | Error occured while reading/writing to the card                                                 |
| ERROR_CODE_APPLICATION_DATA_SIZE_EXCEEDED         | The requested data block size exceeded the limit on card                                        |
| ERROR_CODE_CARD_NOT_ASSOCIATED_TO_USER            | Card number not associated to the user                                                          |
| ERROR_CODE_WRITE_APPLICATION_DATA_FAILED          | Application data was not written to the card                                                    |
| ERROR_CODE_PROGRAM_SPACE_NOT_SUPPORTED            | Program space operation is not supported by the application                                     |
| ERROR_CODE_PROGRAM_SPACE_INVALID_CONFIGURATIONS   | Program Space configuration in the request does not match the program’s specified configuration |
| ERROR_CODE_INVALID_ACCESS_FOR_REQUESTED_OPERATION | Invalid access for the requested operation                                                      |

### 1.2 getReadProgramSpace

This API is used to read the data written on the card’s program space.

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.5.0 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| readProgramSpaceRequest | ReadProgramSpaceParamType | An object that contains a reliantGUID, programGUID, rID and decryptData |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| readProgramSpaceResponse | Promise<`ReadProgramSpaceResultType`> | A promise that resolves to an object containing either a programSpaceData field or an error field. |

**Type Aliases**

```ts
// ReadProgramSpaceParamType
interface ReadProgramSpaceParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  decryptData: boolean;
}

// ReadProgramSpaceResultType
interface ReadProgramSpaceResultType {
  programSpaceData: String;
}
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| Error Code                                        | Description                                                                                     |
| ------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ERROR_CODE_CARD_NOT_ACTIVE                        | Card is not in ACTIVE state                                                                     |
| ERROR_CODE_UNABLE_TO_READ_CARD_NUMBER             | CP Kernel is not able to read the Card Serial number                                            |
| ERROR_CODE_CARD_BLACKLISTED                       | Card is Blacklisted                                                                             |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_CARD          | Program does not support card operations                                                        |
| ERROR_CODE_CARD_INVALID                           | Card’s internal state is not ready for the operation e.g., profile or program is not created    |
| ERROR_CODE_CARD_COMMUNICATION_ERROR               | Error occured while reading/writing to the card                                                 |
| ERROR_CODE_APPLICATION_DATA_SIZE_EXCEEDED         | The requested data block size exceeded the limit on card                                        |
| ERROR_CODE_CARD_NOT_ASSOCIATED_TO_USER            | Card number not associated to the user                                                          |
| ERROR_CODE_WRITE_APPLICATION_DATA_FAILED          | Application data was not written to the card                                                    |
| ERROR_CODE_PROGRAM_SPACE_NOT_SUPPORTED            | Program space operation is not supported by the application                                     |
| ERROR_CODE_PROGRAM_SPACE_INVALID_CONFIGURATIONS   | Program Space configuration in the request does not match the program’s specified configuration |
| ERROR_CODE_INVALID_ACCESS_FOR_REQUESTED_OPERATION | Invalid access for the requested operation                                                      |

## 2 Application SVA Management

### 2.1 getCreateSVA

This API is used to create the SVA provided by the Reliant Application on the card.

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.5.0 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| createSVARequest | CreateSVAParamType | An object that contains a reliantGUID, programGUID, rID and sva object |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| createSVAResponse | Promise<`CreateSVAResultType`> | A promise that resolves to an object containing a string response message from the CPK, or an Error Field |

**Type Aliases**

```ts
// CreateSVAParamType
interface CreateSVAParamType {
  reliantGUID: string;
  programGUID: string;
  rID?: string;
  sva: sva;
}

// CreateSVAResultType
interface CreateSVAResultType {
  response: string;
}

//sva object
```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| Error Code                                        | Description                                                                                     |
| ------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ERROR_CODE_CARD_NOT_ACTIVE                        | The Card is not in ACTIVE state                                                                     
| ERROR_CODE_CARD_BLACKLISTED                       | Card is Blacklisted                                                                             |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_CARD          | Program does not support card operations                                                        
| ERROR_CODE_CARD_NOT_ASSOCIATED_TO_USER	                          | Card is not associated to the passed rID value  
| ERROR_CODE_APPLICATION_DATA_NOT_PRESENT	              | Program/Application Data not written on the card
| ERROR_CODE_CARD_CONNECTION_ERROR	         | Error occured while reading/writing to the card                                        |
| ERROR_CODE_INVALID_ARGUMENT	            | Arguments passed in request parameters are either blank or incorrect. Refer to the error message                                                          |
| ERROR_CODE_SVA_ALREADY_CREATED	          | SVA with given sva unit value is already created on the card                                                    |
| ERROR_CODE_SVA_LIMIT_EXHAUSTED	           | Maximum SVA limit exhausted to create on card                                     |
| ERROR_CODE_CARD_OPERATION_ABORTED	   | Card operation terminated before card transaction started by pressing Back button

### 2.2 getReadSVA

This API is used to read an existing SVA from the card. It will return the SVARecord object that contains details pertaining to the SVA.

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.5.0 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| readSVARequest | ReadSVAParamType | An object that contains a reliantGUID, programGUID, rID and the svaUnit string |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| readSVAResponse | Promise<`ReadSVAResultType`> | A promise that resolves to an object containing the current SVA balance, transactionCount, purseType, unit, and lastTransaction Object, or an error message. |

**Type Aliases**

```ts
// ReadSVAParamType
interface ReadSVAParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  svaUnit: string;
}

// ReadSVAResultType
interface ReadSVAResultType {
  currentBalance: number;
  transactionCount: number;
  purseType: string;
  unit: string;
  lastTransaction: Transaction
}

//Transaction
interface Transaction {
  amount: number;
  balance: number;
}

```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| Error Code                                        | Description                                                                                     |
| ------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ERROR_CODE_CARD_NOT_ACTIVE                        | The Card is not in ACTIVE state                                                                     
| ERROR_CODE_CARD_BLACKLISTED                       | Card is Blacklisted                                                                             |
| ERROR_CODE_PROGRAM_DOES_NOT_SUPPORT_CARD          | Program does not support card operations                                                        
| ERROR_CODE_CARD_NOT_ASSOCIATED_TO_USER	                          | Card is not associated to the passed rID value  
| ERROR_CODE_APPLICATION_DATA_NOT_PRESENT	              | Program/Application Data not written on the card
| ERROR_CODE_CARD_CONNECTION_ERROR	         | Card was moved or removed during read/write operation                                        |
| ERROR_CODE_INVALID_ARGUMENT	            | Arguments passed in request parameters are either blank or incorrect. Refer to the error message                                                          |
| ERROR_CODE_CARD_OPERATION_ABORTED		          | Card operation terminated before card transaction started by pressing Back button                                                    |


### 2.3 getSVAOperation

This API is used to perform several SVA operations on the card using SVA specific data provided by the Reliant Application or the program. SVA Operations include updating, incrementing or decrementing the value of a particular SVA. If the executed operation is successful, it will return the SVAOperationResult object. Otherwise, it will return an error message wrapped inside the intent result.

```
NOTE:An amount must not be less than 0 because operations cannot be carried out with negative amount values, 0 being the minimum limit. An amount must not be greater than 2147483647 either, as this is the maximum limit value allowed for the operation to be carried out.
```

**Compatibility**
| **Available as of CPK version #** | **Deprecated as of CPK version #** |
|--------------------------------------------------|------------------------------------------------------------------|
| + CPK 2.5.0 | + n/a |

**Input Parameters**
| **Parameter** | **Type** | **Description** |
|---------------|----------|------------------------------------------------------|
| svaOperationRequest | SVAOperationParamType | An object that contains a reliantGUID, programGUID, rID and the svaUnit string |

**Response Parameters**
| **Parameter** | **Type** | **Description** |
|-----------------|-----------------|----------------------------------------------------------|
| svaOperationResponse | Promise<`SVAOperationResultType`> | A promise that resolves to an object containing the current SVAOperationResult, or an error message. |

**Type Aliases**

```ts
// SVAOperationType
enum SVAOperationType {
  DECREASE = 'DECREASE',
  INCREASE = 'INCREASE', 
  UPDATE = 'UPDATE'
}

// SVAOperation
interface SVAOperation {
  svaUnit: string;
  svaAmount: number;
  svaOperationType: SVAOperationType;
}

// SVAOperationParamType
interface SVAOperationParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  svaOperation: SVAOperation;
}

```

**Error codes**

In addition to the [general error codes](https://developer.mastercard.com/cp-kernel-integration-api/documentation/reference-pages/code-and-formats/), below are the error codes that CPK can send as part of the response:

| Error Code                                        | Description                                                                                     |
| ------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ERROR_CODE_NO_ERROR                        | Success                                                                   
| ERROR_CODE_INVALID_ARGUMENT	                       | Arguments passed in request parameters are either blank or incorrect. Refer to the error message                                                                             |
| ERROR_CODE_KEY_RETRIEVAL_ERROR	          | Internal Key Retrieval Error


[Return to API reference](README.md)
