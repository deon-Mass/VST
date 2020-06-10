package cd.digitalEdge.vst.Views.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Lists.List_clients;

public class Login extends AppCompatActivity {

    Context context = this;
    Button BTN_connection;
    ProgressBar logging_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        INIT_COMPONENT();
    }

    private void INIT_COMPONENT() {
        BTN_connection = findViewById(R.id.BTN_connection);
        logging_loading = findViewById(R.id.logging_loading);


    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }



}
