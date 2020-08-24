package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Random;

import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Lists.Details_Article;
import cd.digitalEdge.vst.Views.Lists.Recherche;

public class Adaptor_Categorie extends BaseAdapter {

    Context context;
    ArrayList<Categories> DATAS;

    public Adaptor_Categorie(Context context, ArrayList<Categories> DATAS) {
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
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_categorie_item , null);
        final TextView catname = convertView2.findViewById(R.id.catname);
        TextView slug = convertView2.findViewById(R.id.slug);
        LinearLayout CARD = convertView2.findViewById(R.id.CARD);
        Categories data = DATAS.get(position);

        catname.setText(data.getName());
        slug.setText(data.getSlug());
        CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Recherche.class);
                i.putExtra("TITLE", data.getName());
                context.startActivity(i);
            }
        });

        return convertView2;
    }
}
