package cd.digitalEdge.vst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Cartesian3d;
import com.anychart.charts.LinearGauge;
import com.anychart.charts.Pie;
import com.anychart.charts.Polar;
import com.anychart.charts.Venn;
import com.anychart.core.cartesian.series.Column;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Views.Lists.List_clients;
import cd.digitalEdge.vst.Views.Signin.Login;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    TextView text;

    NavigationView navigationView;
    DrawerLayout drawer;
    CardView CARD_CLIENTS;
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
        conditions();
        CheckPermission();
        set_Charts();
    }



    private void conditions(){
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
            Intent i  = new Intent(context, Login.class);
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
