import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'community-pass-react-native-wrapper' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const CompassLibraryReactNativeWrapper =
  NativeModules.CompassLibraryReactNativeWrapper
    ? NativeModules.CompassLibraryReactNativeWrapper
    : new Proxy(
        {},
        {
          get() {
            throw new Error(LINKING_ERROR);
          },
        }
      );

export function saveBiometricConsent({
  reliantGUID,
  programGUID,
  consumerConsentValue,
}: SaveBiometricConsentParamType) {
  return CompassLibraryReactNativeWrapper.saveBiometricConsent({
    reliantGUID,
    programGUID,
    consumerConsentValue,
  });
}

export function getWritePasscode({
  reliantGUID,
  programGUID,
  rID,
  passcode,
}: WritePasscodeParamType) {
  return CompassLibraryReactNativeWrapper.getWritePasscode({
    reliantGUID,
    programGUID,
    rID,
    passcode,
  });
}

export function getWriteProfile({
  reliantGUID,
  programGUID,
  rID,
  overwriteCard,
}: WriteProfileParamType) {
  return CompassLibraryReactNativeWrapper.getWriteProfile({
    reliantGUID,
    programGUID,
    rID,
    overwriteCard,
  });
}

export function getRegisterBasicUser({
  reliantGUID,
  programGUID,
}: RegisterBasicUserParamType) {
  return CompassLibraryReactNativeWrapper.getRegisterBasicUser({
    reliantGUID,
    programGUID,
  });
}

export function getRegisterUserWithBiometrics({
  reliantGUID,
  programGUID,
  consentID,
}: RegisterUserWithBiometricsParamType) {
  return CompassLibraryReactNativeWrapper.getRegisterUserWithBiometrics({
    reliantGUID,
    programGUID,
    consentID,
  });
}

export function getReadProgramSpace({
  reliantGUID,
  programGUID,
  rID,
  decryptData,
}: ReadProgramSpaceParamType) {
  return CompassLibraryReactNativeWrapper.getReadProgramSpace({
    reliantGUID,
    programGUID,
    rID,
    decryptData,
  });
}

export function getWriteProgramSpace({
  reliantGUID,
  programGUID,
  rID,
  programSpaceData,
  encryptData,
}: WriteProgramSpaceParamType) {
  return CompassLibraryReactNativeWrapper.getWriteProgramSpace({
    reliantGUID,
    programGUID,
    rID,
    programSpaceData,
    encryptData,
  });
}

export function getRegistrationData({
  reliantGUID,
  programGUID,
}: RegistrationDataParamType) {
  return CompassLibraryReactNativeWrapper.getRegistrationData({
    reliantGUID,
    programGUID,
  });
    programGUID,
  });
}

export function getVerifyPasscode({
  passcode,
  programGUID,
  reliantGUID,
}: VerifyPasscodeParamType) {
  return CompassLibraryReactNativeWrapper.getVerifyPasscode({
    passcode,
    programGUID,
    reliantGUID,
  });
}

export interface VerifyPasscodeParamType {
  passcode: string;
  programGUID: string;
  reliantGUID: string;
}

export interface VerifyPasscodeResultType {
  status: boolean;
  rId: string;
  counter: number;
}

export interface RegistrationDataParamType {
  reliantGUID: string;
  programGUID: string;
}

export interface RegistrationDataResultType {
  isRegisteredInProgram: boolean;
  authMethods: string[];
  rID: string;
}

export interface SaveBiometricConsentParamType {
  reliantGUID: string;
  programGUID: string;
  consumerConsentValue: boolean;
}

export interface WritePasscodeParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  passcode: string;
}

export interface WriteProfileParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  overwriteCard: boolean;
}

export interface RegisterBasicUserParamType {
  reliantGUID: string;
  programGUID: string;
}

export interface RegisterUserWithBiometricsParamType {
  reliantGUID: string;
  programGUID: string;
  consentID: string;
}

export interface WriteProgramSpaceParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  programSpaceData: string;
  encryptData: boolean;
}

export interface ReadProgramSpaceParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  decryptData: boolean;
}

export interface SaveBiometricConsentResultType {
  consentID: string;
  responseStatus: string;
}

export interface RegisterBasicUserResultType {
  rID: string;
}

export interface RegisterUserWithBiometricsResultType {
  programGUID: string;
  rID: string;
  bioToken: string;
  enrolmentStatus: string;
}

export interface WriteProfileResultType {
  consumerDeviceNumber: string;
}

export interface WritePasscodeResultType {
  responseStatus: string;
}

export interface ErrorResultType {
  code: string;
  message: string;
}

export interface ReadProgramSpaceResultType {
  programSpaceData: string;
}

export interface WriteProgramSpaceResultType {
  isSuccess: boolean;
}

export interface SharedSpaceDataSchema {
  collectionInfo: CollectionInfo;
  user: User;
}

interface CollectionInfo {
  fifthCollection: Collection;
  firstCollection: Collection;
  fourthCollection: Collection;
  lastAmountPaidOut: number;
  lastPayoutTimeStamp: number;
  noOfPaidCollections: number;
  secondCollection: Collection;
  thirdCollection: Collection;
  totalAmountPaid: number;
}

interface Collection {
  amount: number;
  amountPerGram: number;
  id: string;
  produceId: string;
  produceName: string;
  timeStamp: number;
  weightInGrams: number;
}

interface User {
  address: string;
  age: number;
  consentId: string;
  consumerDeviceId: string;
  firstName: string;
  identifier: string;
  lastName: string;
  mobileNumber: string;
  rId: string;
}
