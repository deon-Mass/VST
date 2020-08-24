package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Lists.Details_Article;

public class Adaptor_biens_list extends BaseAdapter {

    Context context;
    ArrayList<Articles> DATA_GOODS;

    public Adaptor_biens_list(Context context, ArrayList<Articles> DATA_GOODS) {
        this.context = context;
        this.DATA_GOODS = DATA_GOODS;
    }

    public int getCount() {
        return DATA_GOODS.size();
    }

    public Object getItem(int position) {
        return DATA_GOODS.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_biens_list, null);
        CardView CARD = convertView2.findViewById(R.id.card);
        TextView title = convertView2.findViewById(R.id.title);
        TextView descripion = convertView2.findViewById(R.id.descripion);
        RatingBar ratingbar = convertView2.findViewById(R.id.ratingbar);
        ImageView more = convertView2.findViewById(R.id.more);
        ImageView image = convertView2.findViewById(R.id.image);
        ProgressBar progress = convertView2.findViewById(R.id.progress);

        progress.setVisibility(View.GONE);

        Articles data = DATA_GOODS.get(position);
        title.setText(data.getName());
        descripion.setText(data.getPrice().concat(" USD"));
        String a =data.getImages().substring(1, data.getImages().length()-1);
        String[] imgs = a.split(",");
        String url = Config.ROOT_img.concat(imgs[0].substring(1,imgs[0].length()-1));

        Adaptor_recherche_list.loadImageBitmap(image,url, progress);

        CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Details_Article.class);
                i.putExtra("source", "biens");
                i.putExtra("Article", data);
                context.startActivity(i);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("Supprimer ce bien").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });



        return convertView2;
    }

}
