package cd.digitalEdge.vst.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.R;

public class Paramettres extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramettres);
        getSupportActionBar().setTitle("Paramettres");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
