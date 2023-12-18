#import <Foundation/Foundation.h>
#import <React/RCTBridge.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTRootView.h>
#import <React/RCTConvert.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTViewManager.h>
#import <React/RCTBridgeModule.h>


@interface RCT_EXTERN_MODULE(RNAppsOnAir, NSObject)
RCT_EXTERN_METHOD(setAppId: (NSString)appId showNativeUI: (BOOL)showNativeUI)
RCT_EXTERN_METHOD(checkForAppUpdate: (RCTResponseSenderBlock)callback)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end

@interface RCT_EXTERN_MODULE(ScreenshotDetector, NSObject)
RCT_EXTERN_METHOD(detectScreenshot)
@end
