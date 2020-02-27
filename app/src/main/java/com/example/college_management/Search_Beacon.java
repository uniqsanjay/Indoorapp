package com.example.college_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;


public class Search_Beacon extends AppCompatActivity implements BeaconConsumer {

    RelativeLayout rl;
    //Recycler View
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    //Beacon Manager
    private BeaconManager beaconManager;
    // Progress bar
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__beacon);

        rl = findViewById(R.id.Relative_One);

        // Recycler View
        rv = findViewById(R.id.search_recycler);

        //Progress Bar
        pb = findViewById(R.id.pb);

        beaconManager= BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser(). setBeaconLayout("m:2–3=beac,i:4–19,i:20–21,i:22–23,p:24–24,d:25–25"));
        beaconManager.bind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        final Region region = new Region("myBeaons",null, null, null);

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                System.out.println("ENTER ------------------->");
                try {
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                System.out.println("EXIT----------------------->");
                try {
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                System.out.println( "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    try{
                        Search_Beacon.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb.setVisibility(View.INVISIBLE);
                                rl.setVisibility(View.GONE);
                                rv.setVisibility(View.VISIBLE);
                                layoutManager = new LinearLayoutManager(Search_Beacon.this);
                                rv.setLayoutManager(layoutManager);
                            }
                        });
                    }
                    catch(Exception e){

                    }
                    final ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
                    for (Beacon b:beacons){
                        String uuid = String.valueOf(b.getId1());
                        String major = String.valueOf(b.getId2());
                        String minor = String.valueOf(b.getId3());
                        String distance = String.valueOf(b.getDistance());
                        System.out.println(uuid);
                        ArrayList<String> arr = new ArrayList<String>();
                        arr.add(uuid);
                        arr.add(major);
                        arr.add(minor);
                        arr.add(distance);
                        arrayList.add(arr);
                    }
                    try {
                        Search_Beacon.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new RecyclerAdapter(arrayList);
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }catch(Exception e){

                    }
                }
                else if (beacons.size()==0) {
                    try {
                        Search_Beacon.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb.setVisibility(View.INVISIBLE);
                                rl.setVisibility(View.VISIBLE);
                                rv.setVisibility(View.GONE);
                            }
                        });
                    } catch (Exception e) {

                    }
                }
            }
        });
        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {    }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        this.unbindService(conn);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int i) {
        return this.bindService(service, conn, i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
}
