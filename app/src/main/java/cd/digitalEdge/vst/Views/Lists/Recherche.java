package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kosalgeek.android.caching.FileCacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Adaptors.Adaptor_recherche_list;
import cd.digitalEdge.vst.Controllers.Background.MyServices;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Online.Selects;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Utils;
import okhttp3.Response;
import timber.log.Timber;

public class Recherche extends AppCompatActivity {
    Context context = this;
    String SEARCH_INPUT = "";
    Adaptor_recherche_list adapter;
    MainActivity MAIN = new MainActivity();

    GridView articles_list;
    ProgressBar progressbar;

    SwipeRefreshLayout swiper;
    SearchView SEARCH;
    LinearLayout progress_data, error404;

    int turn = 0;
    boolean Connected = true;
    TextView textCartItemCount, CATEGORIE,BTN_search;
    public Handler mHandler = new Handler();
    Timer mTimer = null;

    ArrayList<Articles> DATAS = new ArrayList<>();
    ArrayList<Articles> DATAS_SEARCHED = new ArrayList<>();
    FileCacher<ArrayList<Articles>> DATAS_CACHED = new FileCacher<>(context, Constants.FILE_PRODUCTS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AndroidNetworking.initialize(getApplicationContext());

        INIT_COMPONENT();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Connected = true;
        }else{
            Connected = false;
        }

        if (getIntent().hasExtra("TITLE")) {
            String from = getIntent().getExtras().getString("TITLE");
            //Log.e("TITLEEEEEEE", from);
            if (from.equals("V")) SEARCH_INPUT = "Vêtement";
            else if (from.equals("A")) SEARCH_INPUT = "Aliments";
            else if (from.equals("C")) SEARCH_INPUT = "Chaussures";
            else SEARCH_INPUT = from;
            if (Connected == true)getResearchedProd();
            else LoadCache();
        }
        else if(getIntent().hasExtra("SEARCH_INPUT")) {
            SEARCH_INPUT = getIntent().getExtras().getString("SEARCH_INPUT");
            if (Connected == true)getResearchedProd();
            else LoadCache();
        }else{
            SEARCH_INPUT = "Suprême store";
            if (Connected == true) Loadprod();
            else LoadCache();
        }

