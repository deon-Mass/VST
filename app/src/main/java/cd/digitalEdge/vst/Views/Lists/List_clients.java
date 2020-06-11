package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cd.digitalEdge.vst.Adaptors.Adaptor_Client_list;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Customers;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Add_client;
import cd.digitalEdge.vst.Views.Client_Account;

public class List_clients extends AppCompatActivity {

    Context context = this;
    Adaptor_Client_list adapter;
    ListView clients_list;
    SwipeRefreshLayout swiper;


    ArrayList<Customers> DATAS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clients);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Base de Clients");

        INIT_COMPONENT();
        for (int i = 0; i < 10; i++) {
            Customers c = new Customers();
            c.setName("Customer "+i);
            c.setAddress("Adresse "+i);
            c.setEmail("gedeonmass15@gmail.com "+i);
            c.setPhone("+243814511083 ");
            DATAS.add(c);
        }
        gettDatas();
    }

    private void INIT_COMPONENT() {
        clients_list = findViewById(R.id.clients_list);
        swiper = findViewById(R.id.swiper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gettDatas();
            }
        });

        clients_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customers c = DATAS.get(position);
                Intent i  = new Intent(context, Client_Account.class);
                i.putExtra("client_name", c.getName());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.list_client_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.refresh :

                break;
            case R.id.add_client :
                Intent i  = new Intent(context, Add_client.class);
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



    // TODO METHOD
    private void gettDatas(){
        swiper.setRefreshing(true);
        adapter = new Adaptor_Client_list(context, DATAS);
        clients_list.setAdapter(adapter);
        swiper.setRefreshing(false);
    }

}
