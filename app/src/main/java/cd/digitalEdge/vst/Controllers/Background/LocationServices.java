package cd.digitalEdge.vst.Controllers.Background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.Tools.Tool;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

//import io.nlopez.smartlocation.OnLocationUpdatedListener;
//import io.nlopez.smartlocation.SmartLocation;

public class LocationServices extends Service {
    public static final int notify = 2000;
    Context context = this;
    //private FusedLocationProviderClient client;
    int count = 0;
    LocationManager locationManager;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private Timer mTimer = null;

    class TimeDisplay extends TimerTask {
        TimeDisplay() {
        }

        public void run() {
            LocationServices.this.mHandler.post(new Runnable() {
                public void run() {

                    Log.e("MYLOCATION", "je tourne bien");
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = manager.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {
                        LocationServices.this.Mylocation();
                    }
                }
            });
        }
    }


    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        } else {
            this.mTimer = new Timer();
        }
        //this.client = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(getApplicationContext());
        this.mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, 2000);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
    }

    /* access modifiers changed from: 0000 */
    public void Mylocation() {
        try {
            SmartLocation.with(getApplicationContext()).location().start(new OnLocationUpdatedListener() {
                public void onLocationUpdated(Location location) {
                    if (location == null) {
                        LocationServices.this.Mylocation();
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getLatitude());
                    sb.append(",");
                    sb.append(location.getLongitude());
                    String sb2 = sb.toString();
                    Log.e("MYLOCATION", sb2);
                    Tool.setUserPreferences(context, Config_preferences.LAT, String.valueOf(location.getLatitude()));
                    Tool.setUserPreferences(context, Config_preferences.LONG, String.valueOf(location.getLongitude()));
                }
            });
        } catch (Exception e) {
            Log.e("CodePackage.LOCATION", e.getMessage());
        }
    }
}
