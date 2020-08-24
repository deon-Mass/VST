package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.Random;

import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Blanks.Add_product;
import cd.digitalEdge.vst.Views.Lists.Chats;
import cd.digitalEdge.vst.Views.Lists.Details_Article;

public class Adaptor_message_list extends BaseAdapter {

    Context context;

    public Adaptor_message_list(Context context) {
        this.context = context;
    }

    public int getCount() {
        return 20;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_message_list, null);
        LinearLayout CARD = convertView2.findViewById(R.id.card);
        TextView index_letter = convertView2.findViewById(R.id.indx);

        index_letter.setText("MASSADI".substring(0,1));
        int r = new Random().nextInt(4);
        if (r == 1) index_letter.setBackgroundResource(R.drawable.oval_blue);
        if (r == 2) index_letter.setBackgroundResource(R.drawable.oval_red);
        if (r == 3) index_letter.setBackgroundResource(R.drawable.oval_green);
        if (r == 4) index_letter.setBackgroundResource(R.drawable.oval_gris);


        CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Chats.class);
                context.startActivity(i);
            }
        });
        return convertView2;
    }

}