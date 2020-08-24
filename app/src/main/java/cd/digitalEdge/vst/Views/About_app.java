package cd.digitalEdge.vst.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Contacts;
import cd.digitalEdge.vst.Views.Diapo.IntroActivity;

public class About_app extends AppCompatActivity {
    Context context = this;
    TextView back,tutorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getSupportActionBar().hide();

        back = findViewById(R.id.back);
        tutorial = findViewById(R.id.tutorial);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, IntroActivity.class);
                startActivity(i);
                finish();            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Paramettres.class);
        startActivity(i);
        finish();
    }
}
