import React, { useState } from 'react';
import { View, Text, StyleSheet, Dimensions } from 'react-native';
import {
  ErrorResultType,
  getReadProgramSpace,
  ReadProgramSpaceResultType,
} from 'community-pass-react-native-wrapper';
import { PROGRAM_GUID, RELIANT_APP_GUID } from '@env';

import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  readProgramScreenStrings,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');

const ReadProgramSpace = ({ route, navigation }: any) => {
  const { rID } = route?.params || {};
  const [readCardError, setReadCardError] = useState('');
  const [isReadProgramSpaceLoading, setIsReadProgramSpaceLoading] =
    useState(false);

  const handleReadProgramSpace = () => {
    setIsReadProgramSpaceLoading(true);
    getReadProgramSpace({
      reliantGUID: RELIANT_APP_GUID,
      programGUID: PROGRAM_GUID,
      rID: rID,
      decryptData: true,
    })
      .then((res: ReadProgramSpaceResultType) => {
        setIsReadProgramSpaceLoading(false);
        setReadCardError('');
        console.log(res.programSpaceData);
        navigation.navigate(screens.PROGRAM_SPACE_DATA_DETAILS, {
          programSpaceData: res.programSpaceData,
        });
      })
      .catch((e: ErrorResultType) => {
        console.log(JSON.stringify(e, null, 2));
        setReadCardError(`${e.code}: ${e.message}`);
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
          onPress={handleReadProgramSpace}
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
