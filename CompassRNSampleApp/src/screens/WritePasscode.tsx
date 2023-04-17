import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import {
  getWritePasscode,
  ErrorResultType,
  WritePasscodeResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import CustomInput from './components/CustomInput';
import { themeColors } from '../assets/colors';
import {
  buttonLabels,
  genericErrorMessages,
  keyboardTypes,
  screens,
  writePasscodeScreenStrings,
} from '../assets/strings';

const { width: WIDTH } = Dimensions.get('screen');

var REG = /^[0-9]{6}$/;

const WritePasscode = ({ route, navigation }: any) => {
  const { rID, consumerDeviceNumber } = route?.params || {};
  const [writePasscodeError, setWritePasscodeError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [passcode, setPasscode] = useState('');

  const handleWritePasscode = () => {
    if (passcode?.length === 0) {
      setWritePasscodeError(genericErrorMessages.INVALID_PASSCODE);
      return;
    } else {
      if (!REG.test(passcode)) {
        setWritePasscodeError(genericErrorMessages.INVALID_PASSCODE);
        return;
      }
    }

    setIsLoading(true);
    getWritePasscode({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      rID: rID,
      passcode: passcode.toString(),
    })
      .then((res: WritePasscodeResultType) => {
        console.log(res);
        if (res.responseStatus === 'Success') {
          setIsLoading(false);
          setWritePasscodeError('');
          navigation.navigate(screens.WRITE_SUCCESSFUL, {
            consumerDeviceNumber: consumerDeviceNumber,
            rID: rID,
          });
        }
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setWritePasscodeError(e?.message);
        setIsLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {writePasscodeScreenStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {writePasscodeScreenStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{writePasscodeError}</Text>
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
          isLoading={isLoading}
          onPress={handleWritePasscode}
          label={buttonLabels.WRITE_PASSCODE}
          customStyles={styles.button}
          labelStyles={styles.buttonLabel}
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
  button: {
    paddingHorizontal: 15,
    paddingVertical: 10,
    borderRadius: 4,
    width: '100%',
    borderWidth: 2,
  },
  buttonLabel: {
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
});

export default WritePasscode;
