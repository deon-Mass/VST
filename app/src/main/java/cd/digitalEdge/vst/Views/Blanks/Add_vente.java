package cd.digitalEdge.vst.Views.Blanks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Views.Gerer;

public class Add_vente extends AppCompatActivity {

    Context context = this;
    EditText date_vente, Edit_prix,Edit_qnt;
    Spinner spinner_client, spinner_product;
    Button BTN_add;
    TextView BTN_product_added;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nouvelle vente");

        INIT_COMPONENT();
    }


    private void INIT_COMPONENT(){
        date_vente = findViewById(R.id.date_vente);
        BTN_product_added = findViewById(R.id.BTN_product_added);
        Edit_prix = findViewById(R.id.Edit_prix);
        Edit_qnt = findViewById(R.id.Edit_qnt);
        spinner_client = findViewById(R.id.spinner_client);
        spinner_product = findViewById(R.id.spinner_product);
        BTN_add = findViewById(R.id.BTN_add);
    }
    @Override
    protected void onResume() {
        super.onResume();
        date_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tool.Date_Picker(context, date_vente);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Gerer.class);
        i.putExtra("client_name", getIntent().getExtras().getString("client_name"));
        startActivity(i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_client_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_client_done :

                break;
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                break;
        }
        return true;
    }

}
