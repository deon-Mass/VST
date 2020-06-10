package cd.digitalEdge.vst.Views.Blanks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Lists.List_clients;

public class Add_client extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        INIT_COMPONENT();
    }

    private void INIT_COMPONENT(){

    }

    @Override
    protected void onResume() {
        super.onResume();
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
