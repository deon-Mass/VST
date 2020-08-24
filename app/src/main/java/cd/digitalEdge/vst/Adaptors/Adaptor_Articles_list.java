package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_blog_item, null);
        return convertView2;
    }
}
