package cd.digitalEdge.vst.Views.Lists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.kosalgeek.android.caching.FileCacher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cd.digitalEdge.vst.Adaptors.Adaptor_biens_list;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Views.Blanks.Add_product;
import cd.digitalEdge.vst.Views.Gerer;

public class Biens extends AppCompatActivity {

    Context context = this;
    Adaptor_biens_list adaptor;

    ListView list_goods;
    TabItem tab_actif, tab_attente,tab_vendu;
    BottomNavigationView bottomnavigation;
    LinearLayout progress_data,error404;


    ArrayList<Articles> DATA_GOODS = new ArrayList<>();
    ArrayList<Articles> DATA_VENDU = new ArrayList<>();
    ArrayList<Articles> DATA_ATTENTE = new ArrayList<>();
    FileCacher<ArrayList<Articles>> DATAS_CACHED = new FileCacher<>(context, Constants.FILE_BIENS);
    FileCacher<ArrayList<Articles>> DATAS_CACHED_VENDU = new FileCacher<>(context, Constants.FILE_VENDU);
    FileCacher<ArrayList<Articles>> DATAS_CACHED_ATTENTE = new FileCacher<>(context, Constants.FILE_ATTENTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biens);
        getSupportActionBar().setTitle("Mes biens | Actifs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        INIT_COMPONENT();
        bottomnavigation.setSelectedItemId(R.id.tab_actif);
        //getdata();
        LoadBiens();
    }

    private void INIT_COMPONENT() {
        list_goods = findViewById(R.id.list_goods);
        bottomnavigation = findViewById(R.id.bottomnavigation);
        progress_data = findViewById(R.id.progress_data);
        error404 = findViewById(R.id.error404);

        progress_data.setVisibility(View.GONE);
        error404.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i  = new Intent(context, Recherche.class);
                switch (item.getItemId()){
                    case R.id.tab_actif:
                        getSupportActionBar().setTitle("Mes biens | Actifs");
                        SetDatabyViews(DATA_GOODS);
                        //Toast.makeText(context, "actif", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_attente:
                        getSupportActionBar().setTitle("Mes biens | Attente");
                        SetDatabyViews(DATA_ATTENTE);
                        //Toast.makeText(context, "tab_attente", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_vendu:
                        getSupportActionBar().setTitle("Mes biens | Vendus");
                        SetDatabyViews(DATA_VENDU);
                        //Toast.makeText(context, "tab_vendu", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.biens_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_product:
                Intent i  = new Intent(context, Add_product.class);
                startActivity(i);
                finish();
                break;
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Gerer.class);
        startActivity(i);
        finish();
    }

    private void getdata(){
        //adaptor = new Adaptor_biens_list(context);
        list_goods.setAdapter(adaptor);
    }

    private void LoadBiens(){
        Log.i("GOODS_LINK ",Config.GET_GOODS.concat(Preferences.getCurrentUser(context).getId()));
        progress_data.setVisibility(View.VISIBLE);
        //error404.setVisibility(View.GONE);
        DATA_GOODS.clear();
        AndroidNetworking
                .get(Config.GET_GOODS.concat(Preferences.getCurrentUser(context).getId()))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GOODS_LINK ",response.toString());
                        try {
                            JSONArray disponible = response.getJSONArray("disponible");
                            JSONArray vendus = response.getJSONArray("vendus");
                            JSONArray en_attente_validation = response.getJSONArray("en_attente_validation");
                            //Log.e("PRODUCT_DATAS RR ", ar.toString());

                            for (int i = 0; i < disponible.length(); i++) {
                                JSONObject jsonObject = disponible.getJSONObject(i);
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                p.setImages(jsonObject.getString(new Articles().images));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price2));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                DATA_GOODS.add(p);
                            }
                            DATAS_CACHED.writeCache(DATA_GOODS);

                            for (int i = 0; i < vendus.length(); i++) {
                                JSONObject jsonObject = disponible.getJSONObject(i);
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                p.setImages(jsonObject.getString(new Articles().images));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price2));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                DATA_VENDU.add(p);
                            }
                            DATAS_CACHED_VENDU.writeCache(DATA_VENDU);

                            for (int i = 0; i < en_attente_validation.length(); i++) {
                                JSONObject jsonObject = disponible.getJSONObject(i);
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                p.setImages(jsonObject.getString(new Articles().images));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price2));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                DATA_ATTENTE.add(p);
                            }
                            DATAS_CACHED_ATTENTE.writeCache(DATA_ATTENTE);

                            //TODO ;  Attache views by BootomNavigation
                            SetDatabyViews(DATA_GOODS);

                            progress_data.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.e("GOODS_LINK--XX ",e.getMessage());
                            error404.setVisibility(View.VISIBLE);
                            LoadCache();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        Log.e("GOODS_LINK ",anError.getMessage());
                        error404.setVisibility(View.VISIBLE);
                        LoadCache();
                    }
                });
    }

    private void SetDatabyViews(ArrayList<Articles> DATA_GOODS) {
        if (DATA_GOODS.size()<1){
            error404.setVisibility(View.VISIBLE);
            Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
            LoadCache();
        }else{
            error404.setVisibility(View.GONE);
            adaptor = new Adaptor_biens_list(context, DATA_GOODS);
            list_goods.setAdapter(adaptor);
        }
    }
    private void LoadCache() {
        progress_data.setVisibility(View.VISIBLE);
        try {
            if (DATAS_CACHED.getSize()<1){
                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                //error404.setVisibility(View.VISIBLE);
            }else{
                SetDatabyViews(DATAS_CACHED.readCache());
                //error404.setVisibility(View.GONE);
            }
            progress_data.setVisibility(View.GONE);
            //Log.e("CACHED_DATA", String.valueOf(DATAS_CACHED.readCache().size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
