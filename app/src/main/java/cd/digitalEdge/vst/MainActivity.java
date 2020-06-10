package cd.digitalEdge.vst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Views.Lists.List_clients;
import cd.digitalEdge.vst.Views.Signin.Login;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    TextView text;

    NavigationView navigationView;
    DrawerLayout drawer;
    CardView CARD_CLIENTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().show();

        if (Preferences.getUserPreferences(context, "logged").equals("")){}
        INIT_COMPONENT();
    }

    private void INIT_COMPONENT(){
        text = findViewById(R.id.text);
        drawer = findViewById(R.id.drawer);
        CARD_CLIENTS = findViewById(R.id.CARD_CLIENTS);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);
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
        if (id == R.id.titre1) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            m = true;
        } else if (id == R.id.titre1) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.profil :
                drawer.openDrawer(GravityCompat.END );
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
