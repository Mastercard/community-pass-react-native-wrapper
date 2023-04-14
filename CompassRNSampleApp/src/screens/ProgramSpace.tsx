import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { programSpaceScreenStrings, screens } from '../assets/strings';
import { themeColors } from '../assets/colors';

const ProgramSpace = ({ navigation }: any) => {
  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.READ_PROGRAM_SPACE)}
      >
        <Text style={styles.sectionLabel}>
          {programSpaceScreenStrings.ACTION}
        </Text>
        <Text style={styles.cardTitle}>
          {programSpaceScreenStrings.READ_PROGRAM_SPACE_TITLE}
        </Text>
        <Text style={styles.cardDescription}>
          {programSpaceScreenStrings.READ_PROGRAM_SPACE_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.WRITE_PROGRAM_SPACE)}
      >
        <Text style={styles.sectionLabel}>
          {programSpaceScreenStrings.ACTION}
        </Text>
        <Text style={styles.cardTitle}>
          {programSpaceScreenStrings.WRITE_PROGRAM_SPACE_TITLE}
        </Text>
        <Text style={styles.cardDescription}>
          {programSpaceScreenStrings.WRITE_PROGRAM_SPACE_DESCRIPTION}
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

export default ProgramSpace;
