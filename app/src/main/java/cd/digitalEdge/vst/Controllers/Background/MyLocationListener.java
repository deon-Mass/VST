package cd.digitalEdge.vst.Controllers.Background;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MyLocationListener implements LocationListener {
    Context context;

    public MyLocationListener(Context context2) {
        this.context = context2;
    }

    public void onLocationChanged(Location loc) {
        Context context2 = this.context;
        StringBuilder sb = new StringBuilder();
        sb.append("Location changed: Lat: ");
        sb.append(loc.getLatitude());
        sb.append(" Lng: ");
        sb.append(loc.getLongitude());
        Toast.makeText(context2, sb.toString(), Toast.LENGTH_LONG).show();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Longitude: ");
        sb2.append(loc.getLongitude());
        String str = "LOCATION_TAG";
        Log.v(str, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Latitude: ");
        sb3.append(loc.getLatitude());
        Log.v(str, sb3.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
