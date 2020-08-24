package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import cd.digitalEdge.vst.Adaptors.Adaptor_favoris_list;
import cd.digitalEdge.vst.Adaptors.Adaptor_message_list;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Add_product;
import cd.digitalEdge.vst.Views.Gerer;

public class Messages extends AppCompatActivity {

    Context context = this;
    Adaptor_message_list adaptor;
    ListView list_messages;
    LinearLayout progress_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        getSupportActionBar().setTitle("Mes messages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        INIT_COMPONENT();
        getData();
    }


    private void INIT_COMPONENT() {
        progress_data = findViewById(R.id.progress_data);
        list_messages = findViewById(R.id.list_messages);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.biens_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_product:
                Intent i  = new Intent(context, Add_product.class);
                startActivity(i);
                finish();
                break;
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
        }
        return true;
    }

    void getData(){
        adaptor = new Adaptor_message_list(context);
        list_messages.setAdapter(adaptor);
    }




}
