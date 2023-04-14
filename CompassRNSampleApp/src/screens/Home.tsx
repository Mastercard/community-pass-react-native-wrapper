import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ToastAndroid,
} from 'react-native';
import {
  genericErrorMessages,
  homeScreenStrings,
  screenTitles,
  screens,
} from '../assets/strings';
import { themeColors } from '../assets/colors';

const Home = ({ navigation }: any) => {
  const showToast = (message: string) => {
    ToastAndroid.show(
      `${message} ${genericErrorMessages.TOAST_MESSAGE_PLURAL}`,
      ToastAndroid.SHORT
    );
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.PRE_TRANSACTIONS)}
      >
        <Text style={styles.sectionLabel}>{homeScreenStrings.SECTION}</Text>
        <Text style={styles.cardTitle}>
          {homeScreenStrings.PRE_TRANSACTIONS}
        </Text>
        <Text style={styles.cardDescription}>
          {homeScreenStrings.PRE_TRANSACTTIONS_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => navigation.navigate(screens.TRANSACTIONS)}
      >
        <Text style={styles.sectionLabel}>{homeScreenStrings.SECTION}</Text>
        <Text style={styles.cardTitle}>{homeScreenStrings.TRANSACTIONS}</Text>
        <Text style={styles.cardDescription}>
          {homeScreenStrings.TRANSACTTIONS_DESCRIPTION}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.card}
        onPress={() => showToast(screenTitles.ADMIN_TRANSACTIONS)}
      >
        <Text style={styles.sectionLabel}>{homeScreenStrings.SECTION}</Text>
        <Text style={styles.cardTitle}>
          {homeScreenStrings.ADMIN_TRANSACTIONS}
        </Text>
        <Text style={styles.cardDescription}>
          {homeScreenStrings.ADMIN_TRANSACTTIONS_DESCRIPTION}
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

export default Home;
