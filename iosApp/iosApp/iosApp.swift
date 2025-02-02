import SwiftUI
import ComposeApp
import GoogleSignIn
import FirebaseCore
import FirebaseMessaging

class AppDelegate: NSObject, UIApplicationDelegate {

    func application(_ application: UIApplication,
                       didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
          FirebaseApp.configure()
          AppInitializer.shared.onApplicationStart()
        
          // By default showPushNotification value is true.
          // When set showPushNotification to false foreground push  notification will not be shown.
          // You can still get notification content using #onPushNotification listener method.
          NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos(
                showPushNotification: true,
                askNotificationPermissionOnStart: true,
                notificationSoundName: nil)
          )
          
        return true
      }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
            Messaging.messaging().apnsToken = deviceToken
      }
    
    func application(
              _ app: UIApplication,
              open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
            ) -> Bool {
              var handled: Bool

              handled = GIDSignIn.sharedInstance.handle(url)
              if handled {
                return true
              }

              // Handle other custom URL types.

              // If not handled by this app, return false.
              return false
            }


    
}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        HelperKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView().onOpenURL(perform: { url in
                            GIDSignIn.sharedInstance.handle(url)
                        })
        }
    }
}
