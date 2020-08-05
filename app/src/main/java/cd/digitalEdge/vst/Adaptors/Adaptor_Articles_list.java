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

import java.util.ArrayList;
import java.util.Random;

import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.R;

public class Adaptor_Articles_list extends BaseAdapter {

    Context context;

    public Adaptor_Articles_list(Context context) {
        this.context = context;
    }

    public int getCount() {
        return 10;
    }

    public Object getItem(int position) {
        return 0;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_articles_list, null);
        return convertView2;
    }
}
