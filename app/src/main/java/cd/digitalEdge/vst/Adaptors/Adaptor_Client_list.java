package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import cd.digitalEdge.vst.Objects.Customers;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Tool;

public class Adaptor_Client_list extends BaseAdapter {

    Context context;
    ArrayList<Customers> DATAS;

    public Adaptor_Client_list(Context context, ArrayList<Customers> DATAS) {
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
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_client_list, null);
        TextView index_letter = convertView2.findViewById(R.id.index_letter);
        TextView client_name  = convertView2.findViewById(R.id.client_name);
        TextView detail  = convertView2.findViewById(R.id.detail);
        ImageView option  = convertView2.findViewById(R.id.option);

        Customers data = DATAS.get(position);

        client_name.setText(data.getName());
        detail.setText(data.getAddress()+"\n"+ data.getPhone());

        index_letter.setText(data.getName().substring(0,1));
        int r = new Random().nextInt(4);
        if (r == 1) index_letter.setBackgroundResource(R.drawable.oval_blue);
        if (r == 2) index_letter.setBackgroundResource(R.drawable.oval_red);
        if (r == 3) index_letter.setBackgroundResource(R.drawable.oval_green);
        if (r == 4) index_letter.setBackgroundResource(R.drawable.oval_gris);

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("Visualiser").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Cliecked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                popupMenu.getMenu().add("Editer").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Cliecked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                popupMenu.getMenu().add("Supprimmer").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Cliecked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        return convertView2;
    }
}
