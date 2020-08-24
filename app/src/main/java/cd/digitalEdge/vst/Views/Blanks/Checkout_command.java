package cd.digitalEdge.vst.Views.Blanks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Lists.Panier;

public class Checkout_command extends AppCompatActivity {

    Context context = this;
    TextView BTN_btn, prodcount, PT;
    ScrollView page1, page2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_command);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        INIT_COMPONENT();
    }

    private void INIT_COMPONENT() {
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        BTN_btn = findViewById(R.id.BTN_btn);
        prodcount = findViewById(R.id.prodcount);
        PT = findViewById(R.id.PT);

        page2.setVisibility(View.GONE);
        BTN_btn.setText("Suivant");

        ArrayList<Articles> PANIER = new ArrayList<>();
        PANIER = Sqlite_selects_methods.getall_Articles(context);

        prodcount.setText(String.valueOf(PANIER.size()));
        int TOT = 0;
        for (Articles a :PANIER) {
            TOT += Float.parseFloat(a.getPrice()) * Float.parseFloat(a.getQnt());
        }
        PT.setText(String.valueOf(TOT)+" USD");


    }


    @Override
    protected void onResume() {
        super.onResume();
        BTN_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BTN_btn.getText().toString().equals("Suivant")){
                    BTN_btn.setText("Commander");
                    page1.setVisibility(View.GONE);
                    page2.setVisibility(View.VISIBLE);
                }else if (BTN_btn.getText().toString().equals("Commander")){
                    new AlertDialog.Builder(context)
                            .setTitle("Notice")
                            .setMessage("Vos données personnelles seront utilisées pour le traitement de votre commande, vous accompagner au cours de votre visite du site web, et pour d’autres raisons décrites dans notre politique de confidentialité.")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                 }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent ii  = new Intent(context, Panier.class);
        startActivity(ii);
        finish();
    }
}
