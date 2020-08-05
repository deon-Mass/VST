package cd.digitalEdge.vst.Adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.Controllers.Offline.ExecuteUpdate;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Utils;
import cd.digitalEdge.vst.Views.Lists.Details_Article;
import cd.digitalEdge.vst.Views.Lists.Panier;

public class Adaptor_Panier extends BaseAdapter {

    Context context;
    ArrayList<Articles> PANIER;

    public Adaptor_Panier(Context context, ArrayList<Articles> PANIER) {
        this.context = context;
        this.PANIER = PANIER;
    }

    public void refreshEvents(ArrayList<Articles> events, Articles data) {
        ArrayList<Articles> PANIER = new ArrayList<>();
        PANIER = Sqlite_selects_methods.getall_Articles(context);
        if ( null == PANIER || PANIER.isEmpty() ){
            PANIER = new ArrayList<>();
            //Log.e("DATA", "DATAS "+PANIER.size());
        }
        this.PANIER = PANIER;
        notifyDataSetChanged();
    }

    public int getCount() {
        return PANIER.size();
    }

    public Object getItem(int position) {
        return PANIER.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_panier_item, null);

        ImageView option = convertView2.findViewById(R.id.option);
        CardView card = convertView2.findViewById(R.id.card);
        TextView name = convertView2.findViewById(R.id.name);
        TextView descripion = convertView2.findViewById(R.id.descripion);
        TextView price = convertView2.findViewById(R.id.price);
        TextView PT = convertView2.findViewById(R.id.PT);
        TextView pay_now = convertView2.findViewById(R.id.pay_now);
        ImageView delete_panier = convertView2.findViewById(R.id.delete_panier);
        NumberPicker qnt_picker = convertView2.findViewById(R.id.qnt_picker);

        Articles data = PANIER.get(position);

        name.setText(data.getName());
        PT.setText(
                String.valueOf(Integer.parseInt(data.getPrice()) * Integer.parseInt(data.getQnt())) + " USD"
        );
        descripion.setText(data.getSlug());
        pay_now.setText(data.getQnt());
        price.setText(data.getPrice().concat(" USD"));
        qnt_picker.setMaxValue(5);
        qnt_picker.setMinValue(1);
        qnt_picker.setDisplayedValues(Utils.QNT_PICKER());

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Details_Article.class);
                i.putExtra("source", "P");
                context.startActivity(i);
            }
        });
        delete_panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExecuteUpdate.delete(context, Constants.ARTICLE,new Articles().id, data.getId()) == 1){
                    Toast.makeText(context, "Article deleted", Toast.LENGTH_SHORT).show();
                    refreshEvents(PANIER, data);
                }
            }
        });
        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.view_number_picker, null);

                NumberPicker qnt_picker = view.findViewById(R.id.qnt_picker);

                qnt_picker.setMaxValue(5);
                qnt_picker.setMinValue(1);
                qnt_picker.setDisplayedValues(Utils.QNT_PICKER());

                new AlertDialog.Builder(context)
                        .setView(view)
                        .setPositiveButton("D'accord", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (
                                        ExecuteUpdate.update(
                                                context, Constants.ARTICLE,new Articles().qnt, String.valueOf(qnt_picker.getValue() -1),
                                                new Articles().id, data.getId()
                                        ) == 1
                                ){
                                    pay_now.setText(String.valueOf(qnt_picker.getValue() -1));
                                    Toast.makeText(context, "updated qnt", Toast.LENGTH_SHORT).show();
                                    refreshEvents(PANIER, data);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("Acheter maintenant").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.getMenu().add("Retirer du panier").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        return convertView2;
    }
}
