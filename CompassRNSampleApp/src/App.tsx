import 'react-native-gesture-handler';
import React from 'react';
import {
  TransitionPresets,
  createStackNavigator,
} from '@react-navigation/stack';
import { NavigationContainer } from '@react-navigation/native';

import SaveBiometricConsent from './screens/SaveBiometricConsent';
import WritePasscode from './screens/WritePasscode';
import WriteProfile from './screens/WriteProfile';
import RegisterUserWithBiometrics from './screens/RegisterUserWithBiometrics';
import RegisterBasicUser from './screens/RegisterBasicUser';
import Home from './screens/Home';
import PreTransactions from './screens/PreTransactions';
import UserDataForm from './screens/UserDataForm';
import EnterIdentificationNumber from './screens/EnterIdentificationNumber';
import WriteSuccessful from './screens/WriteSuccessful';
import { screens, screenTitles } from './assets/strings';
import { themeColors } from './assets/colors';
import ProgramSpace from './screens/ProgramSpace';
import ReadProgramSpace from './screens/ReadProgramSpace';
import WriteProgramSpace from './screens/WriteProgramSpace';
import ProgramSpaceDataDetails from './screens/ProgramSpaceDataDetails';

const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName={screens.HOME}
        screenOptions={{
          headerMode: 'screen',
          headerTintColor: themeColors.white,
          headerStyle: { backgroundColor: themeColors.mastercardYellow },
          ...TransitionPresets.SlideFromRightIOS,
        }}
      >
        <Stack.Screen
          name={screens.HOME}
          options={{
            headerTitle: screenTitles.HOME,
          }}
          component={Home}
        />
        <Stack.Screen
          name={screens.SAVE_BIOMETRIC_CONSENT}
          options={{
            headerTitle: screenTitles.SAVE_BIOMETRIC_CONSENT,
          }}
          component={SaveBiometricConsent}
        />
        <Stack.Screen
          name={screens.REGISTER_USER_WITH_BIOMETRICS}
          options={{
            headerTitle: screenTitles.REGISTER_USER_WITH_BIOMETRICS,
          }}
          component={RegisterUserWithBiometrics}
        />
        <Stack.Screen
          name={screens.REGISTER_BASIC_USER}
          options={{
            headerTitle: screenTitles.REGISTER_BASIC_USER,
          }}
          component={RegisterBasicUser}
        />
        <Stack.Screen
          name={screens.WRITE_PASSCODE}
          options={{
            headerTitle: screenTitles.WRITE_PASSCODE,
          }}
          component={WritePasscode}
        />
        <Stack.Screen
          name={screens.WRITE_PROFILE}
          options={{
            headerTitle: screenTitles.WRITE_PROFILE,
          }}
          component={WriteProfile}
        />
        <Stack.Screen
          name={screens.PRE_TRANSACTIONS}
          options={{
            headerTitle: screenTitles.PRE_TRANSACTIONS,
          }}
          component={PreTransactions}
        />
        <Stack.Screen
          name={screens.USER_DATA_FORM}
          options={{
            headerTitle: screenTitles.USER_DATA_FORM,
          }}
          component={UserDataForm}
        />
        <Stack.Screen
          name={screens.ENTER_IDENTIFICATION_NUMBER}
          options={{
            headerTitle: screenTitles.ENTER_IDENTIFICATION_NUMBER,
          }}
          component={EnterIdentificationNumber}
        />
        <Stack.Screen
          name={screens.WRITE_SUCCESSFUL}
          options={{
            headerTitle: screenTitles.WRITE_SUCCESSFUL,
          }}
          component={WriteSuccessful}
        />
        <Stack.Screen
          name={screens.PROGRAM_SPACE}
          options={{
            headerTitle: screenTitles.PROGRAM_SPACE,
          }}
          component={ProgramSpace}
        />
        <Stack.Screen
          name={screens.READ_PROGRAM_SPACE}
          options={{
            headerTitle: screenTitles.READ_PROGRAM_SPACE,
          }}
          component={ReadProgramSpace}
        />
        <Stack.Screen
          name={screens.WRITE_PROGRAM_SPACE}
          options={{
            headerTitle: screenTitles.WRITE_PROGRAM_SPACE,
          }}
          component={WriteProgramSpace}
        />
        <Stack.Screen
          name={screens.PROGRAM_SPACE_DATA_DETAILS}
          options={{
            headerTitle: screenTitles.PROGRAM_SPACE_DATA_DETAILS,
          }}
          component={ProgramSpaceDataDetails}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
