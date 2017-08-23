package com.jdkgroup.baseclasses;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.messaging.RemoteMessage;
import com.jdkgroup.database.PMSDatabase;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;
import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.fcm.FCMPushNotificationReceivedListener;
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener;
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication = null;

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        baseApplication = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aileron_regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        Realm.init(this);
        pusherNotificationInit();

     /*   byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);*/

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(PMSDatabase.NAME)
                .schemaVersion(PMSDatabase.VERSION)
                .deleteRealmIfMigrationNeeded()
                //.encryptionKey(key)
                .inMemory()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        //FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public void pusherNotificationInit() {
        try {
            PusherAndroid pusher = new PusherAndroid("886d7aaef31603216409");
            PushNotificationRegistration nativePusher = pusher.nativePusher();
            nativePusher.registerFCM(this);
            nativePusher.subscribe("fcm");
            nativePusher.registerFCM(this, new PushNotificationRegistrationListener() {
                @Override
                public void onSuccessfulRegistration() {
                    AppUtils.loge("PUSHER REGISTRATION " + "SUCCESSFUL");
                }

                @Override
                public void onFailedRegistration(int statusCode, String response) {
                    AppUtils.loge("PUSHER EVENT " + statusCode + " - " + response);
                }
            });

            nativePusher.subscribe("donuts", new InterestSubscriptionChangeListener() {
                @Override
                public void onSubscriptionChangeSucceeded() {
                    System.out.println("Success! I love donuts!");
                }

                @Override
                public void onSubscriptionChangeFailed(int statusCode, String response) {
                    System.out.println(":(: received " + statusCode + " with" + response);
                }
            });

            nativePusher.setFCMListener(new FCMPushNotificationReceivedListener() {
                @Override
                public void onMessageReceived(RemoteMessage remoteMessage) {

                }
            });
        } catch (Exception ex) {
            AppUtils.loge("PUSHER ERROR " + ex.toString());
        }
    }

}
