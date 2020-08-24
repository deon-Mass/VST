package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.Lists.Details_Article;

public class Adaptor_wallet1_list extends BaseAdapter {

    Context context;

    public Adaptor_wallet1_list(Context context) {
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
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_wallet1_list, null);
        //CardView CARD = convertView2.findViewById(R.id.card);

        return convertView2;
    }

}
