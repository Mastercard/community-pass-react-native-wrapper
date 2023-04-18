import React from 'react';
import { StyleSheet, Text, View, Dimensions } from 'react-native';

import CustomButton from './components/CustomButton';
import {
  buttonLabels,
  screens,
  writeSuccessfulScreenStrings,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const { width: WIDTH } = Dimensions.get('screen');

const WriteSuccessful = ({ route, navigation }: any) => {
  const { rID, consumerDeviceNumber } = route?.params || {};

  const goToHomeScreen = () => {
    navigation.navigate(screens.HOME);
  };

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <Text style={styles.title}>
          {writeSuccessfulScreenStrings.SCREEN_TITLE}
        </Text>
        <View style={styles.card}>
          <Text style={styles.label}>
            {writeSuccessfulScreenStrings.CONSUMER_DEVICE_NUMBER}
          </Text>
          <Text style={styles.value}>{consumerDeviceNumber}</Text>
        </View>
        <View style={styles.card}>
          <Text style={styles.label}>{writeSuccessfulScreenStrings.RID}</Text>
          <Text style={styles.value}>{rID}</Text>
        </View>
        <CustomButton
          isLoading={false}
          onPress={goToHomeScreen}
          label={buttonLabels.GO_HOME}
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
  innerContainer: {
    flex: 1,
    alignItems: 'flex-start',
    justifyContent: 'center',
  },
  button: {
    marginTop: 15,
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
  card: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  label: {
    fontWeight: '600',
    color: themeColors.black,
    fontSize: 16,
  },
  value: {
    color: themeColors.black,
    fontSize: 16,
  },
  title: {
    fontSize: 20,
    marginBottom: 20,
    color: themeColors.mastercardYellow,
  },
});

export default WriteSuccessful;
