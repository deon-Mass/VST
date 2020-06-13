package cd.digitalEdge.vst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import cd.digitalEdge.vst.Controllers.Background.LocationServices;
import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Views.Lists.List_clients;
import cd.digitalEdge.vst.Views.Lists.List_product;
import cd.digitalEdge.vst.Views.Signin.Login;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    TextView text;

    NavigationView navigationView;
    DrawerLayout drawer;
    CardView CARD_CLIENTS,CARD_PRODUCTS;
    AnyChartView anyChartView;
    SearchView searchView;
    ProgressBar charts_progressBar;

    TextView textCartItemCount;
    int mCartItemCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().show();

        if (Preferences.getUserPreferences(context, "logged").equals("")){}
        INIT_COMPONENT();
        LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        conditions();
        CheckPermission();
        set_Charts();
    }

    public static void startServiceIfItsNotRuning(Class<?> class1, Context context) {
        context.startService(new Intent(context, class1));
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (class1.getName().equals(service.service.getClassName())) {
                Log.d("servisstart ", class1.getName());
                return;
            }
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String str = "No";
        builder.setMessage((CharSequence) "Your GPS seems to be disabled, do you want to enable it?")
               .setCancelable(false).setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                    }
                })
                .setNegativeButton((CharSequence) str, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }


    private void startLocationListener() {

        long mLocTrackingInterval = 1000 * 5; // 5 sec
        float trackingDistance = 0;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(this)
                .location()
                .continuous()
                .config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(location.getLatitude());
                        sb.append(",");
                        sb.append(location.getLongitude());
                        String sb2 = sb.toString();
                        Log.e("MYLOCATION", sb2);
                        Tool.setUserPreferences(context, Config_preferences.LAT, String.valueOf(location.getLatitude()));
                        Tool.setUserPreferences(context, Config_preferences.LONG, String.valueOf(location.getLongitude()));

                        String lat = Tool.getUserPreferences(context, Config_preferences.LAT);
                        String lng = Tool.getUserPreferences(context, Config_preferences.LONG);
                        Tool.Dialog(context, "Ma position", lat+","+lng);
                    }
                });
    }

    public void Mylocation() {
        try {
            SmartLocation.with(context).location().start(new OnLocationUpdatedListener() {
                public void onLocationUpdated(Location location) {
                    if (location == null) {
                        Log.e("MYLOCATION", "Location null");
                        Mylocation();
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

                    String lat = Tool.getUserPreferences(context, Config_preferences.LAT);
                    String lng = Tool.getUserPreferences(context, Config_preferences.LONG);
                    Tool.Dialog(context, "Ma position", lat+","+lng);
                }
            });
        } catch (Exception e) {
            Log.e("CodePackage.LOCATION", e.getMessage());
        }
    }

    private void conditions(){
        startServiceIfItsNotRuning(LocationServices.class, context);
        if (getIntent().hasExtra("logged")){
            Snackbar.make(drawer, (CharSequence) "Bienvenu (e)", 5000).show();
        }
    }

    private void CheckPermission(){
        Dexter.withContext(context)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied() == true)
                            ;//Toast.makeText(context, "Has granted", Toast.LENGTH_SHORT).show();
                        else ;//Toast.makeText(context, "Has denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();

    }

    private void INIT_COMPONENT(){
        text = findViewById(R.id.text);
        drawer = findViewById(R.id.drawer);
        CARD_CLIENTS = findViewById(R.id.CARD_CLIENTS);
        CARD_PRODUCTS = findViewById(R.id.CARD_PRODUCTS);
        navigationView = findViewById(R.id.navigationView);
        charts_progressBar = findViewById(R.id.charts_progressBar);
        anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void set_Charts(){
        Pie pie = AnyChart.pie();
        Cartesian c = AnyChart.bar();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Entrées", 17000));
        data.add(new ValueDataEntry("Sorties", 12000));
        data.add(new ValueDataEntry("Autre", 18000));

        c.data(data);
        anyChartView.setProgressBar(charts_progressBar);
        anyChartView.setZoomEnabled(true);
        anyChartView.onSaveInstanceState();
        anyChartView.setChart(c);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CARD_CLIENTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, List_clients.class);
                startActivity(i);
                finish();
            }
        });
        CARD_PRODUCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, List_product.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        boolean m = true;
        if (id == R.id.dr_project) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            m = true;
        } else if (id == R.id.dr_commerciaux) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            m = true;
        }else if (id == R.id.dr_client) {
            Intent i  = new Intent(context, List_clients.class);
            startActivity(i);
            finish();
            m = true;
        }else if (id == R.id.dr_params) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            m = true;
        }
        if (m == true){
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }else{
            return false;
        }
    }

    public void Setting_badge(String count2) {
        if (count2.equals("0") || count2 == null){
            textCartItemCount.setVisibility(View.GONE);
        }else{
            textCartItemCount.setVisibility(View.VISIBLE);
            textCartItemCount.setText(count2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.notification);
        View actionView = menuItem.getActionView();

        // todo setting up notification badge
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        Setting_badge("10");

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.location :
                Mylocation();
                break;
            case R.id.notification :
                Toast.makeText(context, "Count", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil :
                if (drawer.isDrawerOpen(GravityCompat.END)) drawer.closeDrawer(GravityCompat.END);
                else drawer.openDrawer(GravityCompat.END );
                break;
            case R.id.logout :
                new AlertDialog.Builder(context)
                        .setTitle("Exit")
                        .setMessage("Voulez-vous vraiment quitter l'application ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i  = new Intent(context, Login.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                break;
        }

        return true;
    }
}
