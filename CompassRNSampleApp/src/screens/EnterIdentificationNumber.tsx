import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';

import CustomInput from './components/CustomInput';
import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  enterIdentificationNumberScreenStrings,
  genericErrorMessages,
  keyboardTypes,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');
const RE = /^[\d]+$/;

const EnterIdentificationNumber = ({ navigation }: any) => {
  const [mobileValidationErrror, setMobileValidationError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [idNumber, setIdNumber] = useState('');

  const validateMobileNumber = () => {
    setIsLoading(true);

    if (RE.test(idNumber)) {
      setIsLoading(false);
      setMobileValidationError('');
      navigation.navigate(screens.USER_DATA_FORM, {
        IdentificationNumber: idNumber,
      });
    } else {
      setMobileValidationError(genericErrorMessages.INVALID_ID_ERROR_MESSAGE);
      setIsLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {enterIdentificationNumberScreenStrings.SCREEN_TITLE}
          </Text>
          <CustomInput
            config={{
              placeholderText:
                enterIdentificationNumberScreenStrings.INPUT_PLACEHOLDER,
              keyboadType: keyboardTypes.NUMERIC,
              hasError: false,
            }}
            value={idNumber}
            onChange={setIdNumber}
          />
        </View>
        <Text style={styles.error}>{mobileValidationErrror}</Text>
        <CustomButton
          isLoading={isLoading}
          onPress={validateMobileNumber}
          label={buttonLabels.VALIDATE_IDENTIFICATION_NUMBER}
          customStyles={styles.validateIdButton}
          labelStyles={styles.validateIdButtonLabel}
          indicatorColor={themeColors.mastercardYellow}
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
    textAlign: 'left',
    width: '100%',
  },
  innerContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  consentbuttonsWrapper: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  validateIdButton: {
    minWidth: '100%',
  },
  validateIdButtonLabel: {
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

export default EnterIdentificationNumber;
