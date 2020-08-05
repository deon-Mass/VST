package cd.digitalEdge.vst.Views.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.digitalEdge.vst.Adaptors.Adaptor_Categorie;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Tools.Utils;

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
                        auth(user_name, pass, v);
                    }
                }


            }
        });
    }

    public void auth(String email, String passe, View v) {
        logging_loading.setVisibility(View.VISIBLE);
        AndroidNetworking
                .post(Config.GET_USER)
                .addBodyParameter("email", email)
                .addBodyParameter("password", passe)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("PRODUCT_DATAS---- ", response.toString());
                            JSONObject jsonObject = response.getJSONObject("user");
                            Users u = new Users();
                            u.setId(jsonObject.getString(new Users().id));
                            u.setName(jsonObject.getString(new Users().name));
                            u.setEmail(jsonObject.getString(new Users().email));
                            u.setAvatar(jsonObject.getString(new Users().avatar));
                            u.setRole_id(jsonObject.getString(new Users().role_id));

                            Preferences.SaveCurrentUser(context, u);
                            if (
                                    Preferences.getUserPreferences(context, Config_preferences.CURRENT_USER).equals("null") ||
                                    Preferences.getUserPreferences(context, Config_preferences.CURRENT_USER) == null
                            ){
                                Toast.makeText(context, "CURRENT USER NULL", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent i  = new Intent(context, MainActivity.class);
                            i.putExtra("logged", true);
                            startActivity(i);
                            finish();

                        } catch (JSONException e) {
                            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        logging_loading.setVisibility(View.GONE);
                        Log.e("PRODUCT_DATAS ",anError.getMessage());
                        Snackbar.make(v, (CharSequence) "Authentification incorrect", 5000).show();
                    }
                });
    }

}
