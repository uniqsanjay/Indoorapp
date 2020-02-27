package com.example.college_management;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BeaconDetectService extends Service implements BeaconConsumer {
    private final String TAG = BeaconDetectService.class.getSimpleName();
    private BeaconManager beaconManager;
    public BeaconDetectService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeBeaconmanager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindBeaconManger();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onBeaconServiceConnect() {
// Specifies a class that should be called each time the BeaconService sees or stops seeing a Region of beacons.
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.e("beacon detected", region.getUniqueId());
                notifyBeacon(region, true);
                setRangeNotify(region);
            }
            @Override
            public void didExitRegion(Region region) {
                Log.e("beacon lost", region.getUniqueId());
                notifyBeacon(region, false);
            }
            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });
        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("beacon.demo.com.beacondemo", null, null, null));
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    private void initializeBeaconmanager() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // call the below line only when you trying to detect beacons that that does not match AltBeacon standard
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //beaconManager.bind(this);
    }

    private void bindBeaconManger() {
        if (beaconManager != null) {
            beaconManager.bind(this);
        } else {
            initializeBeaconmanager();
            beaconManager.bind(this);
        }
    }

    /**
     * Start ranging of beacons
     * @param region beacon region
     */
    private void setRangeNotify(Region region) {
        // Specifies a class that should be called each time the BeaconService gets ranging data, which is nominally once per second when beacons are detected.
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    for (Beacon beacon : beacons) {
                        System.out.println("Major: " + beacon.getId2().toString());
                    }
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (Exception e) {
        }
    }

    /**
     * Method that trigger notification each time when enter or exit in beacon region
     * @param region beacon region
     * @param isEnter true if we enter inside beacon region otherwise false
     */
    private void notifyBeacon(Region region, boolean isEnter) {
        String message = null;
        if (isEnter) {
            message = "You have enter at " + region.getUniqueId();
        } else {
            message = "You have exit from " + region.getUniqueId();
        }
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Sets an ID for the notification, so it can be updated
        int notifyID = (int) System.currentTimeMillis();
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Beacon Notification")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);
        mNotifyBuilder.setContentText(message);
        // Because the ID remains unchanged, the existing notification is
        // updated.
        mNotificationManager.notify(
                notifyID,
                mNotifyBuilder.build());
    }
}
