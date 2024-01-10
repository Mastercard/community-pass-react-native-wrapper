import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import {
  saveBiometricConsent,
  SaveBiometricConsentResultType,
  ErrorResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  saveBiometricConsentScreenStrings,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');

const SaveBiometricConsent = ({ navigation }: any) => {
  const [registrationError, setRegistrationError] = useState('');
  const [isGrantConsentLoading, setIsGrantConsentLoading] = useState(false);
  const [isDenyConsentLoading, setIsDenyConsentLoading] = useState(false);

  const handleGrantBiometricConsent = () => {
    setIsGrantConsentLoading(true);
    saveBiometricConsent({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      consumerConsentValue: true,
    })
      .then((res: SaveBiometricConsentResultType) => {
        console.log(res);
        setIsGrantConsentLoading(false);
        setRegistrationError('');
        return res.consentID;
      })
      .then((id: string) => {
        navigation.navigate(screens.REGISTER_USER_WITH_BIOMETRICS, {
          consentID: id,
        });
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setRegistrationError(e.message);
        setIsGrantConsentLoading(false);
      });
  };

  const handleDeclineBiometricConsent = () => {
    setIsDenyConsentLoading(true);
    saveBiometricConsent({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      consumerConsentValue: false,
    })
      .then((res: SaveBiometricConsentResultType) => {
        setIsDenyConsentLoading(false);
        return res.consentID;
      })
      .then((id: string) => {
        navigation.navigate(screens.REGISTER_BASIC_USER, {
          consentID: id,
        });
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setRegistrationError(e.message);
        setIsDenyConsentLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {saveBiometricConsentScreenStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {saveBiometricConsentScreenStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{registrationError}</Text>
        <View style={styles.consentbuttonsWrapper}>
          <CustomButton
            isLoading={isDenyConsentLoading}
            onPress={handleDeclineBiometricConsent}
            label={buttonLabels.DENY_CONSENT}
            customStyles={styles.declineConsentButton}
            labelStyles={styles.declineConsentButtonLabel}
            indicatorColor={themeColors.mastercardYellow}
          />
          <CustomButton
            isLoading={isGrantConsentLoading}
            onPress={handleGrantBiometricConsent}
            label={buttonLabels.GRANT_CONSENT}
            customStyles={styles.grantConsentButton}
            labelStyles={styles.grantConsentButtonLabel}
            indicatorColor={themeColors.white}
          />
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    maxWidth: WIDTH,
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
  consentbuttonsWrapper: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  grantConsentButton: {
    minWidth: '48%',
  },
  declineConsentButton: {
    backgroundColor: themeColors.white,
    minWidth: '48%',
    borderWidth: 2,
    borderColor: themeColors.mastercardYellow,
  },
  grantConsentButtonLabel: {
    color: themeColors.white,
    textAlign: 'center',
  },
  declineConsentButtonLabel: {
    color: themeColors.mastercardYellow,
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

export default SaveBiometricConsent;
