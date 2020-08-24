package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cd.digitalEdge.vst.Adaptors.Adaptor_Chat;
import cd.digitalEdge.vst.Adaptors.Adaptor_message_list;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Add_product;
import cd.digitalEdge.vst.Views.Gerer;

public class Chats extends AppCompatActivity {

    Context context = this;
    RecyclerView list;
    Adaptor_Chat adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        getSupportActionBar().setTitle("Deon mass");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        INIT_COMPONENT();
        getData();
    }


    private void INIT_COMPONENT() {
        list = findViewById(R.id.list);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Messages.class);
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
                break;
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
        }
        return true;
    }

    void getData(){
        adaptor = new Adaptor_Chat(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        layoutManager.setStackFromEnd(true);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adaptor);
    }



}
