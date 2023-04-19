import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions, Alert } from 'react-native';
import {
  ErrorResultType,
  getVerifyPasscode,
  VerifyPasscodeResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomInput from './components/CustomInput';
import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  authenticatePasscodeUserStrings,
  writePasscodeScreenStrings,
  keyboardTypes,
  genericErrorMessages,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');

const REG = /^[\d]{6}$/;

const AuthenticatePasscodeUser = ({ navigation }: any) => {
  const [readCardError, setReadCardError] = useState('');
  const [passcode, setPasscode] = useState('');
  const [loading, setLoading] = useState(false);
  const [count, setCount] = useState('');

  const showAlert = (res: VerifyPasscodeResultType) => {
    if (res.retryCount > 0 && res.status === true) {
      return Alert.alert(
        res.status
          ? authenticatePasscodeUserStrings.MATCH_FOUND_ALERT_TITLE
          : authenticatePasscodeUserStrings.MATCH_NOT_FOUND_ALERT_TITLE,
  
        res.status
          ? authenticatePasscodeUserStrings.ALERT_MATCH_FOUND_DESCRIPTION
          : authenticatePasscodeUserStrings.ALERT_MATCH_NOT_FOUND_DESCRIPTION,
  
        [
          {
            text: authenticatePasscodeUserStrings.ALERT_ACCEPT_BUTTON,
            onPress: () => navigation.navigate(screens.HOME),
            style: "default",
          },
        ],
  
        {
          cancelable: true,
          onDismiss: () => null,
        }
      );
    } else {
      return;
    }
  };

  const handleVerifyPasscode = () => {
    if (passcode?.length === 0) {
      setReadCardError(genericErrorMessages.INVALID_PASSCODE);
      return;
    } else {
      if (!REG.test(passcode)) {
        setReadCardError(genericErrorMessages.INVALID_PASSCODE);
        return;
      }
    }

    setLoading(true);
    getVerifyPasscode({
      passcode: passcode,
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
    })
      .then((res: VerifyPasscodeResultType) => {
        console.log(JSON.stringify(res, null, 2));
        setLoading(false);
        setCount(res.retryCount?.toString());

        showAlert(res)
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
            {authenticatePasscodeUserStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {authenticatePasscodeUserStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{readCardError}</Text>
        {count.length > 1 ? (
          <Text style={styles.error}>{count} attempts remaining</Text>
        ) : null}
        <CustomInput
          config={{
            placeholderText: writePasscodeScreenStrings.INPUT_PLACEHOLDER,
            keyboadType: keyboardTypes.NUMERIC,
            hasError: false,
            secureTextEntry: true,
          }}
          value={passcode}
          onChange={setPasscode}
        />
        <CustomButton
          isLoading={loading}
          onPress={handleVerifyPasscode}
          label={buttonLabels.READ_CARD}
          customStyles={styles.verifyPasscodeSpaceButton}
          labelStyles={styles.verifyPasscodeSpaceButtonLabel}
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
    marginBottom: 10,
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
  verifyPasscodeSpaceButton: {
    minWidth: '100%',
  },
  verifyPasscodeSpaceButtonLabel: {
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

export default AuthenticatePasscodeUser;
