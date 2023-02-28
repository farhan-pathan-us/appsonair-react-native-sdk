//
//  RNAppsOnAir.swift
//  AppsonairReactNativeSdk
//
//  Created by appsonair on 21/02/23.
//  Copyright © 2023 Facebook. All rights reserved.
//
import Foundation
import UIKit
import AVFoundation
import AppsOnAir

@objc(RNAppsOnAir)
class RNAppsOnAir: NSObject {
  
  private var appId: String = ""
  private var window: UIWindow?
  let appsOnAirServices = AppsOnAirServices()
  
  @objc func setAppId(_ appId: String, showNativeUI: Bool = false) -> (Void) {
    self.appId = appId;
      appsOnAirServices.setAppId(appId, showNativeUI)
  }
  
  @objc func checkForAppUpdate(_ callback: @escaping RCTResponseSenderBlock) {
      appsOnAirServices.checkForAppUpdate { (appUpdateData) in
          callback([appUpdateData])
      }
  }
  
  @objc static func requiresMainQueueSetup() -> Bool {
    return true
  }
}
