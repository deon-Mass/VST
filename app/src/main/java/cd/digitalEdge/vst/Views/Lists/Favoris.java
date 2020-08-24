package cd.digitalEdge.vst.Views.Lists;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kosalgeek.android.caching.FileCacher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cd.digitalEdge.vst.Adaptors.Adaptor_commands_list;
import cd.digitalEdge.vst.Adaptors.Adaptor_favoris_list;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Views.Blanks.Add_product;
import cd.digitalEdge.vst.Views.Gerer;

public class Favoris extends AppCompatActivity {

    Context context = this;
    Adaptor_favoris_list adaptor;
    ListView list_favoris;
    LinearLayout progress_data, error404;
    
    ArrayList<Articles> FAVORIS = new ArrayList<>();
    FileCacher<ArrayList<Articles>> DATAS_CACHED = new FileCacher<>(context, Constants.FILE_FAVORIS);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        getSupportActionBar().setTitle("Mes Favoris");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        INIT_COMPONENT();
        //getData();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            LoadFavoris();
        }else{
            LoadCache();
        }
    }

    private void INIT_COMPONENT() {
        progress_data = findViewById(R.id.progress_data);
        list_favoris = findViewById(R.id.list_favoris);
        error404 = findViewById(R.id.error404);

        progress_data.setVisibility(View.GONE);
        error404.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Gerer.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
        }
        return true;
    }


        // TODO METHOD
        void getData(){
            //adaptor = new Adaptor_favoris_list(context);
            list_favoris.setAdapter(adaptor);
        }

    private void LoadFavoris(){
        //LoadCache();
        Log.i("FAVORIS_DATAS ",Config.GET_FAVORI.concat(Preferences.getCurrentUser(context).getId()));
        progress_data.setVisibility(View.VISIBLE);
        //error404.setVisibility(View.GONE);
        FAVORIS.clear();
        AndroidNetworking
                .get(Config.GET_FAVORI.concat(Preferences.getCurrentUser(context).getId()))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray ar = response.getJSONArray("results");
                            //Log.e("FAVORIS_DATAS RR ", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(String.valueOf(i+1));
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                p.setImages(jsonObject.getString(new Articles().images));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price2));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                FAVORIS.add(p);
                            }

                            DATAS_CACHED.writeCache(FAVORIS);

                            if (FAVORIS.size()<1){
                                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                                //error404.setVisibility(View.VISIBLE);
                                LoadCache();
                            }else{
                                adaptor = new Adaptor_favoris_list(context, FAVORIS);
                                list_favoris.setAdapter(adaptor);
                                //error404.setVisibility(View.GONE);
                            }
                            progress_data.setVisibility(View.GONE);

                        } catch (Exception e) {
                            //Log.e("FAVORIS_DATAS--XX ",e.getMessage());
                            //error404.setVisibility(View.VISIBLE);
                            LoadCache();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        Log.e("FAVORIS_DATAS ",anError.getMessage());
                        //error404.setVisibility(View.VISIBLE);
                        LoadCache();
                    }
                });
    }

    private void LoadCache() {
        progress_data.setVisibility(View.VISIBLE);
        try {
            if (DATAS_CACHED.getSize()<1){
                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                error404.setVisibility(View.VISIBLE);
            }else{
                adaptor = new Adaptor_favoris_list(context, FAVORIS);
                list_favoris.setAdapter(adaptor);
                //error404.setVisibility(View.GONE);
            }
            progress_data.setVisibility(View.GONE);
            //Log.e("CACHED_DATA", String.valueOf(DATAS_CACHED.readCache().size()));
        } catch (Exception e) {
            e.printStackTrace();
            error404.setVisibility(View.VISIBLE);
        }
    }

}
