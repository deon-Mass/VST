package cd.digitalEdge.vst.Controllers.Background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_updates_methods;
import cd.digitalEdge.vst.Controllers.Online.Selects_queries;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Objects.Projects;

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
        this.mTimer.scheduleAtFixedRate(new TimeDisplay(getApplicationContext(), 0), 0, 300000);
        this.mTimer.scheduleAtFixedRate(new TimeDisplay(getApplicationContext(), 1), 0, 300000);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }


    class TimeDisplay extends TimerTask {
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
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = manager.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {
                        if (turn == 0)Load_Products(context);
                        else if (turn == 1)Load_Products(context);
                    }
                }
            });
        }
    }

    public void Load_Products(final Context context) {
        final ArrayList<Products> SYMP = new ArrayList<>();
        SYMP.clear();
        AndroidNetworking.post(Config.SERVER_PATH)
                .addBodyParameter("auth", Config.auth)
                .addBodyParameter("method", "1")
                .addBodyParameter("query", Selects_queries.get_Product())
                .setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            public void onResponse(JSONArray response) {
                int i = 0;
                if (response == null || response.length() < 1) {
                    Toast.makeText(context, "Aucune question", Toast.LENGTH_LONG).show();
                    return;
                }
                while (i < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Products m = new Products();
                        Products columns = new Products();
                        m.setId(jsonObject.getString(columns.id));
                        m.setTitle(jsonObject.getString(columns.title));
                        m.setMesure(jsonObject.getString(columns.mesure));
                        m.setPrice(jsonObject.getString(columns.price));
                        m.setPrice_editable(jsonObject.getString(columns.price_editable));
                        m.setPicture(jsonObject.getString(columns.picture));
                        m.setProject_id(jsonObject.getString(columns.project_id));
                        m.setProject_name(jsonObject.getString(columns.project_name));
                        m.setCreated_at(jsonObject.getString(columns.created_at));
                        m.setStatus(jsonObject.getString(columns.status));
                        SYMP.add(m);
                        i++;
                    } catch (Exception e) {
                    }
                }
                Iterator it = SYMP.iterator();
                while (it.hasNext()) {
                    Products s = (Products) it.next();
                    if (!Products.if_exist(context, s)) {
                        Sqlite_updates_methods.insert_PRODUCT(context, s);
                    }
                }
            }
            public void onError(ANError error) {
            }
        });
    }

    public void Load_Project(final Context context) {
        final ArrayList<Projects> SYMP = new ArrayList<>();
        SYMP.clear();
        AndroidNetworking.post(Config.SERVER_PATH)
                .addBodyParameter("auth", Config.auth)
                .addBodyParameter("method", "1")
                .addBodyParameter("query", Selects_queries.get_Product())
                .setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            public void onResponse(JSONArray response) {
                int i = 0;
                if (response == null || response.length() < 1) {
                    Toast.makeText(context, "Aucune question", Toast.LENGTH_LONG).show();
                    return;
                }
                while (i < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Projects m = new Projects();
                        Projects columns = new Projects();
                        m.setId(jsonObject.getString(columns.id));
                        m.setTitle(jsonObject.getString(columns.title));
                        m.setDescription(jsonObject.getString(columns.description));
                        m.setPicture(jsonObject.getString(columns.picture));
                        m.setOffice_id(jsonObject.getString(columns.office_id));
                        m.setCreated_at(jsonObject.getString(columns.created_at));
                        m.setStatus(jsonObject.getString(columns.status));
                        SYMP.add(m);
                        i++;
                    } catch (Exception e) {
                    }
                }
                Iterator it = SYMP.iterator();
                while (it.hasNext()) {
                    Projects s = (Projects) it.next();
                    if (!Projects.if_exist(context, s)) {
                        Sqlite_updates_methods.insert_PROJECT(context, s);
                    }
                }
            }
            public void onError(ANError error) {
            }
        });
    }
    
}
