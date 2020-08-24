package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kosalgeek.android.caching.FileCacher;

import java.util.ArrayList;

import cd.digitalEdge.vst.Adaptors.Adaptor_favoris_list;
import cd.digitalEdge.vst.Adaptors.Adaptor_wallet1_list;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Views.Gerer;

public class Wallet extends AppCompatActivity {

    Context context = this;
    Adaptor_wallet1_list adaptor;
    ListView list_gain, list_paiement;
    LinearLayout progress_data;

    ArrayList<Articles> DATA_WALLET = new ArrayList<>();
    FileCacher<ArrayList<Articles>> DATAS_CACHED = new FileCacher<>(context, Constants.FILE_WALLET);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        getSupportActionBar().setTitle("Portefeuilles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        INIT_COMPONENT();
        getData();
    }

    private void INIT_COMPONENT() {
        progress_data = findViewById(R.id.progress_data);
        list_paiement = findViewById(R.id.list_paiement);
        list_gain = findViewById(R.id.list_gain);

        progress_data.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Gerer.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    // TODO METHOD
    void getData(){
        adaptor = new Adaptor_wallet1_list(context);
        list_gain.setAdapter(adaptor);
        list_paiement.setAdapter(adaptor);
    }

}
