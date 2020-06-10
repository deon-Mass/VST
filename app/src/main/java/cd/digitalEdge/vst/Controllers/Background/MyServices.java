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


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

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
        /*this.mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, 300000);
        this.mTimer.scheduleAtFixedRate(new TimeDisplay_DISCUSSION(), 0, 5000);
        this.mTimer.scheduleAtFixedRate(new TimeDisplayURG(), 0, 300000);
        this.mTimer.scheduleAtFixedRate(new TimeDisplay_PHARMA(), 0, 300000);*/
    }

    public void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    /*
    public void Load_questions(final Context context) {
        final ArrayList<Symptoms> SYMP = new ArrayList<>();
        SYMP.clear();
        AndroidNetworking.post(Config.SERVER_PATH).addBodyParameter("auth", Config.auth)
                .addBodyParameter("method", "1")
                .addBodyParameter("query", Selects_queries.get_Symptomes())
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
                        Symptoms m = new Symptoms();
                        Symptoms columns = new Symptoms();
                        m.setId(jsonObject.getString(columns.id));
                        m.setTitle(jsonObject.getString(columns.title));
                        m.setDescription(jsonObject.getString(columns.description));
                        m.setIllness_ids(jsonObject.getString(columns.illness_ids));
                        m.setCreated_at(jsonObject.getString(columns.created_at));
                        m.setStatus(jsonObject.getString(columns.status));
                        SYMP.add(m);
                        i++;
                    } catch (Exception e) {
                    }
                }
                Iterator it = SYMP.iterator();
                while (it.hasNext()) {
                    Symptoms s = (Symptoms) it.next();
                    if (!Symptoms.if_exist(context, s)) {
                        Symptoms.insert(context, s);
                    }
                }
            }

            public void onError(ANError error) {
            }
        });
    }


    public void Load_illness_questions(final Context context) {
        final ArrayList<Illness_questions> DATAS = new ArrayList<>();
        String value = Selects_queries.get_Illness_sqtions();
        DATAS.clear();
        AndroidNetworking.post(Config.SERVER_PATH).addBodyParameter("auth", Config.auth).addBodyParameter("method", "1").addBodyParameter("query", value).setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            public void onResponse(JSONArray response) {
                int i = 0;
                if (response == null || response.length() < 1) {
                    Toast.makeText(context, "Aucune question", Toast.LENGTH_LONG).show();
                    return;
                }
                while (i < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Illness_questions m = new Illness_questions();
                        m.setId(jsonObject.getString(new Illness_questions().id));
                        m.setContent(jsonObject.getString(new Illness_questions().content));
                        m.setIllness_ids(jsonObject.getString(new Illness_questions().illness_ids));
                        m.setGender(jsonObject.getString(new Illness_questions().gender));
                        m.setCategory_age(jsonObject.getString(new Illness_questions().category_age));
                        m.setStatus(jsonObject.getString(new Illness_questions().status));
                        DATAS.add(m);
                        i++;
                    } catch (Exception e) {
                    }
                }
                Iterator it = DATAS.iterator();
                while (it.hasNext()) {
                    Illness_questions s = (Illness_questions) it.next();
                    if (!Illness_questions.if_exist(context, s)) {
                        Illness_questions.insert(context, s);
                    }
                }
            }

            public void onError(ANError error) {
            }
        });
    }


    public void Load_pharma(final Context context) {
        final ArrayList<Pharma> PHARMA = new ArrayList<>();
        String value = Selects_queries.get_pharmacie();
        PHARMA.clear();
        AndroidNetworking.post(Config.SERVER_PATH)
                .addBodyParameter("auth", Config.auth)
                .addBodyParameter("method", "1")
                .addBodyParameter("query", value)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    public void onResponse(JSONArray response) {
                        if (response != null && response.length() >= 1) {
                            int i = 0;
                            while (i < response.length()) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Pharma u = new Pharma();
                                    Pharma columns = new Pharma();
                                    u.setId(jsonObject.getString(columns.id));
                                    u.setTitle(jsonObject.getString(columns.title));
                                    u.setStreet(jsonObject.getString(columns.street));
                                    u.setCommune(jsonObject.getString(columns.commune));
                                    u.setCity(jsonObject.getString(columns.city));
                                    u.setPhone(jsonObject.getString(columns.phone));
                                    u.setEmail(jsonObject.getString(columns.email));
                                    u.setResponsable(jsonObject.getString(columns.responsable));
                                    u.setLatlng(jsonObject.getString(columns.latlng));
                                    u.setPicture(jsonObject.getString(columns.picture));
                                    u.setSlug(jsonObject.getString(columns.slug));
                                    u.setCreated_at(jsonObject.getString(columns.created_at));
                                    u.setCreated_by(jsonObject.getString(columns.created_by));
                                    u.setUpdating(jsonObject.getString(columns.updating));
                                    u.setStatus(jsonObject.getString(columns.status));
                                    PHARMA.add(u);
                                    i++;
                                } catch (Exception e) {
                                    //Log.e("PHARMA_LOG_ERR", e.getMessage());
                                }
                            }
                            Iterator it = PHARMA.iterator();
                            while (it.hasNext()) {
                                Pharma s = (Pharma) it.next();
                                if (!Pharma.if_exist(context, s)) {
                                    Pharma.insert(context, s);
                                }
                            }
                        }
                    }

                    public void onError(ANError error) {//Log.e("PHARMA_LOG_ERR2", error.getMessage());
                    }
        });
    }


    public void Load_Chat_list(final Context context) {
        final ArrayList<Chat_discussions> DISCUSSIONS = new ArrayList<>();
        String value = Selects_queries.get_discussion_list(Tool.getUserPreferences(context, new Users().id));
        StringBuilder sb = new StringBuilder();
        sb.append("querie ");
        sb.append(value);
        Log.e("DISCUSS_LIST", sb.toString());
        DISCUSSIONS.clear();
        AndroidNetworking.post(Config.SERVER_PATH).addBodyParameter("auth", Config.auth).addBodyParameter("method", "1").addBodyParameter("query", value).setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            public void onResponse(JSONArray response) {
                if (response != null) {
                    response.length();
                }
                int i = 0;
                while (i < response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Chat_discussions u = new Chat_discussions();
                        u.setId(jsonObject.getString(new Chat_discussions().id));
                        u.setMedecin_id(jsonObject.getString(new Chat_discussions().medecin_id));
                        u.setMedecin_firstnam(jsonObject.getString(new Chat_discussions().medecin_firstnam));
                        u.setMedecin_name(jsonObject.getString(new Chat_discussions().medecin_name));
                        u.setMedecin_middlename_ag(jsonObject.getString(new Chat_discussions().medecin_id));
                        u.setAvatar(jsonObject.getString(new Chat_discussions().avatar));
                        u.setMessage(jsonObject.getString(new Chat_discussions().message));
                        u.setCr(jsonObject.getString(new Chat_discussions().cr));
                        DISCUSSIONS.add(u);
                        i++;
                    } catch (Exception e) {
                    }
                }
                if (ExecuteUpdate.Truncat(context, new Chat_discussions().table_name) >= 1) {
                    Iterator it = DISCUSSIONS.iterator();
                    while (it.hasNext()) {
                        Chat_discussions.insert(context, (Chat_discussions) it.next());
                    }
                }
            }

            public void onError(ANError error) {
            }
        });
    }


    public void read_messages(final Context context) {
        final ArrayList<Chats> DATAS = new ArrayList<>();
        DATAS.clear();
        AndroidNetworking.post(Config.SERVER_PATH).addBodyParameter("auth", Config.auth).addBodyParameter("method", "1").addBodyParameter("query", Selects_queries.get_My_discussions(Tool.getUserPreferences(context, new Users().id))).setPriority(Priority.HIGH).build().getAsJSONArray(new JSONArrayRequestListener() {
            public void onResponse(JSONArray response) {
                if (response != null && response.length() >= 1) {
                    int i = 0;
                    while (i < response.length()) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Chats u = new Chats();
                            Chats columns = new Chats();
                            u.setId(jsonObject.getString(columns.id));
                            u.setFrom_id(jsonObject.getString(columns.from_id));
                            u.setTo_id(jsonObject.getString(columns.to_id));
                            u.setComplaint_id(jsonObject.getString(columns.complaint_id));
                            u.setMessage(jsonObject.getString(columns.message));
                            u.setNotified(jsonObject.getString(columns.notified));
                            u.setRead_at(jsonObject.getString(columns.read_at));
                            u.setRead_status(jsonObject.getString(columns.read_status));
                            u.setStatus(jsonObject.getString(columns.status));
                            u.setCreated_at(jsonObject.getString(columns.created_at));
                            DATAS.add(u);
                            i++;
                        } catch (Exception e) {
                        }
                    }
                    Iterator it = DATAS.iterator();
                    while (it.hasNext()) {
                        Chats s = (Chats) it.next();
                        if (!Chats.if_exist_id(context, s.getId())) {
                            Chats.insert(context, s);
                        }
                    }
                }
            }

            public void onError(ANError error) {
            }
        });
    }




    class TimeDisplay extends TimerTask {
        TimeDisplay() {
        }

        public void run() {
            MyServices.this.mHandler.post(new Runnable() {
                public void run() {
                    ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = manager.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {
                        MyServices.this.Load_questions(MyServices.this.getApplicationContext());
                    }
                }
            });
        }
    }

    class TimeDisplayURG extends TimerTask {
        TimeDisplayURG() {
        }

        public void run() {
            MyServices.this.mHandler.post(new Runnable() {
                public void run() {
                    ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = manager.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {
                        MyServices.this.Load_illness_questions(MyServices.this.getApplicationContext());
                    }
                }
            });
        }
    }

    class TimeDisplay_CHATS extends TimerTask {
        TimeDisplay_CHATS() {
        }

        public void run() {
            MyServices.this.mHandler.post(new Runnable() {
                public void run() {
                    MyServices.this.read_messages(MyServices.this.getApplicationContext());
                }
            });
        }
    }

    class TimeDisplay_DISCUSSION extends TimerTask {
        TimeDisplay_DISCUSSION() {
        }

        public void run() {
            MyServices.this.mHandler.post(new Runnable() {
                public void run() {
                    MyServices.this.Load_Chat_list(MyServices.this.getApplicationContext());
                }
            });
        }
    }

    class TimeDisplay_PHARMA extends TimerTask {
        TimeDisplay_PHARMA() {
            //Log.i("PHARMA_LOG_ERR", "je suis bien dans le timer pharma");
        }
        public void run() {
            MyServices.this.mHandler.post(new Runnable() {
                public void run() {
                    Load_pharma(MyServices.this.getApplicationContext());
                }
            });
        }
    }


     */
}
