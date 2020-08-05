package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Adaptors.Adaptor_Panier;
import cd.digitalEdge.vst.Controllers.Background.MyServices;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Utils;
import okhttp3.internal.Util;

public class Panier extends AppCompatActivity {
    Context context = this;
    ListView panier_list;
    SwipeRefreshLayout swiper;
    TextView textCartItemCount, PT,prodcount;


    Adaptor_Panier adapter;
    ArrayList<Articles> PANIER = new ArrayList<>();
    public Handler mHandler = new Handler();
    Timer mTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        getSupportActionBar().setTitle("Mon Panier");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        INIT_COMPONENTS();
        getdatas();
    }

    private void INIT_COMPONENTS() {
        panier_list = findViewById(R.id.panier_list);
        swiper = findViewById(R.id.swiper);
        prodcount = findViewById(R.id.prodcount);
        PT = findViewById(R.id.PT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.panier_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.facturation);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        } else {
            this.mTimer = new Timer();
        }
        this.mTimer.scheduleAtFixedRate(new TimeDisplay(getApplicationContext(), 0), 0, 100);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

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
            this.turn = turn;
        }

        public void run() {
            mHandler.post(new Runnable() {
                public void run() {
                    //Log.e("PANIER_SERVICES", "je tourne bien");
                    PANIER = Sqlite_selects_methods.getall_Articles(context);
                    if ( null == PANIER || PANIER.isEmpty() ){
                        PANIER = new ArrayList<>();
                        ////Log.e("DATA", "DATAS "+PANIER.size());
                        Utils.Setting_badge("0",textCartItemCount);
                    }else Utils.Setting_badge(String.valueOf(PANIER.size()),textCartItemCount);

                    prodcount.setText(String.valueOf(PANIER.size()));
                    int TOT = 0;
                    for (Articles a :PANIER) {
                        TOT += Integer.parseInt(a.getPrice()) * Integer.parseInt(a.getQnt());
                    }
                    PT.setText(String.valueOf(TOT)+" USD");
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            case R.id.facturation:
                Intent i  = new Intent(context, Panier.class);
                startActivity(i);
                finish();
                return true;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdatas();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //String source = getIntent().getExtras().getString("source");
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void getdatas(){
        swiper.setRefreshing(true);
        PANIER = Sqlite_selects_methods.getall_Articles(context);
        if ( null == PANIER || PANIER.isEmpty() ){
            PANIER = new ArrayList<>();
            Log.e("DATA", "DATAS "+PANIER.size());
        }else{
            adapter = new Adaptor_Panier(context, PANIER);
            //adapter.refreshEvents(PANIER);
            panier_list.setAdapter(adapter);
            swiper.setRefreshing(false);

        }
    }

}