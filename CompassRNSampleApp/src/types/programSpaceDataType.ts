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