        getSupportActionBar().setTitle(SEARCH_INPUT);

    }

    private void INIT_COMPONENT() {
        articles_list = findViewById(R.id.articles_list);
        swiper = findViewById(R.id.swiper);
        progress_data = findViewById(R.id.progress_data);
        progressbar = findViewById(R.id.progressbar);
        error404 = findViewById(R.id.error404);

        //navigationView.setNavigationItemSelectedListener(this);

        progress_data.setVisibility(View.GONE);
        progressbar.setVisibility(View.GONE);
        error404.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResearchedProd();
            }
        });
        error404.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResearchedProd();
            }
        });

        articles_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (
                        scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                                (articles_list.getLastVisiblePosition() ) >= adapter.getCount()-1
                ){
                    Animation from_right = AnimationUtils.loadAnimation(context, R.anim.m_fromright);
                    Animation to_right = AnimationUtils.loadAnimation(context, R.anim.m_toleft);
                    progress_data.startAnimation(from_right);
                    progress_data.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress_data.startAnimation(to_right);
                            progress_data.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recherche_menu, menu);

        // todo setting up searchView from ActionBar
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.searchItem).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setQueryHint("Recherche Article");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SEARCH_INPUT = query;
                getResearchedProd();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        final MenuItem menuItem = menu.findItem(R.id.panier);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        } else {
            this.mTimer = new Timer();
        }
        this.mTimer.scheduleAtFixedRate(new TimeDisplay(getApplicationContext(), 0), 0, 1000);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    public class TimeDisplay extends TimerTask {
        Context context;
        int turn = 0;

        public TimeDisplay(Context context, int turn) {
            this.context = context;
            //this.turn = turn;
        }

        public void run() {
            mHandler.post(new Runnable() {
                public void run() {
                    ////Log.e("PANIER_SERVICES", "je tourne bien");
                    new MyServices().Check_Panier(context);
                    if (Preferences.getUserPreferences(context, Constants.PANIER_COUNT) == null || Preferences.getUserPreferences(context, Constants.PANIER_COUNT).equals("")){
                        Utils.Setting_badge("0",textCartItemCount);
                    }else Utils.Setting_badge(Preferences.getUserPreferences(context, Constants.PANIER_COUNT),textCartItemCount);
                }
            });
        }
    }

    public void Setting_badge(String count2) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.panier :
                Intent i  = new Intent(context, Panier.class);
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

    // TODO METHOD
    private void getResearchedProd(){
        //Log.i("PRODUCT_DATAS ",Config.GET_PRODUCTS);
        progress_data.setVisibility(View.VISIBLE);
        error404.setVisibility(View.GONE);
        DATAS.clear();
        AndroidNetworking
                .post(Config.GET_PRODUCT_SEARCH)
                .addBodyParameter("query", SEARCH_INPUT)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("results");
                            ////Log.e("PRODUCT_DATAS RR ", ar.toString());

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                p.setImages(jsonObject.getString(new Articles().images));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                p.setKeywords(jsonObject.getString(new Articles().keywords));
                                p.setUser_id(jsonObject.getString(new Articles().user_id));
                                p.setUser_name(jsonObject.getString(new Articles().user_name));
                                p.setEmail(jsonObject.getString(new Articles().email));
                                p.setCat_id(jsonObject.getString(new Articles().cat_id));
                                p.setCat_name(jsonObject.getString(new Articles().cat_name));
                                DATAS.add(p);

                            }

                            DATAS_CACHED.writeCache(DATAS);

                            if (DATAS.size()<1){
                                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                                error404.setVisibility(View.VISIBLE);
                            }else{
                                adapter = new Adaptor_recherche_list(context, DATAS);
                                articles_list.setAdapter(adapter);
                                error404.setVisibility(View.GONE);
                            }
                            progress_data.setVisibility(View.GONE);

                        } catch (Exception e) {
                            //Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                            //error404.setVisibility(View.VISIBLE);
                            LoadCache();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        //Log.e("PRODUCT_DATAS ",anError.getMessage());
                        //error404.setVisibility(View.VISIBLE);
                        LoadCache();
                    }
                });
    }

    private void Loadprod(){
        LoadCache();
        progress_data.setVisibility(View.VISIBLE);
        error404.setVisibility(View.GONE);
        DATAS.clear();
        AndroidNetworking
                .get(Config.GET_PRODUCTS)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("products");
                            //Log.e("PRODUCT_DATAS RR ", ar.toString());

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);
                                Articles p = new Articles();
                                p.setId(jsonObject.getString(new Articles().id));
                                p.setName(jsonObject.getString(new Articles().name));
                                p.setSlug(jsonObject.getString(new Articles().slug));
                                p.setImages(jsonObject.getString(new Articles().images));
                                p.setDescription(jsonObject.getString(new Articles().description));
                                p.setPrice(jsonObject.getString(new Articles().price));
                                p.setStock(jsonObject.getString(new Articles().stock));
                                p.setAvailability(jsonObject.getString(new Articles().availability));
                                DATAS.add(p);
                            }
                            DATAS_CACHED.writeCache(DATAS);
                            if (DATAS.size()<1){
                                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                                //error404.setVisibility(View.VISIBLE);
                            }else{
                                adapter = new Adaptor_recherche_list(context, DATAS);
                                articles_list.setAdapter(adapter);
                                //error404.setVisibility(View.GONE);
                            }
                            progress_data.setVisibility(View.GONE);

                        } catch (Exception e) {
                            //Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                            //error404.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        //Log.e("PRODUCT_DATAS ",anError.getMessage());
                        error404.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void LoadCache(){
        progress_data.setVisibility(View.VISIBLE);
        try {
            if (DATAS_CACHED.getSize()<1){
                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                error404.setVisibility(View.VISIBLE);
            }else{
                adapter = new Adaptor_recherche_list(context, DATAS_CACHED.readCache());
                articles_list.setAdapter(adapter);
                error404.setVisibility(View.GONE);
            }
            progress_data.setVisibility(View.GONE);
            ////Log.e("CACHED_DATA", String.valueOf(DATAS_CACHED.readCache().size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
