# Section 7: Register and connect your React Native application with the Community Pass Service

## 7.1 Objectives

Now that you have successfully set up your device and installed the Community Pass React Native Wrapper on your project, you should be ready to start using the Community Pass Kernel Services. At the end of this segment, you will have completed the following:

- Connect your Reliant App and test your connection to the Community Pass Kernel

## 7.2 Prerequisites

Before you get started on this section, ensure you have completed the following:

- You will need to have completed sections 1, 2, 3, 4, 5 and 6 in the [Getting Started](README.md) guide.
- You have received a Reliant App and Program GUID: This is your reliant application identifier shared with you on email as part of our onboarding process.

## 7.3 Use the Community Pass APIs in your React Native Reliant Application

1. Open a terminal/command prompt and navigate to the root folder of your reliant application project

terminal/comand prompt 1

```sh
cd /path/TestApp
```

2. Start your reliant application by running the following command:

```sh
npx react-native start
```

or

```sh
yarn start
```

3. Ensure that your POI device has debug mode enabled and connect it to your computer.
4. Open another terminal and navigate to the root folder of your reliant application project.

terminal/comand prompt 2

```sh
cd /path/TestApp
```

5. Run the following command to install a debug variant apk of your reliant application to your POI device.

```sh
npx react-native run-android
```

or

```sh
yarn android
```

## 7.4 Usage Examples

> By default, your reliant application is connected to the community pass kernel if you can get a response from one of these APIs, as shown below.

Add the saveBiometricConsent() Commnity Pass API to your application

```typescript
saveBiometricConsent({
  reliantGUID: '120a2c48-461e-4312-91c8-c3ba586ad29a', //string
  programGUID: '6d498a97-fb8c-42b3-89a4-0fe13372f557', //string
  consumerConsentValue: true, //boolean
})
  .then((response: SaveBiometricConsentResultType) => {
    console.log(response); //{ "consentID": "c6873db3-3975-4773-ab49-3b6c3e5e03b9" , "responseStatus": "SUCCESS" }
  })
  .catch((error: ErrorResultType) => {
    console.log(error); //{ "code": "600", "message": "Instance registration failed" }
  });
```

Add the getRegisterUserWithBiometrics() compass API to your application

```typescript
getRegisterUserWithBiometrics({
  reliantGUID: '120a2c48-461e-4312-91c8-c3ba586ad29a', //string
  programGUID: '6d498a97-fb8c-42b3-89a4-0fe13372f557', //string
  consentID: 'c6873db3-3975-4773-ab49-3b6c3e5e03b9', //string
  modalities: [Modalities.FACE, Modalities.LEFT_PALM, Modalities.RIGHT_PALM],
  operationMode: OperationModes.BEST_AVAILABLE, // or OperationModes.FULL
})
  .then((response: RegisterUserWithBiometricsResultType) => {
    console.log(response); //{"bioToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", "enrolmentStatus": "NEW", "programGUID": "6d498a97-fb8c-42b3-89a4-0fe13372f557", "rID": "bc54738d3056638532982df19f5a9441509099ea"}
  })
  .catch((error: ErrorResultType) => {
    console.log(error); //{ "code": "500", "message": "Biometric capture failed" }
  });
```

Add the getRegisterBasicUser() Commnity Pass API to your application

```typescript
getRegisterBasicUser({
  reliantGUID: '120a2c48-461e-4312-91c8-c3ba586ad29a', //string
  programGUID: '6d498a97-fb8c-42b3-89a4-0fe13372f557', //string
})
  .then((response: RegisterBasicUserResultType) => {
    console.log(response); //{ "rID": "bc54738d3056638532982df19f5a9441509099ea" }
  })
  .catch((error: ErrorResultType) => {
    console.log(error); //{ "code": "200", "message": "Specified argument failed the validation" }
  });
```

Add the getWritePasscode() Commnity Pass API to your application

```typescript
getWritePasscode({
  reliantGUID: '120a2c48-461e-4312-91c8-c3ba586ad29a', //string
  programGUID: '6d498a97-fb8c-42b3-89a4-0fe13372f557', //string
  rID: 'bc54738d3056638532982df19f5a9441509099ea', //string
  passcode: '123456', //string
})
  .then((response: WritePasscodeResultType) => {
    console.log(response); //{ "responseStatus": "Success" }
  })
  .catch((error: ErrorResultType) => {
    console.log(error); //{ "code": "705", "message": "Invalid passcode used" }
  });
```

Add the getWriteProfile() Commnity Pass API to your application

```typescript
getWriteProfile({
  reliantGUID: '120a2c48-461e-4312-91c8-c3ba586ad29a', //string
  programGUID: '6d498a97-fb8c-42b3-89a4-0fe13372f557', //string
  rID: 'bc54738d3056638532982df19f5a9441509099ea', //string
  overwriteCard: false, //boolean
})
  .then((response: WriteProfileResultType) => {
    console.log(response); //{ "consumerDeviceNumber": "9085674530845673" }
  })
  .catch((error: ErrorResultType) => {
    console.log(error); //{ "code": "721", "message": "Card was removed during read/write operation" }
  });
```

For updates regarding the Community Pass APIs that have been added to the library, refer to the [TODO file](/TODO.md)

You are now ready to start building your React Native Reliant Application using the Community Pass Kernel assets.

[Return to Getting Started](README.md)
