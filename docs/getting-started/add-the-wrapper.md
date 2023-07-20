# Section 5: Install the Community Pass React Native Wrapper

## 5.1 Objectives

To help you connect to the Community Pass Kernel, our team created the Community Pass React Native Wrapper which bridges the gap between your application and the Community Pass Kernel. This library will enable you to develop an application using the CPK service’s APIs.

- At the end of this step, you will have added the Community Pass React Native Wrapper to your React Native reliant application.

## 5.2 Pre-requisites

1. Completed the sections 1, 2, 3 and 4 of this guide
2. The Community Pass Client Library, which is accessible via the [CP Assets](https://developer.mastercard.com/cp-kernel-integration-api/documentation/cp-assets/cp-assets-request/) Request. We will show you how to add the library to your project.

```
NOTE: Please note that you will need to create a developer account and request access to the library file
through CP Assets request. The approval may take 1-2 business days. Once you have access, proceed to download
the library for your development environment.
```

The following are the steps required to set up your project with the Community Pass Client SDK:

## 5.3 Installation

1. Download the Community Pass React Native Wrapper from the [releases](https://github.com/Mastercard/community-pass-react-native-wrapper/releases/) section  
2. Locate the folder where you downloaded the library to. The library will have a name similar to the following example: `community-pass-react-native-wrapper-x.y.z.tgz`
3. Open a terminal/command prompt and navigate to the root folder of your reliant application project. See example below:

```sh
cd /path/TestApp
```

4. From the root folder of your reliant applciation , run the following command to install the Community Pass React Native Wrapper

```sh
npm install /path/community-pass-react-native-wrapper-x.y.z.tgz
```
5. Add the Community Pass Client Library to your Wrapper by :

```sh
cp /path/to/community-pass-library-v2.5.1.aar node_modules/community-pass-react-native-wrapper/android/libs/community-pass-library-v2.5.1.aar
```

6. Open `android/app/build.gradle` file of your reliant application and add the following line to your dependencies

```gradle
dependencies {
    ...

    // add this to your list of dependencies
    implementation files("../../node_modules/community-pass-react-native-wrapper/android/libs/community-pass-library-v2.5.1.aar");

    ...
}
```

7. In the same file `android/app/build.gradle` add the configurations block after your dependencies. See below:

```gradle
dependencies {
    ...
}

// Copy this and paste it below your dependencies block
configurations {
    implementation.exclude group: 'org.slf4j', module: 'slf4j-api'
}
```

8. In the file `android/build.gradle` bump your compileSdkVersion and targetSdkVersion to 33, and your minSdkVersion to 26

```gradle
buildscript {
    ext {
        minSdkVersion = Integer.parseInt(findProperty('android.minSdkVersion') ?: '26')
        compileSdkVersion = Integer.parseInt(findProperty('android.compileSdkVersion') ?: '33')
        targetSdkVersion = Integer.parseInt(findProperty('android.targetSdkVersion') ?: '33')
    }
}
```

9.  A pop up notification will appear as given in the below image informing you that the Gradle files have changed. Click on Sync Now to synchronize the project with the Gradle files.

![](/docs/assets/android-studio-popup.png)

## 5.4 Confirm installation

1. Locate and open the `package.json` file from the root folder of your reliant application
2. Under dependencies you should see the library added to your list. See the example below:

<br/>

```json
"dependencies": {
    ...

    "community-pass-react-native-wrapper": "file:community-pass-react-native-wrapper-x.y.z.tgz",

    ...
}
```

<br/>

---

<br/>
You should now have completed the process of adding the Community Pass React Native Wrapper into your React Native Android Project.

You are now ready to engage with the CPK on your POI device and connect your Reliant Application to the Community Pass Kernel services.

[Return to Getting Started](README.md)
