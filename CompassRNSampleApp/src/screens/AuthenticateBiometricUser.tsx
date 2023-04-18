import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions, Alert } from 'react-native';
import {
  getUserVerification,
  ErrorResultType,
  UserVerificationResultType,
  Modality,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  authenticateBiometricUserStrings,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');

const AuthenticateBiometricUser = ({ navigation }: any) => {
  const [readCardError, setReadCardError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleVerifyBiometricUser = () => {
    setLoading(true);
    getUserVerification({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      modalities: [Modality.FACE, Modality.LEFT_PALM, Modality.RIGHT_PALM],
    })
      .then((res: UserVerificationResultType) => {
        console.log(JSON.stringify(res, null, 2));
        setLoading(false);
        Alert.alert(
          res.isMatchFound
            ? authenticateBiometricUserStrings.MATCH_FOUND_ALERT_TITLE
            : authenticateBiometricUserStrings.MATCH_NOT_FOUND_ALERT_TITLE,
          res.isMatchFound
            ? authenticateBiometricUserStrings.ALERT_MATCH_FOUND_DESCRIPTION
            : authenticateBiometricUserStrings.ALERT_MATCH_NOT_FOUND_DESCRIPTION,
          [
            {
              text: authenticateBiometricUserStrings.ALERT_ACCEPT_BUTTON,
              onPress: () => navigation.navigate(screens.HOME),
              style: 'default',
            },
          ],
          {
            cancelable: true,
            onDismiss: () => null,
          }
        );
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
            {authenticateBiometricUserStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {authenticateBiometricUserStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{readCardError}</Text>
        <CustomButton
          isLoading={loading}
          onPress={handleVerifyBiometricUser}
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

export default AuthenticateBiometricUser;
