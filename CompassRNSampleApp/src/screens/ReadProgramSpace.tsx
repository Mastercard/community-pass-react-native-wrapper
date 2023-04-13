import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import {
  ErrorResultType,
  getReadProgramSpace,
  getRegistrationData,
  GetRegistrationDataResultType,
  ReadProgramSpaceResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import { buttonLabels, readProgramScreenStrings } from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');

const ReadProgramSpace = () => {
  const [readCardError, setReadCardError] = useState('');
  const [rID, setRID] = useState('');
  const [isReadProgramSpaceLoading, setIsReadProgramSpaceLoading] =
    useState(false);

  const handleReadRegistrationData = () => {
    setIsReadProgramSpaceLoading(true);
    getRegistrationData({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
    })
      .then((res: GetRegistrationDataResultType) => {
        console.log(JSON.stringify(res, null, 2));
        setRID(res.rID);
        handleReadProgramSpace();
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setReadCardError(e.message);
        setIsReadProgramSpaceLoading(false);
      });
  };

  const handleReadProgramSpace = () => {
    setIsReadProgramSpaceLoading(true);
    getReadProgramSpace({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      rID: rID,
      decryptData: false,
    })
      .then((res: ReadProgramSpaceResultType) => {
        setIsReadProgramSpaceLoading(false);
        setReadCardError('');
        console.log(res);
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setReadCardError(e.message);
        setIsReadProgramSpaceLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <View style={styles.infoWrapper}>
          <Text style={styles.title}>
            {readProgramScreenStrings.SCREEN_TITLE}
          </Text>
          <Text style={styles.description}>
            {readProgramScreenStrings.SCREEN_DESCRIPTION}
          </Text>
        </View>
        <Text style={styles.error}>{readCardError}</Text>
        <CustomButton
          isLoading={isReadProgramSpaceLoading}
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

export default ReadProgramSpace;
