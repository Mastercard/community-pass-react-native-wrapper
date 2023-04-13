export interface ProgramSpaceV2 {
  collectionInfo: CollectionInfo;
  user: User;
  version: number;
}

export interface CollectionInfo {
  collections: Collection[];
  lastAmountPaidOut: number;
  lastPayoutTimeStamp: number;
  noOfPaidCollections: number;
  totalAmountPaid: number;
}

export interface Collection {
  amount: number;
  amountPerGram: number;
  id: string;
  produceId: string;
  produceName: string;
  timeStamp: number;
  weightInGrams: number;
}

export interface User {
  address: string;
  age: number;
  firstName: string;
  id: string;
  lastName: string;
}
