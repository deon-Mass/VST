package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cd.digitalEdge.vst.R;

public class Adaptor_commands_list extends BaseAdapter {

    Context context;

    public Adaptor_commands_list(Context context) {
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
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_command_list, null);
        //CardView CARD = convertView2.findViewById(R.id.CARD);
        return convertView2;
    }

}
