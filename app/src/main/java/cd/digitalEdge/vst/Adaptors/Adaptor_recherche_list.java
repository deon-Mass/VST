package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_updates_methods;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Lists.Details_Article;

public class Adaptor_recherche_list extends BaseAdapter {

    Context context;
    ArrayList<Articles> DATAS;


    public Adaptor_recherche_list(Context context, ArrayList<Articles> DATAS) {
        this.context = context;
        this.DATAS = DATAS;
    }

    public int getCount() {
        return DATAS.size();
    }

    public Object getItem(int position) {
        return DATAS.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_articles_list2, null);
        CardView CARD = convertView2.findViewById(R.id.CARD);
        TextView name = convertView2.findViewById(R.id.name);
        TextView price = convertView2.findViewById(R.id.price);
        ImageView more = convertView2.findViewById(R.id.more);
        ImageView like = convertView2.findViewById(R.id.like);
        FloatingActionButton addToCard = convertView2.findViewById(R.id.addToCard);
        final int[] turn = {0};

        Articles data = DATAS.get(position);


        name.setText(data.getName());
        price.setText(data.getPrice().concat(" USD"));

        CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Details_Article.class);
                i.putExtra("Article", data);
                context.startActivity(i);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn[0] == 0) {
                    like.setImageResource(R.drawable.ic_favorite);
                    turn[0] =1;
                }
                else {
                    like.setImageResource(R.drawable.ic_favortie_orange);
                    turn[0] =0;
                }
            }
        });
        addToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCard(context,data);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("Ajouter à mon panier").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AddToCard(context,data);
                        return false;
                    }
                });
                popupMenu.getMenu().add("Ajouter aux favoris").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
                popupMenu.getMenu().add("Noter le produit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        new Utils().Noter_article(context, data.getId());
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
        return convertView2;
    }

    public static void AddToCard(Context context,Articles data){
        data.setQnt("1");
        long response = Sqlite_updates_methods.insert_PRODUCT(context, data);
        if (response == 1){
            Toast.makeText(context, "Added to card", Toast.LENGTH_SHORT).show();
        }
        else if(response == 2) Toast.makeText(context, "L'article se trouve déjà dans votre panier", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Error while adding", Toast.LENGTH_SHORT).show();
    }
}
