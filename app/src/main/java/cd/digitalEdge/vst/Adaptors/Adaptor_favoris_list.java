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
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Lists.Details_Article;

public class Adaptor_favoris_list extends BaseAdapter {

    Context context;
    ArrayList<Articles> FAVORIS;

    public Adaptor_favoris_list(Context context, ArrayList<Articles> FAVORIS) {
        this.context = context;
        this.FAVORIS = FAVORIS;
    }

    public int getCount() {
        return FAVORIS.size();
    }

    public Object getItem(int position) {
        return FAVORIS.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_favoris_list, null);
        CardView CARD = convertView2.findViewById(R.id.card);
        TextView title = convertView2.findViewById(R.id.title);
        TextView descripion = convertView2.findViewById(R.id.descripion);
        ImageView delete = convertView2.findViewById(R.id.delete);
        ImageView image = convertView2.findViewById(R.id.image);
        ProgressBar progress = convertView2.findViewById(R.id.progress);

        progress.setVisibility(View.GONE);


        Articles data = FAVORIS.get(position);
        title.setText(data.getName());
        descripion.setText(data.getPrice().concat(" USD"));
        String a =data.getImages().substring(1, data.getImages().length()-1);
        String[] imgs = a.split(",");
        String url = Config.ROOT_img.concat(imgs[0].substring(1,imgs[0].length()-1));

        Adaptor_recherche_list.loadImageBitmap(image,url, progress);
        //Tool.Load_Image2(context, image, url);
        /*Ion.with(context)
                .load(url)
                .withBitmap()
                .placeholder(R.drawable.unknow)
                .error(R.drawable.ic_client_blue)
                //.animateLoad(spinAnimation)
                //.animateIn(fadeInAnimation)
                .intoImageView(image);*/

        CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Details_Article.class);
                i.putExtra("Article", data);
                i.putExtra("source", "favoris");
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView2;
    }

}
