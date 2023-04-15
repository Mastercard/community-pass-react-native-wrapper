import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import {
  getRegisterBasicUser,
  ErrorResultType,
  RegisterBasicUserResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import { themeColors } from '../assets/colors';
import {
  buttonLabels,
  registerBasicUserScreenStrings,
  registrationTypes,
  screens,
} from '../assets/strings';

const { width: WIDTH } = Dimensions.get('screen');

const RegisterBasicUser = ({ navigation }: any) => {
  const [registrationError, setRegistrationError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleRegisterBasicUser = () => {
    setIsLoading(true);
    getRegisterBasicUser({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
    })
      .then((res: RegisterBasicUserResultType) => {
        console.log(res);
        setIsLoading(false);
        setRegistrationError('');
        navigation.navigate(screens.WRITE_PROFILE, {
          rID: res.rID,
          registrationType: registrationTypes.BASIC_USER,
        });
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setRegistrationError(e.message);
        setIsLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {registerBasicUserScreenStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {registerBasicUserScreenStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{registrationError}</Text>
        <CustomButton
          isLoading={isLoading}
          onPress={handleRegisterBasicUser}
          label={buttonLabels.REGISTER_USER}
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

export default RegisterBasicUser;
