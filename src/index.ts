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
  modalities,
  operationMode,
}: RegisterUserWithBiometricsParamType) {
  return CompassLibraryReactNativeWrapper.getRegisterUserWithBiometrics({
    reliantGUID,
    programGUID,
    consentID,
    modalities,
    operationMode,
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

export function getUserVerification({
  reliantGUID,
  programGUID,
  modalities,
}: UserVerificationParamType) {
  return CompassLibraryReactNativeWrapper.getUserVerification({
    reliantGUID,
    programGUID,
    modalities,
  });
}

export function getCreateSVA({
  reliantGUID,
  programGUID,
  rID,
  sva
}: CreateSVAParamType) {
  return CompassLibraryReactNativeWrapper.getCreateSVA({
    reliantGUID,
    programGUID,
    rID,
    sva
  });
}

export function getBlacklistFormFactor({
  programGUID,
  rID,
  reliantGUID,
  consumerDeviceNumber,
  formFactor
}: BlacklistFormFactorParamType) {
  return CompassLibraryReactNativeWrapper.getBlacklistFormFactor({
    programGUID,
    rID,
    reliantGUID,
    consumerDeviceNumber,
    formFactor
  });
}

export function getSVAOperation({
  reliantGUID,
  programGUID,
  rID,
  svaOperation
}: SVAOperationParamType) {
  return CompassLibraryReactNativeWrapper.getSVAOperation({
    reliantGUID,
    programGUID,
    rID,
    svaOperation
  });
}

export function getReadSVA({
  reliantGUID,
  programGUID,
  rID,
  svaUnit
}: ReadSVAParamType) {
  return CompassLibraryReactNativeWrapper.getReadSVA({
    reliantGUID,
    programGUID,
    rID,
    svaUnit
  });
}

export interface VerifyPasscodeParamType {
  passcode: string;
  programGUID: string;
  reliantGUID: string;
}

export interface RegistrationDataParamType {
  reliantGUID: string;
  programGUID: string;
}

export interface UserVerificationParamType {
  reliantGUID: string;
  programGUID: string;
  modalities: Modality[];
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
  modalities: Modality[];
  operationMode: OperationMode;
}

export interface WriteProgramSpaceParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  programSpaceData: string;
  encryptData: boolean;
}

export interface ReadSVAParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  svaUnit: string;
}

export interface ReadProgramSpaceParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  decryptData: boolean;
}

export interface UserVerificationResultType {
  isMatchFound: boolean;
  rID: string;
  biometricMatchList: Match[];
}

export interface VerifyPasscodeResultType {
  status: boolean;
  rID: string;
  retryCount: number;
}

export interface RegistrationDataResultType {
  isRegisteredInProgram: boolean;
  authMethods: AuthType[];
  modalityType: Modality[];
  rID: string;
}

export interface SaveBiometricConsentResultType {
  consentID: string;
  responseStatus: ResponseStatus;
}

export interface RegisterBasicUserResultType {
  rID: string;
}

export interface RegisterUserWithBiometricsResultType {
  programGUID: string;
  rID: string;
  bioToken: string;
  enrolmentStatus: EnrolmentStatus;
}

export interface WriteProfileResultType {
  consumerDeviceNumber: string;
}

export interface WritePasscodeResultType {
  responseStatus: ResponseStatus;
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

export enum OperationMode {
  BEST_AVAILABLE = 'BEST_AVAILABLE',
  FULL = 'FULL',
}

export enum Modality {
  FACE = 'FACE',
  LEFT_PALM = 'LEFT_PALM',
  RIGHT_PALM = 'RIGHT_PALM',
}

enum AuthType {
  NONE = 'NONE',
  CARD_PRESENT = 'CARD_PRESENT',
  PASSCODE = 'PASSCODE',
  BIO = 'BIO',
}

enum ResponseStatus {
  SUCCESS = 'SUCCESS',
  ERROR = 'ERROR',
  UNDEFINED = 'UNDEFINED',
}

enum EnrolmentStatus {
  NEW = 'NEW',
  EXISTING = 'EXISTING',
}

interface Match {
  distance: number;
  modality: string;
  normalizedScore: number;
}

export interface CreateSVAParamType {
  reliantGUID: string;
  programGUID: string;
  rID?: string;
  sva: sva;
}

export interface BlacklistFormFactorParamType {
  programGUID: string;
  rID: string;
  reliantGUID: string;
  consumerDeviceNumber: string;
  formFactor: FormFactor;
}

export interface BlacklistFormFactorResultType {
  type: string;
  consumerDeviceNumber: string;
}

export interface CreateSVAResultType {
  response: string;
}
  
export interface sva {
  value: FinancialSVA | EVoucherSVA;
}
  
export enum SVA {
  FinancialSVA = 'FinancialSVA',
  EVoucherSVA = 'EVoucherSVA',
}
  
export enum EVoucherType {
  COMMODITY = 'COMMODITY',
  POINT = 'POINT', 
}
  
export interface FinancialSVA {
  type: SVA.FinancialSVA;
  unit: string;
}
  
export interface EVoucherSVA {
  type: SVA.EVoucherSVA;
  unit: string;
  eVoucherType: EVoucherType;
}

export interface ReadSVAResultType {
  currentBalance: number;
  transactionCount: number;
  purseType: string;
  unit: string;
  lastTransaction: Transaction
}

export interface Transaction {
  amount: number;
  balance: number;
}

export enum SVAOperationType {
  DECREASE = 'DECREASE',
  INCREASE = 'INCREASE', 
  UPDATE = 'UPDATE'
}

export interface SVAOperation {
  svaUnit: string;
  svaAmount: number;
  svaOperationType: SVAOperationType;
}

export interface SVAOperationParamType {
  reliantGUID: string;
  programGUID: string;
  rID: string;
  svaOperation: SVAOperation;
}

export enum FormFactor {
  CARD = 'CARD',
  QR = 'QR', 
  NONE = 'NONE'
}