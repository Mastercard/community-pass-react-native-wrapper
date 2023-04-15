import React from 'react';
import { View, TextInput, StyleSheet } from 'react-native';

import { themeColors } from '../../assets/colors';

const CustomInput = ({ config, value, onChange }: any) => {
  return (
    <>
      <View style={[styles.container, config.hasError && styles.errorInput]}>
        <TextInput
          style={styles.textInput}
          autoCorrect={false}
          autoCapitalize="none"
          value={value}
          selectionColor={themeColors.black}
          placeholder={config.placeholderText}
          keyboardType={config.keyboadType}
          onChangeText={onChange}
          secureTextEntry={config.secureTextEntry}
        />
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    borderColor: themeColors.gray,
    borderWidth: StyleSheet.hairlineWidth,
    borderRadius: 5,
    backgroundColor: themeColors.gray,
    marginBottom: 15,
    width: '100%',
  },
  textInput: {
    paddingHorizontal: 10,
    width: '100%',
  },
  errorText: {
    fontSize: 9,
    color: 'red',
    width: '100%',
    position: 'absolute',
    marginTop: 10,
  },
  errorInput: {
    borderColor: 'red',
  },
});

export default CustomInput;
