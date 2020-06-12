package cd.digitalEdge.vst.Views.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Online.Selects_queries;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Lists.List_clients;

public class Login extends AppCompatActivity {

    Context context = this;
    Button BTN_connection;
    ProgressBar logging_loading;
    EditText username,passeword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        INIT_COMPONENT();
    }

    private void INIT_COMPONENT() {
        BTN_connection = findViewById(R.id.BTN_connection);
        username = findViewById(R.id.username);
        passeword = findViewById(R.id.passeword);
        logging_loading = findViewById(R.id.logging_loading);

        logging_loading.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString();
                String pass = passeword.getText().toString();
                if (TextUtils.isEmpty(user_name)) {
                    username.setError("Veuiillez saisir votre username");
                } else if (TextUtils.isEmpty(pass)) {
                    passeword.setError("Veuiillez saisir le mot de passe");
                } else {
                    NetworkInfo info = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                    if (info == null || !info.isConnected()) {
                        Snackbar.make(v, (CharSequence) "Vous n'êtes pas connecté", 5000).show();
                    } else {
                        if (user_name.equals("deon") && pass.equals("123456") ) {
                            //auth(user_name, pass);
                            Intent i  = new Intent(context, MainActivity.class);
                            i.putExtra("logged", true);
                            startActivity(i);
                            finish();
                        }else{
                            Snackbar.make(v, (CharSequence) "Authentification incorrect", 5000).show();

                        }
                    }
                }


            }
        });
    }



    public void auth(String phone2, String passe2) {
        logging_loading.setVisibility(View.VISIBLE);
        String value = null;
        try {
            value = Selects_queries.auth(phone2, Tool.SHA1(passe2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Config.SERVER_PATH)
                .addBodyParameter("auth", Config.auth)
                .addBodyParameter("method", "1")
                .addBodyParameter("query", value)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    public void onResponse(JSONArray response) {
                        String str = "AN_ERROR1";
                        Log.e(str, response.toString());
                        String str2 = "Authentification";
                        if (response == null || response.length() < 1) {
                            Utils.MonToast(Login.this.context, str2, "Authentification incorrect", "Danger");
                            logging_loading.setVisibility(View.GONE);
                            return;
                        }
                        int i = 0;
                        while (i < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Users u = new Users();
                                Users columns = new Users();
                                u.setId(jsonObject.getString(columns.id));

                                Tool.userPreferences_Set(Login.this.context, u);
                                Intent intent = new Intent(Login.this.context, MainActivity.class);
                                Utils.MonToast(Login.this.context, str2, "Authentification correct", "Success");
                                Login.this.startActivity(intent);
                                Login.this.finish();
                                i++;
                            } catch (Exception e) {
                                Log.e(str, e.getMessage());
                            }
                        }
                    }
                    public void onError(ANError error) {
                        logging_loading.setVisibility(View.GONE);
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("AN_ERROR", error.getMessage());
                    }
                });
    }




}
