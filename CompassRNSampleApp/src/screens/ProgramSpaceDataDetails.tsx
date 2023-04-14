import React, { useCallback, useEffect, useState } from 'react';
import { View, Text, StyleSheet, Dimensions, ScrollView } from 'react-native';

import CustomButton from './components/CustomButton';
import { buttonLabels, screens } from '../assets/strings';
import { themeColors } from '../assets/colors';
import type { SharedSpaceDataSchema } from '../types/programSpaceDataType';

const { width: WIDTH } = Dimensions.get('screen');

const ProgramSpaceDataDetails = ({ route, navigation }: any) => {
  const { programSpaceData } = route?.params;
  const [data, setData] = useState<SharedSpaceDataSchema>();

  const updateStateWithData = useCallback(() => {
    setData(JSON.parse(programSpaceData));
  }, [programSpaceData]);

  useEffect(() => {
    updateStateWithData();
  }, [updateStateWithData]);

  return (
    <View style={styles.container}>
      <View style={styles.buttonContainer}>
        <CustomButton
          isLoading={false}
          onPress={() => navigation.navigate(screens.HOME)}
          label={buttonLabels.GO_HOME}
          customStyles={styles.readProgramSpaceButton}
          labelStyles={styles.readProgramSpaceButtonLabel}
          indicatorColor={themeColors.white}
        />
      </View>
      <ScrollView style={styles.scrollviewStyles}>
        <Text style={styles.collectionTitle}> User Information</Text>
        <View style={styles.userCardContainer}>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>First Name</Text>
            <Text style={styles.desc}>{data?.user?.firstName}</Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Last Name</Text>
            <Text style={styles.desc}>{data?.user?.lastName}</Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Age</Text>
            <Text style={styles.desc}>{data?.user?.age}</Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Mobile Number</Text>
            <Text style={styles.desc}>{data?.user?.mobileNumber}</Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>R-ID</Text>
            <Text style={styles.desc}>{data?.user?.rId}</Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Address</Text>
            <Text style={styles.desc}>{data?.user?.address}</Text>
          </View>
        </View>
        <Text style={styles.collectionTitle}> Collection Information</Text>
        <View style={styles.userCardContainer}>
          <Text style={styles.collectionTitle}>Collection 1</Text>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.id}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.produceId}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce Name</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.produceName}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.amount}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount per gram</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.amountPerGram}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Weight in grams</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.weightInGrams}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.firstCollection?.timeStamp}
            </Text>
          </View>
        </View>
        <View style={styles.userCardContainer}>
          <Text style={styles.collectionTitle}>Collection 2</Text>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.id}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.produceId}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce Name</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.produceName}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.amount}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount per gram</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.amountPerGram}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Weight in grams</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.weightInGrams}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.secondCollection?.timeStamp}
            </Text>
          </View>
        </View>
        <View style={styles.userCardContainer}>
          <Text style={styles.collectionTitle}>Collection 3</Text>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.id}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.produceId}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce Name</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.produceName}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.amount}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount per gram</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.amountPerGram}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Weight in grams</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.weightInGrams}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.thirdCollection?.timeStamp}
            </Text>
          </View>
        </View>
        <View style={styles.userCardContainer}>
          <Text style={styles.collectionTitle}>Collection 4</Text>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.id}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.produceId}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce Name</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.produceName}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.amount}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount per gram</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.amountPerGram}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Weight in grams</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.weightInGrams}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fourthCollection?.timeStamp}
            </Text>
          </View>
        </View>
        <View style={styles.userCardContainer}>
          <Text style={styles.collectionTitle}>Collection 5</Text>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.id}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.produceId}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Produce Name</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.produceName}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.amount}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Amount per gram</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.amountPerGram}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>Weight in grams</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.weightInGrams}
            </Text>
          </View>
          <View style={styles.userCardWrapper}>
            <Text style={styles.label}>ID</Text>
            <Text style={styles.desc}>
              {data?.collectionInfo?.fifthCollection?.timeStamp}
            </Text>
          </View>
        </View>
        <View style={styles.bottomPadding} />
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: WIDTH,
    paddingHorizontal: 10,
    alignItems: 'center',
    backgroundColor: themeColors.white,
  },
  scrollviewStyles: {
    flex: 1,
    width: WIDTH,
    paddingHorizontal: 20,
    paddingTop: 20,
  },
  bottomPadding: {
    marginBottom: 120,
  },
  userCardWrapper: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    width: '100%',
    marginBottom: 5,
  },
  label: {
    fontSize: 16,
    color: '#000000',
    flex: 0.5,
  },
  desc: {
    fontSize: 16,
    color: '#000000',
    fontWeight: '700',
    flex: 0.5,
  },
  userCardContainer: {
    shadowColor: '#333',
    elevation: 10,
    border: 1,
    borderColor: '#eaeaea',
    margin: 5,
    paddingVertical: 20,
    paddingHorizontal: 20,
    backgroundColor: '#ffffff',
    borderRadius: 10,
    marginBottom: 20,
  },
  collectionTitle: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 10,
  },
  readProgramSpaceButton: {
    minWidth: '100%',
    marginBottom: 20,
  },
  readProgramSpaceButtonLabel: {
    color: themeColors.white,
    textAlign: 'center',
  },
  buttonContainer: {
    position: 'absolute',
    bottom: 20,
    zIndex: 10000,
    marginHorizontal: 20,
  },
});

export default ProgramSpaceDataDetails;
