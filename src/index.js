import { NativeModules } from 'react-native';

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

export default class AppsOnAir {
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
}
