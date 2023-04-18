import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import {
  ErrorResultType,
  getRegistrationData,
  RegistrationDataResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  retrieveRegistrationDataScreenStrings,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';
import { NAVIGATION_OPTIONS } from '../../utils/enums';

const { width: WIDTH } = Dimensions.get('screen');

const RetrieveRegistrationData = ({ route, navigation }: any) => {
  const { navOptions } = route?.params || {};
  const [readCardError, setReadCardError] = useState('');
  const [rID, setRID] = useState('');

  const [loading, setLoading] = useState(false);

  const handleNextNavigation = (authMethods: Array<string>) => {
    switch (navOptions) {
      case NAVIGATION_OPTIONS.WRITE_PROGRAM_SPACE:
        navigation.navigate(screens.WRITE_PROGRAM_SPACE, {
          rID: rID,
          navOptions: NAVIGATION_OPTIONS.WRITE_PROGRAM_SPACE,
        });
        return;
      case NAVIGATION_OPTIONS.READ_PROGRAM_SPACE:
        navigation.navigate(screens.READ_PROGRAM_SPACE, {
          rID: rID,
          navOptions: NAVIGATION_OPTIONS.READ_PROGRAM_SPACE,
        });
        return;
      case NAVIGATION_OPTIONS.AUTHENTICATE_USER:
        if (authMethods.includes('BIO')) {
          navigation.navigate(screens.AUTHENTICATE_BIOMETRIC_USER, {
            rID: rID,
            navOptions: NAVIGATION_OPTIONS.AUTHENTICATE_USER,
          });
        } else {
          navigation.navigate(screens.AUTHENICATE_PASSCODE_USER, {
            rID: rID,
            navOptions: NAVIGATION_OPTIONS.AUTHENTICATE_USER,
          });
        }
        return;
      default:
        return;
    }
  };

  const handleReadRegistrationData = () => {
    setLoading(true);
    getRegistrationData({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
    })
      .then((res: RegistrationDataResultType) => {
        console.log(JSON.stringify(res, null, 2));
        setRID(res.rID);
        setLoading(false);
        setReadCardError('');
        handleNextNavigation(res.authMethods);
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setReadCardError(e.message);
        setLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {retrieveRegistrationDataScreenStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {retrieveRegistrationDataScreenStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{readCardError}</Text>
        <CustomButton
          isLoading={loading}
          onPress={handleReadRegistrationData}
          label={buttonLabels.READ_CARD}
          customStyles={styles.readProgramSpaceButton}
          labelStyles={styles.readProgramSpaceButtonLabel}
          indicatorColor={themeColors.white}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: WIDTH,
    padding: 20,
    backgroundColor: themeColors.white,
  },
  error: {
    color: themeColors.mastercardRed,
    marginVertical: 10,
    textAlign: 'left',
    width: '100%',
  },
  innerContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonWrapper: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  readProgramSpaceButton: {
    minWidth: '100%',
  },
  readProgramSpaceButtonLabel: {
    color: themeColors.white,
    textAlign: 'center',
  },
  title: {
    fontSize: 20,
    color: themeColors.black,
    marginBottom: 20,
  },
  description: {
    fontSize: 14,
    color: themeColors.black,
  },
  infoWrapper: {
    width: '100%',
  },
});

export default RetrieveRegistrationData;
