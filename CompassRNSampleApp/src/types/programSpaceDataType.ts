export interface ProgramSpaceRequest {
  collectionInfo: CollectionInfo;
  user: User;
  version: number;
}

export interface ProgramSpaceResponse {
  collectionInfo: ResponseCollectionInfo;
  user: ResponseUser;
}

interface CollectionInfo {
  collections: Collection[];
  lastAmountPaidOut: number;
  lastPayoutTimeStamp: number;
  noOfPaidCollections: number;
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
  firstName: string;
  id: string;
  lastName: string;
}

interface ResponseCollectionInfo {
  fifthCollection: ResponseCollection;
  firstCollection: ResponseCollection;
  fourthCollection: ResponseCollection;
  lastAmountPaidOut: number;
  lastPayoutTimeStamp: number;
  noOfPaidCollections: number;
  secondCollection: ResponseCollection;
  thirdCollection: ResponseCollection;
  totalAmountPaid: number;
}

interface ResponseCollection {
  amount: number;
  amountPerGram: number;
  id: string;
  produceId: string;
  produceName: string;
  timeStamp: number;
  weightInGrams: number;
}

interface ResponseUser {
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
