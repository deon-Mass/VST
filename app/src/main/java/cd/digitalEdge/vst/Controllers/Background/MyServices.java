package cd.digitalEdge.vst.Controllers.Background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Adaptors.Adaptor_Categorie;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_updates_methods;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;

public class MyServices extends Service {
    public static final int discussion_time = 5000;
    public static final int notify = 300000;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    int count = 0;
    private Timer mTimer = null;


    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        } else {
            this.mTimer = new Timer();
        }
        this.mTimer.scheduleAtFixedRate(new TimeDisplay(getApplicationContext(), 0), 0, 3000);
        this.mTimer.scheduleAtFixedRate(new TimeDisplay_CAT(getApplicationContext(), 0), 0, 3000);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
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
                    Log.e("MYSERVICE", "je tourne bien");
                    Check_Panier(context);
                }
            });
        }
    }
    public class TimeDisplay_CAT extends TimerTask {
        Context context;
        int turn = 0;

        public TimeDisplay_CAT(Context context, int turn) {
            this.context = context;
            this.turn = turn;
        }

        public void run() {
            mHandler.post(new Runnable() {
                public void run() {
                    Log.e("MYSERVICE_CAT", "je tourne bien");
                    Load_cat(context);
                }
            });
        }
    }

    public void Check_Panier(final Context context) {
        ArrayList<Articles> PANIER = new ArrayList<>();
        PANIER = Sqlite_selects_methods.getall_Articles(context);
        if ( null == PANIER || PANIER.isEmpty() ){
            PANIER = new ArrayList<>();
            //Log.e("DATA", "DATAS "+PANIER.size());
        }
        int count = PANIER.size();
        Preferences.setUserPreferences(context, Constants.PANIER_COUNT, String.valueOf(count));
    }


    public void Load_cat(final Context context) {
        final ArrayList<Categories> DATAS_CAT    = new ArrayList<>();
        DATAS_CAT.clear();
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
                                DATAS_CAT.add(p);
                            }


                            Iterator it = DATAS_CAT.iterator();
                            while (it.hasNext()) {
                                Categories s = (Categories) it.next();
                                if(Sqlite_updates_methods.insert_CATEGORIE(context, s) == 1){
                                    Log.e("CATEGORIE_INSERT","INSEERTED");
                                }else if(Sqlite_updates_methods.insert_CATEGORIE(context, s) == 0){
                                    Log.e("CATEGORIE_INSERT","NOT INSEERTED");
                                }
                            }

                        } catch (JSONException e) {
                            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("PRODUCT_DATAS ",anError.getMessage());
                    }
                });
    }

}
