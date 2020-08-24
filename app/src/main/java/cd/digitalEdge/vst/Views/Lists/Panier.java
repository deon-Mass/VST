package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Adaptors.Adaptor_Panier;
import cd.digitalEdge.vst.Controllers.Offline.ExecuteUpdate;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Blanks.Checkout_command;

public class Panier extends AppCompatActivity {
    Context context = this;
    ListView panier_list;
    TextView textCartItemCount, PT,prodcount;
    LinearLayout progress_data;




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
        prodcount = findViewById(R.id.prodcount);
        progress_data = findViewById(R.id.progress_data);
        PT = findViewById(R.id.PT);

        progress_data.setVisibility(View.GONE);
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
                        TOT += Float.parseFloat(a.getPrice()) * Float.parseFloat(a.getQnt());
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
                break;
            case R.id.facturation:
                Intent i  = new Intent(context, Panier.class);
                startActivity(i);
                finish();
                break;
            case R.id.commander:
                getdatas();
                if (PANIER.size() < 1 || PANIER.isEmpty()){
                    Toast.makeText(context, "Le panier est vide, veuillez ajouter des aricles dans le papnier ", Toast.LENGTH_LONG).show();
                }else{
                    Intent ii  = new Intent(context, Checkout_command.class);
                    startActivity(ii);
                    finish();
                }

                break;
            case R.id.refresh:
                getdatas();
                break;
            case R.id.deleteAll:
                if (ExecuteUpdate.Truncat(context, Constants.ARTICLE) == 1) Toast.makeText(context, "Panier vidé", Toast.LENGTH_SHORT).show();
                else Toast.makeText(context, "Panier Non vidé", Toast.LENGTH_SHORT).show();
                getdatas();
                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        //String source = getIntent().getExtras().getString("source");
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void getdatas(){
        Animation from_right = AnimationUtils.loadAnimation(context, R.anim.m_fromright);
        Animation to_right = AnimationUtils.loadAnimation(context, R.anim.m_toleft);
        progress_data.startAnimation(from_right);
        progress_data.setVisibility(View.VISIBLE);
        PANIER = Sqlite_selects_methods.getall_Articles(context);
        if ( null == PANIER || PANIER.isEmpty() ){
            PANIER = new ArrayList<>();
            Log.e("DATA", "DATAS "+PANIER.size());
        }
        adapter = new Adaptor_Panier(context, PANIER);
        panier_list.setAdapter(adapter);
        progress_data.startAnimation(to_right);
        progress_data.setVisibility(View.GONE);
    }

}