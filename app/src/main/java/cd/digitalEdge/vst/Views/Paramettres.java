package cd.digitalEdge.vst.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Offline.ExecuteUpdate;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Views.Blanks.Contacts;

public class Paramettres extends AppCompatActivity {

    Context context = this;
    TextView clearCache,clearPanier, contact_us,about,profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramettres);
        getSupportActionBar().setTitle("Paramettres");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        INIT_COMPONENT();
    }

    private void INIT_COMPONENT() {
        clearCache = findViewById(R.id.clearCache);
        clearPanier = findViewById(R.id.clearPanier);
        contact_us = findViewById(R.id.contact_us);
        about = findViewById(R.id.about);
        profil = findViewById(R.id.profil);
    }


    @Override
    protected void onResume() {
        super.onResume();
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileCacher<ArrayList<Articles>> data = new FileCacher<>(context, Constants.FILE_PRODUCTS);
                try {
                    if (data.clearCache() == true) Toast.makeText(context, "Cache vidé", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(context, "Cache non vidé", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clearPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExecuteUpdate.Truncat(context, Constants.ARTICLE) == 1) Toast.makeText(context, "Panier vidé", Toast.LENGTH_SHORT).show();
                else Toast.makeText(context, "Panier Non vidé", Toast.LENGTH_SHORT).show();
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Contacts.class);
                startActivity(i);
                finish();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, About_app.class);
                startActivity(i);
                finish();
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Gerer.class);
                startActivity(i);
                finish();
            }
        });
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
