import React, { Component } from 'react';
import { Text, SafeAreaView, Platform } from 'react-native';
import AppsOnAir from 'appsonair-react-native-sdk';

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
