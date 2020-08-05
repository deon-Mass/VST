package cd.digitalEdge.vst;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cd.digitalEdge.vst.Adaptors.Adaptor_Categorie;
import cd.digitalEdge.vst.Adaptors.Adaptor_Panier;
import cd.digitalEdge.vst.Adaptors.Adaptor_recherche_list;
import cd.digitalEdge.vst.Controllers.Background.LocationServices;
import cd.digitalEdge.vst.Controllers.Background.MyServices;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.PrinterBluetooth.ImprimerActivity;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Contacts;
import cd.digitalEdge.vst.Views.Gerer;
import cd.digitalEdge.vst.Views.Lists.Details_Article;
import cd.digitalEdge.vst.Views.Lists.Panier;
import cd.digitalEdge.vst.Views.Lists.Recherche;
import cd.digitalEdge.vst.Views.Paramettres;
import cd.digitalEdge.vst.Views.Signin.Login;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    Users currentUser;
    Adaptor_recherche_list adapter;
    Adaptor_Categorie adapterCat;
    TextView text;

    TextView log_out_in,menu_params,categorie,panier,BTN_seeAll;
    TextView username,useremail, panier_count;
    ImageView userProfil;
    ProgressBar profil_progress;

    DrawerLayout drawer;
    EditText SEARCH;
    ImageView drawer_icon;
    BottomNavigationView bottomnavigation;
    LinearLayout progress_data,sous,BTN_gerer;

    TextView textCartItemCount;
    TextView CATEGORIE,login,contact;
    Button BTN_search;
    int mCartItemCount = 10;

    ListView listCat;
    GridView articles_list;
    SwipeRefreshLayout swiper;

    ArrayList<Categories> DATAS = new ArrayList<>();
    ArrayList<Articles> DATAS_PROD = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_logo2);
        //getSupportActionBar().setIcon(R.drawable.logo_supreme);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().hide();

        if (Preferences.getUserPreferences(context, "logged").equals("")){}
        INIT_COMPONENT();

        LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        conditions();
        CheckPermission();
        set_Charts();
        Loadprod();
        loadCat();

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
        contact = findViewById(R.id.contact);
        drawer = findViewById(R.id.drawer);
        progress_data = findViewById(R.id.progress_data);
        CATEGORIE = findViewById(R.id.CATEGORIE);
        BTN_search = findViewById(R.id.BTN_search);
        SEARCH = findViewById(R.id.SEARCH);
        login = findViewById(R.id.login);
        listCat = findViewById(R.id.listCat);
        panier = findViewById(R.id.panier);
        BTN_gerer = findViewById(R.id.BTN_gerer);
        BTN_seeAll = findViewById(R.id.BTN_seeAll);
        menu_params = findViewById(R.id.menu_params);
        categorie = findViewById(R.id.categorie);
        drawer_icon = findViewById(R.id.drawer_icon);
        articles_list = findViewById(R.id.articles_list);
        swiper = findViewById(R.id.swiper);
        bottomnavigation = findViewById(R.id.bottomnavigation);
        log_out_in = findViewById(R.id.log_out_in);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        userProfil = findViewById(R.id.userProfil);
        sous = findViewById(R.id.sous);
        panier_count = findViewById(R.id.panier_count);
        profil_progress = findViewById(R.id.profil_progress);
        profil_progress.setVisibility(View.GONE);

        //navigationView.setNavigationItemSelectedListener(this);
        startService(new Intent(this, MyServices.class));
        getUserInfo();
    }

    private void getUserInfo(){
        currentUser = Preferences.getCurrentUser(context);
        if (currentUser == null) {
            //Toast.makeText(context, "NULL USER", Toast.LENGTH_SHORT).show();
            log_out_in.setText("Se Connecter");
            log_out_in.setBackgroundResource(R.drawable.corner_dark_solid_dark);
            username.setText("Visiteur");
            panier_count.setText("0");
            useremail.setVisibility(View.GONE);
            userProfil.setImageResource(R.drawable.unknow);
            return;
        }
        log_out_in.setText("Se déconnecter");
        log_out_in.setBackgroundResource(R.drawable.corner_bleu_solid_bleu);
        username.setText(currentUser.getName());
        useremail.setText(currentUser.getEmail());
        if (Preferences.getUserPreferences(context, Constants.PANIER_COUNT) == null || Preferences.getUserPreferences(context, Constants.PANIER_COUNT).equals("")){
            panier_count.setText("0");
        }else panier_count.setText(Preferences.getUserPreferences(context, Constants.PANIER_COUNT));
        String path = Config.ROOT_img.concat(currentUser.getAvatar());

        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                profil_progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected Bitmap doInBackground(Object[] objects) {
                Bitmap bmp = null;
                try {
                    URL url = new URL("https://lesupreme.shop/storage/users/November2019/MgMgAthaK3NIDNomVAxM.jpg");
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                userProfil.setImageBitmap((Bitmap) o);
                profil_progress.setVisibility(View.GONE);
            }
        }.execute();



        //Tool.Load_Image(context, userProfil, path);


    }

    private void set_Charts(){
        /*Pie pie = AnyChart.pie();
        Cartesian c = AnyChart.bar();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Entrées", 17000));
        data.add(new ValueDataEntry("Sorties", 12000));
        data.add(new ValueDataEntry("Autre", 18000));

        c.data(data);
        anyChartView.setProgressBar(charts_progressBar);
        anyChartView.setZoomEnabled(true);
        anyChartView.onSaveInstanceState();
        anyChartView.setChart(c);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_gerer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Intent i  = new Intent(context, Gerer.class);
                    context.startActivity(i);
                    finish();
                }else{
                    Toast.makeText(context, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
                }
            }
        });
        log_out_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Preferences.setUserPreferences(context, Config_preferences.CURRENT_USER, "null");
                }
                if (log_out_in.getText().toString().equals("Se Connecter")){
                    Intent i  = new Intent(context, Login.class);
                    context.startActivity(i);
                    finish();
                }
                log_out_in.setText("Se Connecter");
                log_out_in.setBackgroundResource(R.drawable.corner_bleu_solid_bleu);
                getUserInfo();

            }
        });
        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i  = new Intent(context, Recherche.class);
                switch (item.getItemId()){
                    case R.id.Vetements:
                        i.putExtra("TITLE", "V");
                        context.startActivity(i);
                        finish();
                        break;
                    case R.id.Chaussures:
                        i.putExtra("TITLE", "C");
                        context.startActivity(i);
                        finish();
                        break;
                    case R.id.aliments:
                        i.putExtra("TITLE", "A");
                        context.startActivity(i);
                        finish();
                        break;
                    case R.id.plus:
                        if (drawer.isDrawerOpen(GravityCompat.END)) drawer.closeDrawer(GravityCompat.END);
                        else drawer.openDrawer(GravityCompat.END );
                        break;
                }
                return false;
            }
        });
        drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
                else drawer.openDrawer(GravityCompat.START );
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Contacts.class);
                context.startActivity(i);
                finish();
            }
        });
        BTN_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Recherche.class);
                context.startActivity(i);
                finish();
            }
        });
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Panier.class);
                context.startActivity(i);
                finish();
            }
        });
        categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                drawer.openDrawer(GravityCompat.END );
            }
        });
        menu_params.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Paramettres.class);
                context.startActivity(i);
                finish();
            }
        });
        BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SEARCH_Intent(SEARCH.getText().toString());
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return DRAWER_NAVIGATION(context, drawer ,id);
    }*/

    public boolean DRAWER_NAVIGATION(Context context, DrawerLayout drawer, int id){
        boolean m = true;
        if (id == R.id.dr_home) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            m = true;
        } else if (id == R.id.dr_acheter) {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            m = true;
        }else if (id == R.id.dr_panier) {
            Intent i  = new Intent(context, Panier.class);
            context.startActivity(i);
            finish();
            m = true;
        }else if (id == R.id.dr_params) {
            Intent i  = new Intent(context, Paramettres.class);
            startActivity(i);
            finish();
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

        final MenuItem menuItem = menu.findItem(R.id.panier);
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
            case R.id.panier :
                Intent i  = new Intent(context, Panier.class);
                startActivity(i);
                finish();
                break;
            case R.id.Exit :
                new AlertDialog.Builder(context)
                        .setTitle("Exit")
                        .setMessage("Voulez vous vraiment quitter l'application ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
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


    // TODO : METHODS
    private void SEARCH_Intent(String text){
        if (TextUtils.isEmpty(text)){
            Toast.makeText(context, "Veuillez saisir un mot de recherche ", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i  = new Intent(context, Recherche.class);
        i.putExtra("SEARCH_INPUT", text);
        startActivity(i);
        finish();
    }

    private void loadCat(){
        progress_data.setVisibility(View.VISIBLE);
        DATAS = Sqlite_selects_methods.getall_Categorie(context);
        if ( null == DATAS || DATAS.isEmpty() ){
            DATAS = new ArrayList<>();
            Log.e("DATA_CATEGORIE", "DATAS "+DATAS.size());
        }
        adapterCat = new Adaptor_Categorie(context, DATAS);
        listCat.setAdapter(adapterCat);
        progress_data.setVisibility(View.GONE);
    }
    private void loadCat2(){
        //Log.i("PRODUCT_DATAS ",Config.GET_PRODUCTS);
        progress_data.setVisibility(View.VISIBLE);
        DATAS.clear();
        AndroidNetworking
                .get(Config.GET_PRODUCT_CAT)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("categories");
                            //Log.i("PRODUCT_DATAS---- ", ar.toString());

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);
                                Categories p = new Categories();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                DATAS.add(p);
                            }


                            progress_data.setVisibility(View.GONE);
                            adapterCat = new Adaptor_Categorie(context, DATAS);
                            listCat.setAdapter(adapterCat);

                        } catch (JSONException e) {
                            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        Log.e("PRODUCT_DATAS ",anError.getMessage());
                    }
                });
    }

    private void Loadprod(){
        DATAS_PROD.clear();
        AndroidNetworking
                .get(Config.GET_PRODUCTS)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("products");
                            //Log.i("PRODUCT_DATAS---- ", ar.toString());

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                //p.setImages(jsonObject.getJSONArray("images"));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                DATAS_PROD.add(p);
                            }
                            horizontal_list(DATAS_PROD);

                        } catch (JSONException e) {
                            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        Log.e("PRODUCT_DATAS ",anError.getMessage());
                    }
                });
    }


    private void horizontal_list(ArrayList<Articles> DATA){
        for (Articles data : DATA) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.view_prod, null);
            CardView CARD = convertView.findViewById(R.id.CARD);
            ImageView prod_img = convertView.findViewById(R.id.prod_img);
            TextView name = convertView.findViewById(R.id.name);
            TextView price = convertView.findViewById(R.id.price);

            name.setText(data.getName());
            price.setText(data.getPrice().concat(" USD"));

            CARD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i  = new Intent(context, Details_Article.class);
                    i.putExtra("Article", data);
                    context.startActivity(i);
                }
            });

            sous.addView(convertView, 0);

        }




    }


}
