package cd.digitalEdge.vst.Views.Lists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import cd.digitalEdge.vst.Adaptors.Adaptor_recherche_list;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Commande;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Views.Gerer;

public class Commandes extends AppCompatActivity {

    Context context = this;
    Adaptor_commands_list adaptor;

    ListView command_list;
    LinearLayout progress_data;

    ArrayList<Commande> COMMAMNDES = new ArrayList<>();
    FileCacher<ArrayList<Commande>> DATAS_CACHED = new FileCacher<>(context, Constants.FILE_COMMANDES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);
        getSupportActionBar().setTitle("Mes commandes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        INIT_COMPONENT();
        getData();
    }

    private void INIT_COMPONENT() {
        progress_data = findViewById(R.id.progress_data);
        command_list = findViewById(R.id.command_list);

        progress_data.setVisibility(View.GONE);
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


    void getData(){
        adaptor = new Adaptor_commands_list(context);
        command_list.setAdapter(adaptor);
    }

    // TODO METHOD
    private void LoadCommands(){
        //Log.i("PRODUCT_DATAS ",Config.GET_PRODUCTS);
        progress_data.setVisibility(View.VISIBLE);
        //error404.setVisibility(View.GONE);
        COMMAMNDES.clear();
        AndroidNetworking
                .post(Config.GET_COMMAND)
                .addBodyParameter("id", Preferences.getCurrentUser(context).getId())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("results");
                            //Log.e("PRODUCT_DATAS RR ", ar.toString());

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);
                                Commande p = new Commande();
                                p.setId(jsonObject.getString(new Commande().id));
                                COMMAMNDES.add(p);

                            }

                            DATAS_CACHED.writeCache(COMMAMNDES);

                            if (COMMAMNDES.size()<1){
                                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                                //error404.setVisibility(View.VISIBLE);
                            }else{
                                //adaptor = new Adaptor_commands_list(context, COMMAMNDES);
                                command_list.setAdapter(adaptor);
                                //error404.setVisibility(View.GONE);
                            }
                            progress_data.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                            //error404.setVisibility(View.VISIBLE);
                            LoadCache();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        Log.e("PRODUCT_DATAS ",anError.getMessage());
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
                //error404.setVisibility(View.VISIBLE);
            }else{
                //adaptor = new Adaptor_commands_list(context, DATAS_CACHED.readCache());
                command_list.setAdapter(adaptor);
                //error404.setVisibility(View.GONE);
            }
            progress_data.setVisibility(View.GONE);
            //Log.e("CACHED_DATA", String.valueOf(DATAS_CACHED.readCache().size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
