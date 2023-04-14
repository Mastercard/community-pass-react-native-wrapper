export const buttonLabels = {
  REGISTER_USER: 'Register User',
  ADD_USER_DATA: 'Add User Data',
  DENY_CONSENT: 'Declined Consent',
  GRANT_CONSENT: 'Granted Consent',
  VALIDATE_IDENTIFICATION_NUMBER: 'Validate Identification Number',
  WRITE_PROFILE: 'Write profile on Card',
  WRITE_PASSCODE: 'Write Passcode on Card',
  GO_HOME: 'Go Home',
  READ_CARD: 'Read Card',
  WRITE_CARD: 'Write Card',
};

export const screens = {
  HOME: 'Home',
  PRE_TRANSACTIONS: 'PreTransactions',
  TRANSACTIONS: 'Transactions',
  ADMIN_TRANSACTIONS: 'AdminTransactions',
  SAVE_BIOMETRIC_CONSENT: 'SaveBiometricConsent',
  REGISTER_USER_WITH_BIOMETRICS: 'RegisterUserWithBiometrics',
  REGISTER_BASIC_USER: 'RegisterBasicUser',
  WRITE_PASSCODE: 'WritePasscode',
  WRITE_PROFILE: 'WriteProfile',
  USER_DATA_FORM: 'UserDataForm',
  ENTER_IDENTIFICATION_NUMBER: 'EnterIdentificationNumber',
  WRITE_SUCCESSFUL: 'WriteSuccessful',
  PROGRAM_SPACE: 'ProgramSpace',
  READ_PROGRAM_SPACE: 'ReadProgramSpace',
  WRITE_PROGRAM_SPACE: 'WriteProgramSpace',
  PROGRAM_SPACE_DATA_DETAILS: 'ProgaramSpaceDataDetails',
  TRANSCATIONS: 'Transactions',
  PROVIDE_A_SERVICE: 'ProvideService',
  RETRIEVE_REGISTRATION_DATA: 'RetrieveRegistrationData',
  AUTHENICATE_PASSCODE_USER: 'AuthenticatePasscodeUser',
  AUTHENTICATE_BIOMETRIC_USER: 'AuthenticateBiometricUser',
};

export const screenTitles = {
  HOME: 'Community Pass React Native Sample App',
  PRE_TRANSACTIONS: 'Pre Transactions',
  TRANSACTIONS: 'Transactions',
  ADMIN_TRANSACTIONS: 'Admin Transactions',
  SAVE_BIOMETRIC_CONSENT: 'Save Biometric Consent',
  REGISTER_USER_WITH_BIOMETRICS: 'Register User With Biometrics',
  REGISTER_BASIC_USER: 'Register Basic User',
  WRITE_PASSCODE: 'Write Passcode',
  WRITE_PROFILE: 'Write Profile',
  USER_DATA_FORM: 'User Data Form',
  ENTER_IDENTIFICATION_NUMBER: 'Enter Identification Number',
  WRITE_SUCCESSFUL: 'Registration Successful',
  PROGRAM_SPACE: 'Program Space',
  READ_PROGRAM_SPACE: 'Read Program Space',
  WRITE_PROGRAM_SPACE: 'Write Program Space',
  PROGRAM_SPACE_DATA_DETAILS: 'Program Space Data',
  TRANSCATIONS: 'Transactions',
  PROVIDE_A_SERVICE: 'Provide a Service',
  RETRIEVE_REGISTRATION_DATA: 'Retrieve Registration Data',
  AUTHENICATE_PASSCODE_USER: 'Verify Passcode User',
  AUTHENTICATE_BIOMETRIC_USER: 'Verify Biometric User',
};

