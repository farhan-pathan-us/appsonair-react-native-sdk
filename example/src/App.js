import React, { Component } from 'react';
import { Text, SafeAreaView, Platform } from 'react-native';
import AppsOnAir from 'appsonair-react-native-sdk';
import { requestMultiple, PERMISSIONS } from 'react-native-permissions';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      response: 'Response Loading....',
    };
  }

  async componentDidMount() {
    // Get your appId from https://appsonair.com/
    // 2nd parameter is true/false it is show Native UI

    if (Platform.OS === 'ios') {
      requestMultiple([
        PERMISSIONS.IOS.PHOTO_LIBRARY,
        PERMISSIONS.IOS.MEDIA_LIBRARY,
        PERMISSIONS.IOS.PHOTO_LIBRARY_ADD_ONLY,
      ]).then((result) => {
        AppsOnAir.detectScreenshot();
      });
    } else if (Platform.OS === 'android') {
      requestMultiple([
        PERMISSIONS.ANDROID.READ_EXTERNAL_STORAGE,
        PERMISSIONS.ANDROID.READ_MEDIA_IMAGES,
        PERMISSIONS.ANDROID.WRITE_EXTERNAL_STORAGE,
      ]);
    }

    AppsOnAir.setAppId('xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx', true);

    AppsOnAir.checkForAppUpdate((res) => {
      console.log('res', res);
      this.setState({ response: res });
      if (Platform.OS === 'ios') {
        const isIosUpdate = this.state.response.updateData.isIOSUpdate;
        if (isIosUpdate) {
          console.log('isIosUpdate ----- ', res);
        }
      }
    });
    AppsOnAir.detectScreenshot();
  }

  render() {
    return (
      <SafeAreaView>
        <Text>{JSON.stringify(this.state.response)}</Text>
      </SafeAreaView>
    );
  }
}
export default App;
