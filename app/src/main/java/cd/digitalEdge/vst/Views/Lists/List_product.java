package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import cd.digitalEdge.vst.Adaptors.Adaptor_Client_list;
import cd.digitalEdge.vst.Adaptors.Adaptor_product_list;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Customers;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Add_client;
import cd.digitalEdge.vst.Views.Client_Account;

public class List_product extends AppCompatActivity {
    Context context = this;
    Adaptor_product_list adapter;

    ListView product_list;
    SwipeRefreshLayout swiper;
    int turn = 0;


    ArrayList<Products> DATAS = new ArrayList<>();
    ArrayList<Products> DATAS_SEARCHED = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Base de Produits");

        INIT_COMPONENT();
        gettDatas();
    }

    private void INIT_COMPONENT() {
        product_list = findViewById(R.id.product_list);
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

        product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Products c;
                if (turn == 0) c = DATAS.get(position);
                else c = DATAS_SEARCHED.get(position);
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

        // todo setting up searchView from ActionBar
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.searchItem).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setQueryHint("Recherche produit");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 1){
                    adapter = new Adaptor_product_list(context, DATAS);
                    product_list.setAdapter(adapter);
                    turn = 0;
                    return true;
                }
                DATAS_SEARCHED.clear();
                for (Products c : DATAS) {
                    if (c.getTitle().contains(newText) ) DATAS_SEARCHED.add(c);
                }
                adapter = new Adaptor_product_list(context, DATAS_SEARCHED);
                product_list.setAdapter(adapter);
                turn = 1;
                return false;
            }
        });
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

        DATAS = Sqlite_selects_methods.getall_Products(context);
        if (null == DATAS || DATAS.isEmpty() ){
            DATAS = new ArrayList<>();
        }
        adapter = new Adaptor_product_list(context, DATAS);
        product_list.setAdapter(adapter);
        swiper.setRefreshing(false);
    }

}
