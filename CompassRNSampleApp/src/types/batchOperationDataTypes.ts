export interface BatchOperationsSchema {
  registrationData?: BasicRegistrationDifferentProgram;
  basicRegistrationDifferentProgram?: BasicRegistrationDifferentProgram;
  consumerDeviceNumber?: BasicRegistrationDifferentProgram;
  updateCardProfile?: UpdateCardProfile;
  writePasscode?: UpdateCardProfile;
  verifyPasscode?: VerifyPasscode;
  writeProgramSpace?: WriteProgramSpace;
  svaList?: BasicRegistrationDifferentProgram;
  svaOperation?: PokedexSvaOperation;
  readSVA?: ReadSVA;
  createFinancialSva?: CreateFinancialSva;
  createEVoucherSva?: CreateEVoucherSva;
  writeApplicationDataRecord?: WriteApplicationDataRecord;
  readApplicationDataBlob?: BasicRegistrationDifferentProgram;
  writeApplicationDataBlob?: WriteApplicationDataBlob;
  readAppDataChunk?: ReadAppDataChunk;
  readProgramSpace?: BasicRegistrationDifferentProgram;
}

export interface BasicRegistrationDifferentProgram {
  programGUID: string;
  name: string;
}

export interface CreateEVoucherSva {
  sva: CreateEVoucherSvaSva;
  programGUID: string;
  name: string;
}

export interface CreateEVoucherSvaSva {
  EVoucherSva: EVoucherSva;
}

export interface EVoucherSva {
  unit: string;
  eVoucherType: string;
}

export interface CreateFinancialSva {
  sva: CreateFinancialSvaSva;
  programGUID: string;
  name: string;
}

export interface CreateFinancialSvaSva {
  FinancialSVA: FinancialSVA;
}

export interface FinancialSVA {
  unit: string;
}

export interface ReadAppDataChunk {
  indexes: number[];
  programGUID: string;
  name: string;
}

export interface ReadSVA {
  svaUnit: string;
  programGUID: string;
  name: string;
}

export interface PokedexSvaOperation {
  svaOperation: SvaOperationSvaOperation;
  programGUID: string;
  name: string;
}

export interface SvaOperationSvaOperation {
  unit: string;
  amount: number;
  operationType: string;
}

export interface UpdateCardProfile {
  rid: string;
  programGUID: string;
  name: string;
  passcode?: string;
}

export interface VerifyPasscode {
  passcode: string;
  programGUID: string;
  name: string;
  shouldContinueOnVerificationFail: boolean;
}

export interface WriteApplicationDataBlob {
  dataBlob: string;
  programGUID: string;
  name: string;
}

export interface WriteApplicationDataRecord {
  appDataRecord: AppDataRecord[];
  programGUID: string;
  name: string;
}

export interface AppDataRecord {
  index: number;
  chunk: string;
  isShared: boolean;
}

export interface WriteProgramSpace {
  rId: string;
  data: string;
  programGUID: string;
  name: string;
}
