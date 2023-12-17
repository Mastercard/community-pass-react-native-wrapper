import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ToastAndroid,
  ScrollView,
} from 'react-native';
import {
  genericErrorMessages,
  homeScreenStrings,
  screenTitles,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const Home = ({ navigation }: any) => {
  // // eslint-disable-next-line @typescript-eslint/no-unused-vars
  // const [batchOperationsLoading, setBatchOperationsLoading] = useState(false);
  // // eslint-disable-next-line @typescript-eslint/no-unused-vars
  // const [error, setError] = useState('');

  // // eslint-disable-next-line @typescript-eslint/no-unused-vars
  // const handleBatchCardOperations = () => {
  //   setBatchOperationsLoading(true);
  //   getBatchOperationsV1({
  //     reliantGUID: RELIANT_APP_GUID,
  //     programGUID: PROGRAM_GUID,
  //     shouldContinueOnError: true,
  //     listOfOperations: {
  //       verifyPasscode: {
  //         programGUID: PROGRAM_GUID,
  //         passcode: '123456',
  //         name: 'verifyPasscode',
  //         shouldContinueOnVerificationFail: false,
  //       },
  //       consumerDeviceNumber: {
  //         programGUID: PROGRAM_GUID,
  //         name: 'consumerDeviceNumber',
  //       },
  //       readProgramSpace: {
  //         programGUID: PROGRAM_GUID,
  //         name: 'readProgramSpace',
  //         decryptData: true,
  //       },
  //     },
  //   })
  //     .then((res: BatchOperationsV1ResultType) => {
  //       res.executedList?.map((operation) => {
  //         if (operation?.hasOwnProperty('readProgramSpace')) {
  //           console.log(
  //             JSON.parse(
  //               operation.readProgramSpace?.programSpaceData ?? 'undefined'
  //             ) as SharedSpaceDataSchema
  //           );
  //         } else if (operation?.hasOwnProperty('consumerDeviceNumber')) {
  //           console.log(operation?.consumerDeviceNumber);
  //         } else if (operation?.hasOwnProperty('registrationData')) {
  //           console.log(operation?.registrationData);
  //         } else if (operation?.hasOwnProperty('verifyPasscode')) {
  //           console.log(operation?.verifyPasscode);
  //         }
  //       });

  //       res.skippedList?.map((operation) => {
  //         if (operation?.hasOwnProperty('readProgramSpace')) {
  //         } else if (operation?.hasOwnProperty('consumerDeviceNumber')) {
  //         } else if (operation?.hasOwnProperty('registrationData')) {
  //         }
  //       });
  //       res.abortedList?.map((operation) => {
  //         if (operation?.hasOwnProperty('readProgramSpace')) {
  //           // console.log('Aborted List: ', res.abortedList);
  //         } else if (operation?.hasOwnProperty('consumerDeviceNumber')) {
  //           // console.log('Aborted List: ', res.abortedList);
  //         } else if (operation?.hasOwnProperty('registrationData')) {
  //           // console.log('Aborted List: ', res.abortedList);
  //         }
  //       });

  //       setBatchOperationsLoading(false);
  //       setError('');
  //     })
  //     .catch((e: ErrorResultType) => {
  //       console.log(JSON.stringify(e, null, 2));
  //       setError(e.message);
  //       setBatchOperationsLoading(false);
  //     });
  // };

  const showToast = (message: string) => {
    ToastAndroid.show(
      `${message} ${genericErrorMessages.TOAST_MESSAGE_PLURAL}`,
      ToastAndroid.SHORT
    );
  };

  return (
    <ScrollView style={styles.container}>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.PRE_TRANSACTIONS)}
      >
        <Text style={styles.sectionLabel}>{homeScreenStrings.SECTION}</Text>
        <Text style={styles.cardTitle}>
          {homeScreenStrings.PRE_TRANSACTIONS}
        </Text>
        <Text style={styles.cardDescription}>
          {homeScreenStrings.PRE_TRANSACTTIONS_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.TRANSACTIONS)}
      >
        <Text style={styles.sectionLabel}>{homeScreenStrings.SECTION}</Text>
        <Text style={styles.cardTitle}>{homeScreenStrings.TRANSACTIONS}</Text>
        <Text style={styles.cardDescription}>
          {homeScreenStrings.TRANSACTTIONS_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => showToast(screenTitles.ADMIN_TRANSACTIONS)}
        // onPress={() => handleBatchCardOperations()}
      >
        <Text style={styles.sectionLabel}>{homeScreenStrings.SECTION}</Text>
        <Text style={styles.cardTitle}>
          {homeScreenStrings.ADMIN_TRANSACTIONS}
        </Text>
        <Text style={styles.cardDescription}>
          {homeScreenStrings.ADMIN_TRANSACTTIONS_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <View style={styles.bottomPadding} />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  card: {
    backgroundColor: themeColors.white,
    borderRadius: 5,
    padding: 30,
    marginBottom: 30,
  },
  cardTitle: {
    color: themeColors.black,
    marginBottom: 10,
    fontWeight: '600',
    fontSize: 16,
  },
  sectionLabel: {
    color: themeColors.black,
    marginBottom: 10,
    fontWeight: '400',
    fontSize: 14,
  },
  cardDescription: {
    color: themeColors.darkGray,
    marginBottom: 10,
    fontSize: 14,
  },
  bottomPadding: {
    height: 15,
  },
});

export default Home;
