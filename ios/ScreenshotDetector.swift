
//  ScreenshotDetector.swift
//  AppsonairReactNativeSdk
//
//  Created by pathan-farhan-us on 18/12/23.
//  Copyright © 2023 Facebook. All rights reserved.
//
import UIKit
import React
import AVFoundation
import AppsOnAir


@objc(ScreenshotDetector)
class ScreenshotDetector: RCTEventEmitter {

    override static func requiresMainQueueSetup() -> Bool {
        return true
    }

    override func supportedEvents() -> [String] {
        return ["screenshotTaken"]
    }

    @objc func detectScreenshot() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(screenshotTaken),
            name: UIApplication.userDidTakeScreenshotNotification,
            object: nil
        )
    }

    @objc func screenshotTaken() {
        let path = saveScreenshot()
        sendEvent(withName: "screenshotTaken", body: ["path": path])
    }

    func saveScreenshot() -> String {
        // Implement code to save the screenshot and return the file path
        // For simplicity, let's assume the screenshot is saved in the Documents directory
        let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        let screenshotPath = documentsDirectory.appendingPathComponent("screenshot.png")
        // Save the screenshot here...
        return screenshotPath.path
    }
}
