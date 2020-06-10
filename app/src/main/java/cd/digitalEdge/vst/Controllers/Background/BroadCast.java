package cd.digitalEdge.vst.Controllers.Background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.util.Iterator;

public class BroadCast extends BroadcastReceiver {
    //FirebaseHandler FH;
    String MYID;
    Context context;
    int count = 0;
    //FirebaseFirestore db;
    //DatabaseReference ref;
    //private Socket socket;

    public void onReceive(Context context2, Intent intent) {
        Log.d("BroadCAST RECEVER", " je suis bien la");
        //firebaseservices(context2);
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && !info.isConnected()) {
        }
    }

    /*private void repetingHandler() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info != null) {
                    info.isConnected();
                }
                handler.postDelayed(this, 1000);
            }
        }, 5000);
    }

    private void get_response_from_tbl_enquete(Context context2) {
        new Selects_queries();
        String value = Selects_queries.get_unnotified_msg(Tool.getUserPreferences(context2, new Users().id));
        StringBuilder sb = new StringBuilder();
        sb.append("querie XXXXXXXX ");
        sb.append(value);
        Log.e("myTagBROAD", sb.toString());
        AndroidNetworking.post(Config.SERVER_PATH).addBodyParameter("auth", Config.auth)
                .addBodyParameter("method", "1")
                .addBodyParameter("query", value).
                setPriority(Priority.HIGH).build()
                .getAsJSONArray(new JSONArrayRequestListener() {
            public void onResponse(JSONArray response) {
                Log.e("myTagBROAD", response.toString());
                try {
                    Chats u = new Chats();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        u.setId(jsonObject.getString("chat_id"));
                        u.setTo_id(jsonObject.getString("to_id"));
                        u.setFrom_id(jsonObject.getString("from_id"));
                        u.setMessage(jsonObject.getString("message"));
                        u.setNotified(jsonObject.getString("notified"));
                    }
                } catch (Exception e) {
                    Log.e("AN_ERROR", e.getMessage());
                }
            }

            public void onError(ANError error) {
                String str = "AN_ERROR";
                Log.e(str, error.getErrorBody());
                Log.e(str, error.getErrorDetail());
            }
        });
    }

    public void set_chat_Notified(Context context2, String chat_id) {
        String o = new Updates().APPLY_UPDATE(context2, Updates_queries.set_Notified(chat_id), null);
        if (o.length() <= 5 && !o.equals("-1")) {
            o.contains("<");
        }
    }

    private void sockecktservices(final Context context2) {
        try {
            this.socket = IO.socket("http://192.168.137.1:3000/");
            this.socket.connect();
            this.socket.on("message", new Listener() {
                public void call(Object... args) {
                    try {
                        Chats c = (Chats) new Gson().fromJson(String.valueOf(args[0].get("message")), Chats.class);
                        if (c.getTo_id().equals(Tool.getUserPreferences(context2, new Users().id))) {
                            Intent i = new Intent(context2, MainActivity.class);
                            i.putExtra("TAB", ExifInterface.GPS_MEASUREMENT_3D);
                            Tool.Notify(context2, i, "Vous avez un nouveau message");
                        }
                        if ((c.getTo_id().equals(Tool.getUserPreferences(context2, new Users().id)) || c.getFrom_id().equals(Tool.getUserPreferences(context2, new Users().id))) && Chats.insert(context2, c) >= 1) {
                            Toast.makeText(context2, "inserted", 0).show();
                        }
                    } catch (Exception e) {
                        Log.e("ARGMENTS", e.getMessage());
                    }
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void firebaseservices(final Context context2) {
        this.FH = new FirebaseHandler(context2, Config_preferences.table_name);
        this.db = this.FH.getRef_Chatfirestore();
        this.MYID = Tool.getUserPreferences(context2, new Users().id);
        this.db.collection(Config_preferences.table_name).whereEqualTo(new Chats().to_id, (Object) this.MYID).addSnapshotListener(MetadataChanges.INCLUDE, (EventListener<QuerySnapshot>) new EventListener<QuerySnapshot>() {
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    Log.e("SNAPSHOT_ERR", e.getMessage());
                    return;
                }
                Iterator it = queryDocumentSnapshots.iterator();
                while (it.hasNext()) {
                    QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                    if (((Chats) document.toObject(Chats.class)).getNotified().equals("0")) {
                        BroadCast.this.count++;
                        BroadCast.this.db.collection(Config_preferences.table_name).document(document.getId()).update(new Chats().notified, (Object) "1", new Object[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                            public void onSuccess(Void aVoid) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception e) {
                            }
                        });
                    }
                }
                if (BroadCast.this.count > 0) {
                    Intent i = new Intent(context2, MainActivity.class);
                    i.putExtra("TAB", ExifInterface.GPS_MEASUREMENT_3D);
                    Tool.Notify(context2, i, "Vous a envoyé un message");
                    BroadCast.this.count = 0;
                }
            }
        });
    }*/
}
