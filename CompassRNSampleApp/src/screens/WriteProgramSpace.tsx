import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions, Alert } from 'react-native';
import {
  ErrorResultType,
  getWriteProgramSpace,
  WriteProgramSpaceResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  screens,
  writeProgramScreenStrings,
} from '../assets/strings';
import { themeColors } from '../assets/colors';
import data from '../../data/sharedSpaceData.json';
import type { SharedSpaceDataSchema } from '../types/programSpaceDataType';

const { width: WIDTH } = Dimensions.get('screen');
const sharedSpaceData: SharedSpaceDataSchema = data;

const WriteProgramSpace = ({ route, navigation }: any) => {
  const { rID: sharedRID } = route?.params || {};
  const [readCardError, setWriteCardError] = useState('');
  const [isWriteProgramSpaceLoading, setIsWriteProgramSpaceLoading] =
    useState(false);

  const handleProceed = () => {
    setWriteCardError('');
    setIsWriteProgramSpaceLoading(false);
    navigation.navigate(screens.HOME);
  };

  const handleWriteProgramSpace = () => {
    setIsWriteProgramSpaceLoading(true);
    getWriteProgramSpace({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      rID: sharedRID,
      programSpaceData: JSON.stringify(sharedSpaceData),
      encryptData: true,
    })
      .then((res: WriteProgramSpaceResultType) => {
        setIsWriteProgramSpaceLoading(false);
        setWriteCardError('');
        console.log(res);
        Alert.alert(
          writeProgramScreenStrings.ALERT_TITLE,
          writeProgramScreenStrings.ALERT_DESCRIPTION,
          [
            {
              text: writeProgramScreenStrings.ALERT_ACCEPT_BUTTON,
              onPress: () => handleProceed(),
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
        setWriteCardError(e.message);
        setIsWriteProgramSpaceLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {writeProgramScreenStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {writeProgramScreenStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{readCardError}</Text>
        <CustomButton
          isLoading={isWriteProgramSpaceLoading}
          onPress={handleWriteProgramSpace}
          label={buttonLabels.WRITE_CARD}
          customStyles={styles.writeProgramSpaceButton}
          labelStyles={styles.writeProgramSpaceButtonLabel}
          indicatorColor={themeColors.white}
        />
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
  buttonWrapper: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  writeProgramSpaceButton: {
    minWidth: '100%',
  },
  writeProgramSpaceButtonLabel: {
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

export default WriteProgramSpace;
