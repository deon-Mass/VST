package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;

import cd.digitalEdge.vst.R;

public class Adaptor_Client_list extends BaseAdapter {

    Context context;

    public Adaptor_Client_list(Context context) {
        this.context = context;
    }

    public int getCount() {
        return 10;
    }

    public Object getItem(int position) {
        return Integer.valueOf(0);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_client_list, null);
        TextView message = (TextView) convertView2.findViewById(R.id.message);
        return convertView2;
    }
}
