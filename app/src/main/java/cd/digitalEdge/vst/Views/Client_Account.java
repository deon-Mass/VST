package cd.digitalEdge.vst.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Add_client;
import cd.digitalEdge.vst.Views.Blanks.Add_vente;
import cd.digitalEdge.vst.Views.Lists.List_clients;

public class Client_Account extends AppCompatActivity {

    Context context = this;
    Button BTN_back,BTN_add_vente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("client_name"));

        INIT_COMPONENT();

    }

    private void INIT_COMPONENT(){
        BTN_back = findViewById(R.id.BTN_back);
        BTN_add_vente = findViewById(R.id.BTN_add_vente);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_add_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Add_vente.class);
                i.putExtra("client_name", getSupportActionBar().getTitle());
                startActivity(i);
                finish();
            }
        });
        BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, List_clients.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
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


}
