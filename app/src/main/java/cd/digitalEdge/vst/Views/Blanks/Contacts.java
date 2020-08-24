package cd.digitalEdge.vst.Views.Blanks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;

public class Contacts extends AppCompatActivity {

    Context context = this;
    Users currentUser;

    TextView nom,email,sujet,message,BTN_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().setTitle("Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentUser = Preferences.getCurrentUser(context);

        INIT_COMPONENT();
    }

    void INIT_COMPONENT(){
        nom = findViewById(R.id.nom);
        email = findViewById(R.id.email);
        sujet = findViewById(R.id.sujet);
        message = findViewById(R.id.message);
        BTN_send = findViewById(R.id.BTN_send);

        if (currentUser != null){
            nom.setText(currentUser.getName());
            email.setText(currentUser.getEmail());
            nom .setEnabled(false);
            email .setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckZone() == false) return;
                ProgressDialog p = new ProgressDialog(context);
                p.setTitle("Contact");
                p.setMessage("Envoi en cours...");
                p.show();
                //Log.e("TAG_CONTACT", Config.SEND_IN_CONTACT);
                AndroidNetworking
                        .post(Config.SEND_IN_CONTACT)
                        .addBodyParameter("name", nom.getText().toString())
                        .addBodyParameter("email", email.getText().toString())
                        .addBodyParameter("subject", sujet.getText().toString())
                        .addBodyParameter("content", message.getText().toString())
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                //Log.i("TAG_CONTACT", response);
                                if (response.contains("true")){
                                    Toast.makeText(context, "Message envoyé", Toast.LENGTH_SHORT).show();
                                    sujet.setText("");
                                    message.setText("");

                                }else if (!response.contains("true")){
                                    Toast.makeText(context, "Le message n'a pas pu être envoyé", Toast.LENGTH_SHORT).show();

                                }
                                p.dismiss();
                            }
                            @Override
                            public void onError(ANError anError) {
                                //Log.e("TAG_CONTACT", anError.getMessage());
                                //Tool.Dialog(context, "",anError.getMessage());
                                p.dismiss();
                            }
                        });
            }
        });
    }


    private boolean CheckZone(){
        if (nom.getText().toString().length() < 3){
            nom.setError("Champs nom doit contenir plus de 3 caractères");
            return false;
        }else if (!email.getText().toString().contains("@")){
            email.setError("Veuillez insérer  une addresse mail valide");
            return false;
        }else if (email.getText().toString().length() < 3){
            email.setError("Champs nom doit contenir plus de 3 caractères");
            return false;
        }else if (sujet.getText().toString().length() < 3){
            sujet.setError("Champs nom doit contenir plus de 3 caractères");
            return false;
        }else if (message.getText().toString().length() < 3){
            message.setError("Champs nom doit contenir plus de 3 caractères");
            return false;
        }else if (TextUtils.isEmpty(nom.getText().toString())){
            nom.setError("Champs obligatoire");
            return false;
        }else if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Champs obligatoire");
            return false;
        }else if (TextUtils.isEmpty(sujet.getText().toString())){
            sujet.setError("Champs obligatoire");
            return false;
        }else if (TextUtils.isEmpty(message.getText().toString())){
            message.setError("Champs obligatoire");
            return false;
        }else return true;
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

    @Override
    public void onBackPressed() {
        //String source = getIntent().getExtras().getString("source");
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

}