export const homeScreenStrings = {
  SECTION: 'Section',
  PRE_TRANSACTIONS: 'Pre-Transactions',
  PRE_TRANSACTTIONS_DESCRIPTION:
    'You will complete user setup and on-boarding in this phase',
  TRANSACTIONS: 'Transactions',
  TRANSACTTIONS_DESCRIPTION:
    'You will conduct a life transaction during this phase',
  ADMIN_TRANSACTIONS: 'Admin-Transactions',
  ADMIN_TRANSACTTIONS_DESCRIPTION:
    'You can make changes to a user profile in this phase',
};

export const enterIdentificationNumberScreenStrings = {
  SCREEN_TITLE: 'Part 2: Enter Identification Number',
  INPUT_PLACEHOLDER: 'Identification Number',
};

export const keyboardTypes = {
  NUMERIC: 'numeric',
  PHONE_PAD: 'phone-pad',
  EMAIL_ADDRESS: 'email-address',
  DEFAULT: 'default',
};

export const preTransactionsScreenStrings = {
  ACTION: 'Action',
  ENROLL_A_NEW_USER_TITLE: 'Enrol a New User',
  ENROLL_A_NEW_USER_DESCRIPTION:
    'enroll a user using either biometrics or passcode',
  USE_SHARED_SPACE_TITLE: 'Use the Shared Space',
  USE_SHARED_SPACE_DESCRIPTION: 'Sync data between the POI and the Card',
  TOAST_MESSAGE: 'have not yet been implemented.',
};
export const transactionsScreenStrings = {
  ACTION: 'Action',
  AUTHENTICATE_USER_ON_PROGRAM:
    'Authenticate and Check User Registration on Program',
  AUTHENTICATE_USER_ON_PROGRAM_DESCRIPTION:
    'Authenticate and check if user is registered on a program using biometrics or passcode',
  PROVIDE_A_SERVICE_TITLE: 'Provide a Service',
  PROVIDE_A_SERVICE_DESCRIPTION: 'Provide a service and update the Card',
  TOAST_MESSAGE: 'have not yet been implemented.',
};

export const programSpaceScreenStrings = {
  ACTION: 'Action',
  READ_PROGRAM_SPACE_TITLE: 'Read Program Space',
  READ_PROGRAM_SPACE_DESCRIPTION:
    'Read card and fetch data stored in the program space',
  WRITE_PROGRAM_SPACE_TITLE: 'Write Program Space',
  WRITE_PROGRAM_SPACE_DESCRIPTION: 'Sync data between the POI and the Card',
};

export const registerBasicUserScreenStrings = {
  SCREEN_TITLE: 'Part 2: Register Basic User',
  SCREEN_DESCRIPTION:
    'This step calls the getRegisterBasicUser API method and return a rId.',
};

export const registerUserWithBiometricsScreenStrings = {
  SCREEN_TITLE: 'Part 2: Register Biometric User',
  SCREEN_DESCRIPTION:
    'This step calls the getRegisterUserWithBiometrics API method. The kernel will perform capture, validate biometric hashes created via a one to many search, create the Compass and return a R-ID as part of the process.',
  ALERT_TITLE: 'User Found In Records!',
  ALERT_DESCRIPTION:
    'A match of the biometrics hashes of this user has been found in our records.',
  ALERT_CANCEL_BUTTON: 'Cancel Registration',
  ALERT_ACCEPT_BUTTON: 'Continue with Registration',
};

export const saveBiometricConsentScreenStrings = {
  SCREEN_TITLE: 'Part 1: Capture Consent',
  SCREEN_DESCRIPTION:
    'This step calls the saveBiometricConsent API method. In this step, we check that a user has consented to capturing and storing their biometrics. If a user declines. You will register using passcode.',
};

export const userDataScreenStrings = {
  FIRST_NAME_PLACEHOLDER: 'First Name',
  LAST_NAME_PLACEHOLDER: 'Last Name',
  ADDRESS_PLACEHOLDER: 'Address',
  EMAIL_ADDRESS_PLACEHOLDER: 'Email Address',
  PHONE_NUMBER_PLACEHOLDER: 'Phone Number',
  AGE_PLACEHOLDERL: 'Age',
};

