import {
  NativeModules,
  NativeEventEmitter,
  Platform,
  Alert,
} from 'react-native';
const { ScreenshotDetector } = NativeModules;

function isNativeModuleLoaded(module) {
  if (module == null) {
    console.error(
      'Could not load RNAppsOnAir native module. Make sure native dependencies are properly linked.'
    );
    return false;
  }
  return true;
}

const AppsOnAirModule = NativeModules.RNAppsOnAir
  ? NativeModules.RNAppsOnAir
  : new Proxy(
      {},
      {
        get() {
          throw new Error();
        },
      }
    );

const screenshotEventEmitter = new NativeEventEmitter(ScreenshotDetector);

export default class AppsOnAir {
  static isListenerAdded = false;

  static setAppId(appId, showNativeUI = false) {
    if (!isNativeModuleLoaded(AppsOnAirModule)) {
      return;
    }

    AppsOnAirModule.setAppId(appId, showNativeUI);
  }

  static checkForAppUpdate(callback) {
    if (!isNativeModuleLoaded(AppsOnAirModule)) {
      return;
    }

    AppsOnAirModule.checkForAppUpdate((response) => {
      callback(response);
    });
  }

  static detectScreenshot() {
    if (Platform.OS === 'ios') {
      if (!this.isListenerAdded) {
        screenshotEventEmitter.addListener('screenshotTaken', (event) => {
          Alert.alert(
            'Alert Screenshot',
            `Screenshot taken!\nPath: ${event.path}`,
            [
              {
                text: 'Cancel',
                onPress: () => console.log('Cancel Pressed'),
                style: 'cancel',
              },
              { text: 'OK', onPress: () => console.log('OK Pressed') },
            ]
          );
        });
        this.isListenerAdded = true;
      }
      ScreenshotDetector.detectScreenshot();
    }
  }
}
