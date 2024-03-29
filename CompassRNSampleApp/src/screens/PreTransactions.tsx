import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { preTransactionsScreenStrings, screens } from '../assets/strings';
import { themeColors } from '../assets/colors';

const PreTransactions = ({ navigation }: any) => {
  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.SAVE_BIOMETRIC_CONSENT)}
      >
        <Text style={styles.sectionLabel}>
          {preTransactionsScreenStrings.ACTION}
        </Text>
        <Text style={styles.cardTitle}>
          {preTransactionsScreenStrings.ENROLL_A_NEW_USER_TITLE}
        </Text>
        <Text style={styles.cardDescription}>
          {preTransactionsScreenStrings.ENROLL_A_NEW_USER_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.PROGRAM_SPACE)}
      >
        <Text style={styles.sectionLabel}>
          {preTransactionsScreenStrings.ACTION}
        </Text>
        <Text style={styles.cardTitle}>
          {preTransactionsScreenStrings.USE_SHARED_SPACE_TITLE}
        </Text>
        <Text style={styles.cardDescription}>
          {preTransactionsScreenStrings.USE_SHARED_SPACE_DESCRIPTION}
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

export default PreTransactions;