export const writePasscodeScreenStrings = {
  SCREEN_TITLE: 'Part 4: Write Passcode',
  SCREEN_DESCRIPTION:
    'This step calls the getWritePasscode API used to write a Passcode to the card. This is initiated by the Reliant Application to CPK after a successful user registration.',
  INPUT_PLACEHOLDER: 'Passcode',
};

export const writeProfileScreenStrings = {
  SCREEN_TITLE: 'Part 3: Write Card',
  SCREEN_DESCRIPTION:
    'This step calls the getRegisterUserWithBiometrics API method. The kernel will perform capture, validate biometric hashes created via a one to many search, create the Compass and return a R-ID as part of the process.',
  OVERWRITE_CARD: 'Overwrite Card',
};

export const writeSuccessfulScreenStrings = {
  SCREEN_TITLE: 'User registration was successful!',
  CONSUMER_DEVICE_NUMBER: 'Consumer Device Number: ',
  RID: 'rID: ',
};

export const readProgramScreenStrings = {
  SCREEN_TITLE: 'Part 2: Read Program Space',
  SCREEN_DESCRIPTION: 'This step calls the getReadProgramSpace API method',
};

export const retrieveRegistrationDataScreenStrings = {
  SCREEN_TITLE: 'Part 1: Registration Data Check',
  SCREEN_DESCRIPTION:
    'This step calls the getRegistrationData API method which reads the card and checks if the sample user is registered in the program and if they have Passcode or Biometric Authentication',
};

export const writeProgramScreenStrings = {
  SCREEN_TITLE: 'Part 2: Write Program Space',
  SCREEN_DESCRIPTION: 'This step calls the getWriteProgramSpace API method',
  ALERT_TITLE: 'Write Successful',
  ALERT_DESCRIPTION: 'Data has been successfully written to program space.',
  ALERT_ACCEPT_BUTTON: 'OK',
};

export const authenticateBiometricUserStrings = {
  SCREEN_TITLE: 'Part 2: Verify Biometric User',
  SCREEN_DESCRIPTION: 'This step calls the getUserVerification API method',
  MATCH_FOUND_ALERT_TITLE: 'Verification Successful',
  MATCH_NOT_FOUND_ALERT_TITLE: 'Verification Not Successful',
  ALERT_MATCH_NOT_FOUND_DESCRIPTION: 'User has not been found on the program',
  ALERT_MATCH_FOUND_DESCRIPTION:
    'User has been verified successfully on the program',
  ALERT_ACCEPT_BUTTON: 'OK',
};

export const authenticatePasscodeUserStrings = {
  SCREEN_TITLE: 'Part 2: Verify Basic User',
  SCREEN_DESCRIPTION: 'This step calls the getVerifyPasscode API method',
  MATCH_FOUND_ALERT_TITLE: 'Verification Successful',
  MATCH_NOT_FOUND_ALERT_TITLE: 'Verification Not Successful',
  ALERT_MATCH_NOT_FOUND_DESCRIPTION: 'User has not been found on the program',
  ALERT_MATCH_FOUND_DESCRIPTION:
    'User has been verified successfully on the program',
  ALERT_ACCEPT_BUTTON: 'OK',
  ALERT_TRY_AGRAIN: 'Try Again',
};

export const genericErrorMessages = {
  TOAST_MESSAGE_PLURAL: 'have not yet been implemented.',
  TOAST_MESSAGE_SINGULAR: 'has not yet been implemented.',
  INVALID_ID_ERROR_MESSAGE: 'Please input a valid identification number',
  INVALID_PASSCODE: 'Please input a 6 digit passcode',
};

export const registrationTypes = {
  BASIC_USER: 'BASIC_USER',
  BIOMETRIC_USER: 'BIOMETRIC_USER',
};
