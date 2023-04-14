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
  programSpaceData: String | null;
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

[Return to API reference](README.md)
