import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  ToastAndroid,
} from 'react-native';
import {
  transactionsScreenStrings,
  screens,
  genericErrorMessages,
  screenTitles,
} from '../assets/strings';
import { themeColors } from '../assets/colors';
import { NAVIGATION_OPTIONS } from '../../utils/enums';

const Transactions = ({ navigation }: any) => {
  const showToast = (message: string) => {
    ToastAndroid.show(
      `${message} ${genericErrorMessages.TOAST_MESSAGE_SINGULAR}`,
      ToastAndroid.SHORT
    );
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.card}
        onPress={() =>
          navigation.navigate(screens.RETRIEVE_REGISTRATION_DATA, {
            navOptions: NAVIGATION_OPTIONS.AUTHENTICATE_USER,
          })
        }
      >
        <Text style={styles.sectionLabel}>
          {transactionsScreenStrings.ACTION}
        </Text>
        <Text style={styles.cardTitle}>
          {transactionsScreenStrings.AUTHENTICATE_USER_ON_PROGRAM}
        </Text>
        <Text style={styles.cardDescription}>
          {transactionsScreenStrings.AUTHENTICATE_USER_ON_PROGRAM_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => showToast(screenTitles.PROVIDE_A_SERVICE)}
      >
        <Text style={styles.sectionLabel}>
          {transactionsScreenStrings.ACTION}
        </Text>
        <Text style={styles.cardTitle}>
          {transactionsScreenStrings.PROVIDE_A_SERVICE_TITLE}
        </Text>
        <Text style={styles.cardDescription}>
          {transactionsScreenStrings.PROVIDE_A_SERVICE_DESCRIPTION}
        </Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  card: {
    backgroundColor: themeColors.white,
    borderRadius: 5,
    padding: 30,
    marginBottom: 30,
  },
  cardTitle: {
    color: themeColors.black,
    marginBottom: 10,
    fontWeight: '600',
    fontSize: 16,
  },
  sectionLabel: {
    color: themeColors.black,
    marginBottom: 10,
    fontWeight: '400',
    fontSize: 14,
  },
  cardDescription: {
    color: themeColors.darkGray,
    marginBottom: 10,
    fontSize: 14,
  },
});

export default Transactions;
